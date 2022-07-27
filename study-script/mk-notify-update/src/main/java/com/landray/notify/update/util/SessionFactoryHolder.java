package com.landray.notify.update.util;

import com.landray.notify.update.model.SourceApp;
import com.landray.notify.update.model.SourceModule;
import com.landray.notify.update.support.AcmePhysicalNamingStrategy;
import lombok.extern.slf4j.Slf4j;
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
public class SessionFactoryHolder {

    private static SessionFactory sessionFactory = null;

    public static SessionFactory getInstance() {
        if (sessionFactory == null) {
            synchronized (TransactionUtil.class) {
                if (sessionFactory == null) {
                    // using an explicitly built BootstrapServiceRegistry
                    BootstrapServiceRegistry bootstrapRegistry =
                            new BootstrapServiceRegistryBuilder().build();

                    // A SessionFactory is set up once for an application!
                    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder(bootstrapRegistry)
                            .configure("config/hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
                            .build();
                    try {
                        MetadataSources sources = new MetadataSources(registry);
//                        sources.addAnnotatedClass(SourceApp.class);
//                        sources.addAnnotatedClass(SourceModule.class);
                        MetadataBuilder metadataBuilder = sources.getMetadataBuilder();
                        metadataBuilder.applyPhysicalNamingStrategy(new AcmePhysicalNamingStrategy());
                        sessionFactory = metadataBuilder.build().buildSessionFactory();
                    } catch (Exception e) {
                        // The registry would be destroyed by the SessionFactory, but we had trouble building the SessionFactory
                        // so destroy it manually.
                        log.error("Create 'sessionFactory' failed", e);
                        StandardServiceRegistryBuilder.destroy(registry);
                    }
                }
            }
        }
        return sessionFactory;
    }

    public static EntityManager createEntityManager() {
        return getInstance().createEntityManager();
    }
}
