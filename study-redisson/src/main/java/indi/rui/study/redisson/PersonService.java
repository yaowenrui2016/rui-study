package indi.rui.study.redisson;

import java.util.List;
import java.util.Set;

/**
 * @author: yaowr
 * @create: 2020-01-31
 */
public interface PersonService {
    void add(Person person);

    void testTx(Person person);

    void batchedAdd(int amount);

    Person randomPerson(long num);

    QueryResult<Person> findAll(QueryRequest request);

    Person findById(Long id);

    Person update(Person person);

    int deleteByGender(GenderEnum gender);

    int deleteByName(String name);

    int deleteById(Long id);

    int deleteAll(List<Long> ids);

    int deleteBetweenIds(Long start, Long end);

    void send();

    void addByKafka(Person person);

    void distinctRandomPut();

    void distinctRandomPutAll();

    Set<Integer> distinctRandomGet();

    Set<Integer> rsetGet();

    String addUpdateAndDelete();
}
