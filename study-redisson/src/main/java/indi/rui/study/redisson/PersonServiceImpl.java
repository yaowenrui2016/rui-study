package indi.rui.study.redisson;

import static indi.rui.study.redisson.RedisKeysConstant.CURRENT_PERSON_ID_KEY;
import static indi.rui.study.redisson.RedisKeysConstant.RANDOM_TEST_KEY;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.redisson.api.RAtomicLong;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2020-01-31
 */
@Slf4j
@Service
// @Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository repository;
    @Autowired
    private EntityManager em;
    @Autowired
    private RedissonClient redisson;
    @Autowired
    private RedisTemplate redisTemplate;
    // @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private PlatformTransactionManager txManager;

    private Random r = new Random(System.currentTimeMillis());

    private ThreadPoolExecutor executor =
        new ThreadPoolExecutor(8, 8, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10000),
            new NamedThreadFactory("producer"), new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public void add(Person person) {
        executor.execute(() -> {
            TransactionStatus status1 = txManager.getTransaction(new DefaultTransactionDefinition());
            log.info("status1 is {} new", status1.isNewTransaction());
            TransactionStatus status2 = txManager
                .getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));
            log.info("status2 is {} new", status2.isNewTransaction());
            repository.save(person);
            txManager.commit(status1);
        });
    }

    /**
     * 测试一下事务
     * 
     * @param person
     */
    @Override
    public void testTx(Person person) {
        repository.save(person);
        throw new NoRecordException("ss");
    }

    @Override
    public void addByKafka(Person person) {
        kafkaTemplate.send("study_redisson_person", JSON.toJSONString(person));
    }

    // @KafkaListener(topics = "study_redisson_person")
    // public void consumePerson(ConsumerRecord<Object, String> cr) {
    // repository.save(JSON.parseObject(cr.value(), Person.class));
    // }

    @Override
    public void batchedAdd(int amount) {
        long begin = getNum(amount);
        new ForkJoinPool().invoke(new CreatePersonTask((int)begin, amount));
        log.info("创建Person Begin with {} and amount is {}", begin, amount);
    }

    private long getNum(int amount) {
        RAtomicLong currentPersonId = redisson.getAtomicLong(CURRENT_PERSON_ID_KEY);
        for (;;) {
            long begin = currentPersonId.get();
            if (currentPersonId.compareAndSet(begin, begin + amount)) {
                return begin;
            }
            log.info("人员编号 {} 被占用", begin);
        }
    }

    @Override
    public Person randomPerson(long num) {
        Person person = new Person();
        person.setFdName("person_" + num);
        person.setFdBirthday(new Date());
        person.setFdGender((num + r.nextInt(100)) % 2 == 0 ? GenderEnum.MALE : GenderEnum.FEMALE);
        Account account = new Account();
        account.setFdUsername("account_" + num);
        account.setFdPassword("****".replace("*", String.valueOf(r.nextInt(10))));
        person.setFdAccount(account);
        return person;
    }

    /**
     * 递归创建的任务类
     */
    private class CreatePersonTask extends RecursiveAction {

        private int begin;
        private int len;

        public CreatePersonTask(int begin, int len) {
            this.begin = begin;
            this.len = len;
        }

        @Override
        protected void compute() {
            if (len > 1000) {
                int middle = len / 2;
                CreatePersonTask left = new CreatePersonTask(begin, middle);
                CreatePersonTask right = new CreatePersonTask(begin + middle, len - middle);
                invokeAll(left, right);
            } else {
                List<Person> tmp = new LinkedList<>();
                for (long i = begin; i < begin + len; i++) {
                    tmp.add(randomPerson(i));
                }
                repository.saveAll(tmp);
                log.info("保存Person【{}, {}】", begin, len);
            }
        }
    }

    @Override
    public QueryResult<Person> findAll(QueryRequest request) {
        CriteriaBuilder builder = em.getCriteriaBuilder();
        // 查询总数
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<Person> root = criteriaQuery.from(Person.class);
        criteriaQuery.select(builder.count(root.get("fdId")));
        toPredicate(builder, criteriaQuery, root, request.getConditions()); // where条件
        int total = em.createQuery(criteriaQuery).getSingleResult().intValue();

        // 查询内容
        CriteriaQuery query = builder.createQuery(Person.class);
        Root<Person> rt = query.from(Person.class);
        query.select(rt);
        toPredicate(builder, query, rt, request.getConditions()); // where条件
        List<Person> content = em.createQuery(query).setFirstResult(request.getOffset())
            .setMaxResults(request.getPageSize()).getResultList();
        return QueryResult.of(request, total, content);
    }

    private void toPredicate(CriteriaBuilder builder, CriteriaQuery criteriaQuery, Root root,
        Map<String, Object> conditions) {
        if (CollectionUtils.isEmpty(conditions)) {
            return;
        }
        if (conditions.containsKey("fdName")) {
            Predicate predicate = builder.like(root.get("fdName"), "%" + conditions.get("fdName") + "%");
            // Predicate predicate = builder.equal(root.get("fdName"), conditions.get("fdName"));
            criteriaQuery.where(predicate);
        }
    }

    @Override
    public Person findById(Long fdId) {
        Person entity = repository.findById(fdId).orElseThrow(this::throwErr);
        return entity;
    }

    @Transactional
    @Override
    public Person update(Person person) {
        Person entity = findById(person.getFdId());
        BeanUtils.copyProperties(person, entity);
        return entity;
    }

    @Transactional
    @Override
    public int deleteByGender(GenderEnum gender) {
        return repository.deleteByGender(gender);
    }

    @Transactional
    @Override
    public int deleteByName(String name) {
        return repository.deleteByName(name);
    }

    @Transactional
    @Override
    public int deleteById(Long id) {
        return repository.deleteByIds(Collections.singletonList(id));
    }

    @Transactional
    @Override
    public int deleteAll(List<Long> ids) {
        int result;
        if (CollectionUtils.isEmpty(ids)) {
            result = repository.deleteByAll();
        } else {
            result = repository.deleteByIds(ids);
        }
        return result;
    }

    @Transactional
    @Override
    public int deleteBetweenIds(Long start, Long end) {
        return repository.deleteBetweenIds(start, end);
    }

    private RuntimeException throwErr() {
        return new RuntimeException("记录不存在");
    }

    private AtomicInteger counter = new AtomicInteger(1);

    @Override
    public void send() {
        kafkaTemplate.send("my_topic_name", "hello springboot " + counter.getAndIncrement());
    }

    // @KafkaListener(topics = "my_topic_name")
    // public void recive(ConsumerRecord<String, String> record) {
    // log.info("{} -> {}", record.key(), record.value());
    // }

    @Override
    public void distinctRandomPut() {
        for (int i = 0; i < 10000; i++) {
            redisTemplate.opsForSet().add(RANDOM_TEST_KEY, i);
        }
        redisTemplate.expire(RANDOM_TEST_KEY, 30, TimeUnit.MINUTES);
    }

    @Override
    public void distinctRandomPutAll() {
        Integer[] arr = new Integer[10000];
        for (int i = 0; i < 10000; i++) {
            arr[i] = i;
        }
        redisTemplate.opsForSet().add(RANDOM_TEST_KEY, arr);
        redisTemplate.expire(RANDOM_TEST_KEY, 30, TimeUnit.MINUTES);
    }

    @Override
    public Set<Integer> distinctRandomGet() {
        return redisTemplate.opsForSet().distinctRandomMembers(RANDOM_TEST_KEY, 50);
    }

    @Override
    public Set<Integer> rsetGet() {
        RSet<Integer> rset = redisson.getSet(RANDOM_TEST_KEY);
        return rset.random(50);
    }

    @Override
    public String addUpdateAndDelete() {
        TransactionStatus status1 = txManager.getTransaction(new DefaultTransactionDefinition());
//        TransactionStatus status2 =
//            txManager.getTransaction(new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW));
        log.info("status1 is {} new", status1.isNewTransaction());
//        log.info("status2 is {} new", status2.isNewTransaction());
        long num = getNum(1);
        Person person = randomPerson(num);
        // 新增
        repository.save(person);
        // 更新
        person.setFdComment("我是一个好孩子");
        repository.save(person);
        // 删除
        repository.delete(person);
        txManager.commit(status1);
        return String.valueOf(num);
    }
}
