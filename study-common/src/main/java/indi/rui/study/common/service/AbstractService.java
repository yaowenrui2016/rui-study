package indi.rui.study.common.service;

import indi.rui.study.common.dto.IVO;
import indi.rui.study.common.dto.IdVO;
import indi.rui.study.common.dto.IdsVO;
import indi.rui.study.common.entity.IEntity;
import indi.rui.study.common.field.FdCreateTime;
import indi.rui.study.common.query.QueryRequest;
import indi.rui.study.common.query.QueryResult;
import indi.rui.study.common.query.QueryTemplate;
import indi.rui.study.common.repository.IRepository;
import indi.rui.study.common.utils.IDGenerator;
import indi.rui.study.common.utils.ReflectUtil;
import indi.rui.study.common.utils.VoAndEntityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 * @author: yaowr
 * @create: 2020-06-09
 */
@Transactional(readOnly = true)
public class AbstractService<E extends IEntity, V extends IVO, R extends IRepository<E>>
        implements IService<E, V> {

    @PersistenceContext
    protected EntityManager entityManager;
    @Autowired
    protected R repository;
    protected Class<E> entityClass;
    protected Class<V> viewObjectClass;


    @Transactional
    @Override
    public void add(V vo) {
        E entity = ReflectUtil.newInstance(getEntityClass());
        voToEntity(vo, entity);
        beforeAdd(entity);
        repository.save(entity);
    }

    @Transactional
    @Override
    public void update(V vo) {
        E entity = loadById(IdVO.of(vo.getFdId())).orElseThrow(() -> new RuntimeException("记录不存在"));
        voToEntity(vo, entity);
        beforeUpdate(entity);
        repository.save(entity);
    }

    @Override
    public QueryResult<V> find(QueryRequest request) {
        QueryTemplate queryTemplate = new QueryTemplate<>(entityManager, getEntityClass(), getViewObjectClass(),
                this::entityToVo);
        return queryTemplate.find(request);
    }

    @Override
    public V get(IdVO idVO) {
        E entity = loadById(idVO).orElseThrow(() -> new RuntimeException("记录不存在"));
        V vo = ReflectUtil.newInstance(getViewObjectClass());
        entityToVo(entity, vo);
        return vo;
    }

    @Transactional
    @Override
    public void delete(IdVO idVO) {
        repository.deleteById(idVO.getFdId());
    }

    @Transactional
    @Override
    public void deleteByIds(IdsVO idsVO) {
        for (Long fdId : idsVO.getFdIds()) {
            repository.deleteById(fdId);
        }
    }

    @Override
    public Optional<E> loadById(IdVO idVO) {
        return repository.findById(idVO.getFdId());
    }

    @Transactional
    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    public List<V> findAll() {
        Iterator<E> iterator = repository.findAll().iterator();
        List<V> rtnList = new ArrayList<>(64);
        while (iterator.hasNext()) {
            V vo = ReflectUtil.newInstance(getViewObjectClass());
            entityToVo(iterator.next(), vo);
            rtnList.add(vo);
        }
        return rtnList;
    }

    //====================== 推荐重载的底层方法 ====================//

    protected void beforeAdd(E entity) {
        if (Objects.isNull(entity.getFdId())) {
            entity.setFdId(IDGenerator.get());
        }
        if (entity instanceof FdCreateTime) {
            ((FdCreateTime) entity).setFdCreateTime(new Date());
        }
    }

    protected void beforeUpdate(E entity) {

    }

    protected void voToEntity(V vo, E entity) {
        VoAndEntityHelper.copyProperties(vo, entity);
    }

    protected void entityToVo(E entity, V vo) {
        VoAndEntityHelper.copyProperties(entity, vo);
    }

    //==================== 获取VO和Entity类的方法 ==================//

    @Override
    @SuppressWarnings("unchecked")
    public Class<E> getEntityClass() {
        if (entityClass == null) {
            entityClass = (Class<E>) ReflectUtil.getActualClass(getClass(),
                    AbstractService.class, "E");
        }
        return entityClass;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<V> getViewObjectClass() {
        if (viewObjectClass == null) {
            viewObjectClass = (Class<V>) ReflectUtil.getActualClass(getClass(),
                    AbstractService.class, "V");
        }
        return viewObjectClass;
    }
}
