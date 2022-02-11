package com.landray.repair;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.landray.repair.conf.AcmePhysicalNamingStrategy;
import com.landray.repair.entity.SourceApp;
import com.landray.repair.entity.SourceAppModule;
import com.landray.repair.entity.SourceModule;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.BootstrapServiceRegistry;
import org.hibernate.boot.registry.BootstrapServiceRegistryBuilder;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static indi.rui.study.unittest.util.SimpleIdGenerator.generateID;

/**
 * @author: yaowr
 * @create: 2022-01-27
 */
@Slf4j
public class Main {

    private static SessionFactory sessionFactory;

    static {
        // using an explicitly built BootstrapServiceRegistry
        BootstrapServiceRegistry bootstrapRegistry =
                new BootstrapServiceRegistryBuilder().build();

        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder(bootstrapRegistry)
                .configure("config/hibernate.cfg.xml") // configures settings from hibernate.cfg.xml
                .build();
        try {
            MetadataSources sources = new MetadataSources(registry);
            sources.addAnnotatedClass(SourceApp.class);
            sources.addAnnotatedClass(SourceAppModule.class);
            sources.addAnnotatedClass(SourceModule.class);
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

    // =========================== 业务方法 ======================== //

    private static void mockData() {
        Session session = null;
        try {
            // 打开session并开启事务
            session = sessionFactory.openSession();
            session.beginTransaction();
            // 从JSON文件读取sourceApp数据
            List<SourceApp> sourceApps = FileUtils.loadJSON4List("source_apps.json", SourceApp.class);
            List<String> appIds = new ArrayList<>();
            for (SourceApp sourceApp : sourceApps) {
                // 检查数据库是否存在sourceApp，不存在则保存
                SourceApp prevSourceApp = session.get(SourceApp.class, sourceApp.getFdId());
                if (prevSourceApp == null) {
                    appIds.add((String) session.save(sourceApp));
                    log.info("saved sourceApp success! sourceApp={}",
                            JSONObject.toJSONString(sourceApp, SerializerFeature.PrettyFormat));
                } else {
                    appIds.add(prevSourceApp.getFdId());
                }
            }
            // 从JSON文件读取sourceModule数据
            List<SourceModule> sourceModules = FileUtils.loadJSON4List("source_modules.json", SourceModule.class);
            List<String> moduleIds = new ArrayList<>();
            for (SourceModule sourceModule : sourceModules) {
                // 检查数据库是否存在sourceModule，不存在则保存
                SourceModule prev = session.get(SourceModule.class, sourceModule.getFdId());
                if (prev == null) {
                    moduleIds.add((String) session.save(sourceModule));
                    log.info("saved sourceModule success! sourceModule={}",
                            JSONObject.toJSONString(sourceModule, SerializerFeature.PrettyFormat));
                } else {
                    moduleIds.add(prev.getFdId());
                }
            }
            // 建立中间表数据
            for (String appId : appIds) {
                for (String moduleId : moduleIds) {
                    SourceAppModule sourceAppModule = new SourceAppModule();
                    sourceAppModule.setFdAppId(appId);
                    sourceAppModule.setFdModuleId(moduleId);
                    sourceAppModule.setFdTenantId(0);
                    sourceAppModule.setFdSourceId("4");
                    sourceAppModule.setFdId(generateID());
                    session.save(sourceAppModule);
                }
            }
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

    private static void doWork() {
        Session session = null;
        try {
            // 打开session并开启事务
            session = sessionFactory.openSession();
            session.beginTransaction();
            // 查询SourceModule
            List<SourceModule> modules = session.createQuery("from SourceModule", SourceModule.class).list();
            Map<String, SourceModule> holdOn = new HashMap<>();
            List<SourceModule> duplicate = new ArrayList<>();
            // 筛选出code重复的SourceModule
            for (SourceModule module : modules) {
                SourceModule delModule = holdOn.put(module.getFdCode(), module);
                if (delModule != null) {
                    duplicate.add(delModule);
                }
            }
            log.info("Find modules({}), duplicate({})",
                    modules.size(),
                    duplicate.size());
            if (CollectionUtils.isNotEmpty(duplicate)) {
                // 检查重复SourceModule是否被引用
                String moduleIds = StringUtils.join(duplicate.stream().map(SourceModule::getFdId).toArray(), "','");
                List<SourceAppModule> sourceAppModules = session.createQuery("from SourceAppModule where fdModuleId in ('" + moduleIds + "')", SourceAppModule.class).list();
                if (CollectionUtils.isNotEmpty(sourceAppModules)) {
                    for (SourceAppModule sourceAppModule : sourceAppModules) {
                        // 寻找可替代的SourceModule
                        SourceModule replace = null;
                        for (SourceModule delModule : duplicate) {
                            if (delModule.getFdId().equals(sourceAppModule.getFdModuleId())) {
                                replace = holdOn.get(delModule.getFdCode());
                                break;
                            }
                        }
                        if (replace != null) {
                            // 检测替换后是否违反唯一索引
                            List<SourceAppModule> exists = session.createQuery(
                                    "from SourceAppModule where fdAppId = '" + sourceAppModule.getFdAppId()
                                            + "' and fdModuleId = '" + replace.getFdId()
                                            + "' and fdSourceId = '" + sourceAppModule.getFdSourceId()
                                            + "'", SourceAppModule.class).list();
                            if (CollectionUtils.isNotEmpty(exists)) {
                                session.delete(sourceAppModule);
                            } else {
                                // 替换SourceModule引用
                                sourceAppModule.setFdModuleId(replace.getFdId());
                                session.update(sourceAppModule);
                            }
                        }
                    }
                }
                // 删除重复SourceModule
                for (SourceModule delModule : duplicate) {
                    session.delete(delModule);
                }
            }
            // 提交事务
            session.getTransaction().commit();
        } catch (Exception e) {
            // 回滚事务
            if (session != null) {
                session.getTransaction().rollback();
                log.error("Do work rollback!", e);
            } else {
                log.error("Do work error!", e);
            }
        } finally {
            // 关闭session
            if (session != null) {
                session.close();
            }
        }
    }

    public static void main(String[] args) {
        log.info("Startup!");
        try {
//            mockData();
            doWork();
        } finally {
            sessionFactory.close();
            log.info("Stop!");
        }
    }
}
