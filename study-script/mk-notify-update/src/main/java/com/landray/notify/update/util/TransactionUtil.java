package com.landray.notify.update.util;

import com.landray.notify.update.model.SourceApp;
import com.landray.notify.update.model.SourceModule;
import com.landray.notify.update.support.AcmePhysicalNamingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.EntityManager;

/**
 * @author: yaowr
 * @create: 2022-07-26
 */
@Slf4j
public class TransactionUtil {

    private static SessionFactory sessionFactory = SessionFactoryHolder.getInstance();

    public static void inNewTransaction(Runnable runnable) {
        Session session = null;
        try {
            // 打开session并开启事务
            session = sessionFactory.openSession();
            session.beginTransaction();
            // 运行业务方法
            runnable.run();
            // 提交事务
            session.getTransaction().commit();
        } catch (Exception e) {
            // 回滚事务
            if (session != null) {
                session.getTransaction().rollback();
                log.error("Mock data rollback!", e);
            } else {
                log.error("Mock data error!", e);
            }
        } finally {
            // 关闭session
            if (session != null) {
                session.close();
            }
        }
    }
}
