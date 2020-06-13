package indi.rui.study.common.query;

import indi.rui.study.common.dto.Condition;
import indi.rui.study.common.dto.IVO;
import indi.rui.study.common.entity.IEntity;
import indi.rui.study.common.utils.ReflectUtil;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author: yaowr
 * @create: 2020-06-10
 */
public class QueryTemplate<E extends IEntity, V extends IVO> {

    private EntityManager entityManager;
    private Class<E> entityClass;
    private Class<V> viewObjectClass;
    private BiConsumer<E, V> entityToVo;

    public QueryTemplate(EntityManager entityManager, Class<E> entityClass, Class<V> viewObjectClass,
                         BiConsumer<E, V> entityToVo) {
        this.entityManager = entityManager;
        this.entityClass = entityClass;
        this.viewObjectClass = viewObjectClass;
        this.entityToVo = entityToVo;
    }

    public QueryResult find(QueryRequest request) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        // 查询总数
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        Root<E> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(builder.count(root.get("fdId")));
        toPredicate(builder, criteriaQuery, root, request.getConditions()); // where条件
        int total = entityManager.createQuery(criteriaQuery).getSingleResult().intValue();
        // 查询内容
        CriteriaQuery query = builder.createQuery(entityClass);
        Root<E> rt = query.from(entityClass);
        query.select(rt);
        toPredicate(builder, query, rt, request.getConditions()); // where条件
        List<E> entitys = entityManager.createQuery(query).setFirstResult(request.getOffset())
                .setMaxResults(request.getPageSize()).getResultList();
        // Entity转换为VO
        List<V> content = new ArrayList();
        for (E entity : entitys) {
            V vo = ReflectUtil.newInstance(viewObjectClass);
            entityToVo.accept(entity, vo);
            content.add(vo);
        }
        return QueryResult.of(request, total, content);
    }

    private void toPredicate(CriteriaBuilder builder, CriteriaQuery criteriaQuery, Root root,
                             List<Condition> conditions) {
        if (CollectionUtils.isEmpty(conditions)) {
            return;
        }
        List<Predicate> predicates = new ArrayList<>();
        for (Condition condition : conditions) {
            Predicate predicate;
            List<String> btVals;
            switch (condition.getOperator()) {
                case eq:
                    predicate = builder.equal(root.get(condition.getPropName()), condition.getValue());
                    break;
                case neq:
                    predicate = builder.notEqual(root.get(condition.getPropName()), condition.getValue());
                    break;
                case lt:
                    predicate = builder.lessThan(root.get(condition.getPropName()), (String) condition.getValue());
                    break;
                case lte:
                    predicate = builder.lessThanOrEqualTo(root.get(condition.getPropName()), (String) condition.getValue());
                    break;
                case gt:
                    predicate = builder.greaterThan(root.get(condition.getPropName()), (String) condition.getValue());
                    break;
                case gte:
                    predicate = builder.greaterThanOrEqualTo(root.get(condition.getPropName()), (String) condition.getValue());
                    break;
                case in:
                    predicate = root.get(condition.getPropName()).in((Collection<?>) condition.getValue());
                    break;
                case notIn:
                    predicate = builder.not(root.get(condition.getPropName()).in((Collection<?>) condition.getValue()));
                    break;
                case like:
                    predicate = builder.like(root.get(condition.getPropName()), "%" + condition.getValue() + "%");
                    break;
                case isNull:
                    predicate = root.get(condition.getPropName()).isNull();
                    break;
                case isNotNull:
                    predicate = root.get(condition.getPropName()).isNotNull();
                    break;
                case between:
                    btVals = (List<String>) condition.getValue();
                    predicate = builder.between(root.get(condition.getPropName()), btVals.get(0), btVals.get(1));
                    break;
                case notBetween:
                    btVals = (List<String>) condition.getValue();
                    predicate = builder.not(builder.between(root.get(condition.getPropName()), btVals.get(0), btVals.get(1)));
                    break;
                default:
                    throw new RuntimeException("不支持的操作条件");
            }
            predicates.add(predicate);
        }
        criteriaQuery.where(builder.and(predicates.toArray(new Predicate[predicates.size()])));
    }

}
