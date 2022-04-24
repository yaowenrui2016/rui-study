package com.landray.repair;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.landray.repair.conf.AcmePhysicalNamingStrategy;
import com.landray.repair.entity.SysNotifyDone;
import com.landray.repair.entity.SysNotifyOriginal;
import com.landray.repair.entity.SysNotifyTodo;
import com.landray.repair.entity.SysOrgElementSummary;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
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
import org.hibernate.query.Query;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author: yaowr
 * @create: 2022-01-27
 */
@Slf4j
public class Main {

    public static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
            sources.addAnnotatedClass(SysNotifyTodo.class);
            sources.addAnnotatedClass(SysNotifyDone.class);
            sources.addAnnotatedClass(SysNotifyOriginal.class);
            sources.addAnnotatedClass(SysOrgElementSummary.class);
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

    private static Properties properties = new Properties();

    static {
        try {
            InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("bootstrap.properties");
            properties.load(inputStream);
            log.info("properties: {}", JSONObject.toJSONString(properties, SerializerFeature.PrettyFormat));
        } catch (IOException e) {
            throw new RuntimeException("加载bootstrap.properties失败");
        }
    }

    // =========================== 业务方法 ======================== //

//    private static void byTimeRange() {
//        // 加载参数
//        String startTime = properties.getProperty("startTime");
//        String endTime = properties.getProperty("endTime");
//        boolean insert = Boolean.parseBoolean(properties.getProperty("insert"));
//        int batchSize = Integer.parseInt(properties.getProperty("batchSize"));
//        int todoType = Integer.parseInt(properties.getProperty("todoType"));
//        String targets = properties.getProperty("targets");
//
//        // 按白名单人员处理
//        List<String> targetList = Arrays.asList(targets.split(","));
//
//        Session session = null;
//        try {
//            // 打开session
//            session = sessionFactory.openSession();
//            // 查询已办数据
//            Query<Long> totalQuery = session.createQuery("select count(1) from SysNotifyDone where fdType = " + todoType + " and fdFinishTime between ?1 and ?2", Long.class);
//            totalQuery.setParameter(1, Date.from(LocalDateTime.parse(startTime, DEFAULT_DATE_FORMATTER)
//                    .atZone(ZoneId.systemDefault())
//                    .toInstant()));
//            totalQuery.setParameter(2, Date.from(LocalDateTime.parse(endTime, DEFAULT_DATE_FORMATTER)
//                    .atZone(ZoneId.systemDefault())
//                    .toInstant()));
//            long total = totalQuery.getSingleResult();
//            Query<SysNotifyDone> query = session.createQuery("from SysNotifyDone where fdType = " + todoType + " and fdFinishTime between  ?1 and ?2", SysNotifyDone.class);
//            query.setParameter(1, Date.from(LocalDateTime.parse(startTime, DEFAULT_DATE_FORMATTER)
//                    .atZone(ZoneId.systemDefault())
//                    .toInstant()));
//            query.setParameter(2, Date.from(LocalDateTime.parse(endTime, DEFAULT_DATE_FORMATTER)
//                    .atZone(ZoneId.systemDefault())
//                    .toInstant()));
//            int offset = 0;
//            int pageSize = batchSize;
//            int doneCount = 0;
//            int errorCount = 0;
//            int insertCount = 0;
//            while (true) {
//                // 开启事务
//                try {
//                    session.beginTransaction();
//                    query.setFirstResult(offset);
//                    query.setMaxResults(pageSize);
//                    List<SysNotifyDone> doneList = query.list();
//                    for (SysNotifyDone done : doneList) {
//                        // 检查sys_notify_original
//                        String sql = "select count(1) from SysNotifyOriginal where fd_command = 'done'";
//                        if (StringUtils.isNotBlank(done.getFdAppName())) {
//                            sql += " and fd_app_name = '" + done.getFdAppName() + "'";
//                        }
//                        if (StringUtils.isNotBlank(done.getFdModuleName())) {
//                            sql += " and fd_module_name = '" + done.getFdModuleName() + "'";
//                        }
//                        if (StringUtils.isNotBlank(done.getFdEntityId())) {
//                            sql += " and fd_entity_id = '" + done.getFdEntityId() + "'";
//                        }
//                        if (StringUtils.isNotBlank(done.getFdEntityName())) {
//                            sql += " and fd_entity_name = '" + done.getFdEntityName() + "'";
//                        }
//                        Query<Long> oriQuery = session.createQuery(sql, Long.class);
//                        long count = oriQuery.getSingleResult();
//                        if (count <= 0) {
//                            log.error("errorDone: {}", JSONObject.toJSONString(done));
//                            errorCount++;
//                            if (insert) {
//                                // 检查sys_notify_todo
//                                String todoSql = "select count(1) from SysNotifyTodo where fdOwnerId = '" + done.getFdOwnerId() + "'";
//                                if (StringUtils.isNotBlank(done.getFdAppName())) {
//                                    todoSql += " and fd_app_name = '" + done.getFdAppName() + "'";
//                                }
//                                if (StringUtils.isNotBlank(done.getFdModuleName())) {
//                                    todoSql += " and fd_module_name = '" + done.getFdModuleName() + "'";
//                                }
//                                if (StringUtils.isNotBlank(done.getFdEntityId())) {
//                                    todoSql += " and fd_entity_id = '" + done.getFdEntityId() + "'";
//                                }
//                                if (StringUtils.isNotBlank(done.getFdEntityName())) {
//                                    todoSql += " and fd_entity_name = '" + done.getFdEntityName() + "'";
//                                }
//                                if (StringUtils.isNotBlank(done.getFdEntityKey())) {
//                                    todoSql += " and fd_entity_key = '" + done.getFdEntityKey() + "'";
//                                }
//                                if (StringUtils.isNotBlank(done.getFdNotifyId())) {
//                                    todoSql += " and fd_notify_id = '" + done.getFdNotifyId() + "'";
//                                }
//                                if (StringUtils.isNotBlank(done.getFdParameter1())) {
//                                    todoSql += " and fd_parameter1 = '" + done.getFdParameter1() + "'";
//                                }
//                                if (StringUtils.isNotBlank(done.getFdParameter2())) {
//                                    todoSql += " and fd_parameter2 = '" + done.getFdParameter2() + "'";
//                                }
//                                Query<Long> todoQuery = session.createQuery(todoSql, Long.class);
//                                long todoCount = todoQuery.getSingleResult();
//                                if (todoCount > 0) {
//                                    log.warn("待办已存在，无需补发！done={}", JSONObject.toJSONString(done));
//                                    continue;
//                                }
//                                SysNotifyTodo todo = new SysNotifyTodo();
//                                BeanUtils.copyProperties(todo, done);
//                                session.save(todo);
//                                insertCount++;
//                            }
//                        }
//                    }
//                    doneCount += doneList.size();
//                    log.info("checked {}/{}, error({}), insert({})", doneCount, total, errorCount, insertCount);
//                    // 提交事务
//                    session.getTransaction().commit();
//                    if (doneList.size() < pageSize) {
//                        break;
//                    }
//                    offset += pageSize;
//                } catch (Throwable thr) {
//                    log.error("Exec check error!", thr);
//                    break;
//                }
//            }
//
//        } catch (Exception e) {
//            // 回滚事务
//            if (session != null) {
//                session.getTransaction().rollback();
//                log.error("Check done rollback!", e);
//            } else {
//                log.error("Check done error!", e);
//            }
//        } finally {
//            // 关闭session
//            if (session != null) {
//                session.close();
//            }
//        }
//    }

    private static void byPerson() {
        // 加载参数
        String startTime = properties.getProperty("startTime");
        String endTime = properties.getProperty("endTime");
        boolean insert = Boolean.parseBoolean(properties.getProperty("insert"));
        int batchSize = Integer.parseInt(properties.getProperty("batchSize"));
        int todoType = Integer.parseInt(properties.getProperty("todoType"));
        String targets = properties.getProperty("targets");

        Session session = null;
        try {
            // 打开session
            session = sessionFactory.openSession();

            // 白名单人员登录名转换为id
            List<String> loginNames = Arrays.asList(targets.split(","));
            List<String> personIds = session.createQuery("select fdId from SysOrgElementSummary where fdLoginName in (?1)", String.class)
                    .setParameter(1, loginNames).list();
            String byPersonSql = "";
            if (CollectionUtils.isNotEmpty(personIds)) {
//                personIds = summaryList.stream().map(SysOrgElementSummary::getFdId).collect(Collectors.toList());
                byPersonSql = " and fdOwnerId in (:personIds)";
            }

            // 查询已办数据
            Query<Long> totalQuery = session.createQuery("select count(1) from SysNotifyDone where fdType = "
                    + todoType + byPersonSql + " and fdFinishTime >= :startTime and fdFinishTime < :endTime", Long.class);
            if (StringUtils.isNotBlank(byPersonSql)) {
                totalQuery.setParameter("personIds", personIds);
            }
            totalQuery.setParameter("startTime", Date.from(LocalDateTime.parse(startTime, DEFAULT_DATE_FORMATTER)
                    .atZone(ZoneId.systemDefault())
                    .toInstant()));
            totalQuery.setParameter("endTime", Date.from(LocalDateTime.parse(endTime, DEFAULT_DATE_FORMATTER)
                    .atZone(ZoneId.systemDefault())
                    .toInstant()));
            long total = totalQuery.getSingleResult();
            Query<SysNotifyDone> query = session.createQuery("from SysNotifyDone where fdType = "
                    + todoType + byPersonSql + " and fdFinishTime >= :startTime and fdFinishTime < :endTime", SysNotifyDone.class);
            if (StringUtils.isNotBlank(byPersonSql)) {
                query.setParameter("personIds", personIds);
            }
            query.setParameter("startTime", Date.from(LocalDateTime.parse(startTime, DEFAULT_DATE_FORMATTER)
                    .atZone(ZoneId.systemDefault())
                    .toInstant()));
            query.setParameter("endTime", Date.from(LocalDateTime.parse(endTime, DEFAULT_DATE_FORMATTER)
                    .atZone(ZoneId.systemDefault())
                    .toInstant()));
            int offset = 0;
            int pageSize = batchSize;
            int doneCount = 0;
            int errorCount = 0;
            int insertCount = 0;
            while (true) {
                // 开启事务
                try {
                    session.beginTransaction();
                    query.setFirstResult(offset);
                    query.setMaxResults(pageSize);
                    List<SysNotifyDone> doneList = query.list();
                    for (SysNotifyDone done : doneList) {
                        // 检查sys_notify_original
                        String sql = "select count(1) from SysNotifyOriginal where fd_command = 'done'";
                        if (StringUtils.isNotBlank(done.getFdAppName())) {
                            sql += " and fd_app_name = '" + done.getFdAppName() + "'";
                        }
                        if (StringUtils.isNotBlank(done.getFdModuleName())) {
                            sql += " and fd_module_name = '" + done.getFdModuleName() + "'";
                        }
                        if (StringUtils.isNotBlank(done.getFdEntityId())) {
                            sql += " and fd_entity_id = '" + done.getFdEntityId() + "'";
                        }
                        if (StringUtils.isNotBlank(done.getFdEntityName())) {
                            sql += " and fd_entity_name = '" + done.getFdEntityName() + "'";
                        }
                        Query<Long> oriQuery = session.createQuery(sql, Long.class);
                        long count = oriQuery.getSingleResult();
                        if (count <= 0) {
                            errorCount++;
                            if (insert) {
                                // 检查sys_notify_todo
                                String todoSql = "select count(1) from SysNotifyTodo where fdOwnerId = '" + done.getFdOwnerId() + "'";
                                if (StringUtils.isNotBlank(done.getFdAppName())) {
                                    todoSql += " and fd_app_name = '" + done.getFdAppName() + "'";
                                }
                                if (StringUtils.isNotBlank(done.getFdModuleName())) {
                                    todoSql += " and fd_module_name = '" + done.getFdModuleName() + "'";
                                }
                                if (StringUtils.isNotBlank(done.getFdEntityId())) {
                                    todoSql += " and fd_entity_id = '" + done.getFdEntityId() + "'";
                                }
                                if (StringUtils.isNotBlank(done.getFdEntityName())) {
                                    todoSql += " and fd_entity_name = '" + done.getFdEntityName() + "'";
                                }
                                if (StringUtils.isNotBlank(done.getFdEntityKey())) {
                                    todoSql += " and fd_entity_key = '" + done.getFdEntityKey() + "'";
                                }
                                if (StringUtils.isNotBlank(done.getFdNotifyId())) {
                                    todoSql += " and fd_notify_id = '" + done.getFdNotifyId() + "'";
                                }
                                if (StringUtils.isNotBlank(done.getFdParameter1())) {
                                    todoSql += " and fd_parameter1 = '" + done.getFdParameter1() + "'";
                                }
                                if (StringUtils.isNotBlank(done.getFdParameter2())) {
                                    todoSql += " and fd_parameter2 = '" + done.getFdParameter2() + "'";
                                }
                                Query<Long> todoQuery = session.createQuery(todoSql, Long.class);
                                long todoCount = todoQuery.getSingleResult();
                                if (todoCount > 0) {
                                    log.warn("待办已存在，无需补发！doneId={}", done.getFdId());
                                    continue;
                                }
                                SysNotifyTodo todo = new SysNotifyTodo();
                                BeanUtils.copyProperties(todo, done);
                                session.save(todo);
                                insertCount++;
                            }
                        }
                    }
                    doneCount += doneList.size();
                    log.info("checked {}/{}, error({}), insert({})", doneCount, total, errorCount, insertCount);
                    // 提交事务
                    session.getTransaction().commit();
                    if (doneList.size() < pageSize) {
                        break;
                    }
                    offset += pageSize;
                } catch (Throwable thr) {
                    log.error("Exec check error!", thr);
                    break;
                }
            }

        } catch (Exception e) {
            // 回滚事务
            if (session != null) {
                session.getTransaction().rollback();
                log.error("Check done rollback!", e);
            } else {
                log.error("Check done error!", e);
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
            byPerson();
        } finally {
            sessionFactory.close();
            log.info("Stop!");
        }
    }
}
