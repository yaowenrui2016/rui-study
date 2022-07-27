package com.landray.notify.update.util;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.function.Consumer;

/**
 * @author: yaowr
 * @create: 2022-07-26
 */
@Slf4j
public class TransactionUtil {

    /**
     * 开启事务
     *
     * @param consumer
     */
    public static void inTransaction(Consumer<EntityManager> consumer) {
        EntityManager entityManager = null;
        EntityTransaction entityTx = null;
        try {
            // 创建entityManager
            entityManager = SessionFactoryHolder.createEntityManager();
            // 开启事务
            entityTx = entityManager.getTransaction();
            entityTx.begin();
            // 运行业务方法
            consumer.accept(entityManager);
            // 提交事务
            entityTx.commit();
        } catch (Exception e) {
            // 回滚事务
            if (entityTx != null) {
                entityTx.rollback();
                log.error("Transaction error, rollback!", e);
            } else {
                log.error("Transaction error!", e);
            }
        } finally {
            // 关闭entityManager
            entityManager.close();
        }
    }
}
