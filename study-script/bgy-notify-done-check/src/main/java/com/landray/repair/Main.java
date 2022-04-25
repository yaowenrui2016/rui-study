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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

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
            log.info("load properties: {}", JSONObject.toJSONString(properties, SerializerFeature.PrettyFormat));
        } catch (IOException e) {
            throw new RuntimeException("加载bootstrap.properties失败");
        }
    }


    /**
     * 主方法
     *
     * @param args
     */
    public static void main(String[] args) {
        // 加载参数
        String operation = properties.getProperty("operation");
        log.info("Application running [{}]", operation);
        try {
            switch (operation) {
                case "supplyTodo":
                    supplyTodo();
                    break;
                case "deleteTodoById":
                    deleteTodoById();
                    break;
                case "deleteDuplicateDone":
                    deleteDuplicateDone();
                    break;
                default:
                    log.error("Not support operation [{}]", operation);
            }
        } finally {
            sessionFactory.close();
            log.info("Application stopped.");
        }
    }

    // =========================== 业务方法 ======================== //

    private static void supplyTodo() {
        // 加载参数
        boolean onlyCheck = Boolean.parseBoolean(properties.getProperty("supplyTodo.onlyCheck", "true"));
        int todoType = Integer.parseInt(properties.getProperty("supplyTodo.todoType", "1"));
        int batchSize = Integer.parseInt(properties.getProperty("supplyTodo.batchSize", "1000"));
        String startTime = properties.getProperty("supplyTodo.startTime", "0001-01-01 00:00:00");
        String endTime = properties.getProperty("supplyTodo.endTime", DEFAULT_DATE_FORMATTER.format(Instant.now().atZone(ZoneId.systemDefault())));
        String targets = properties.getProperty("supplyTodo.targets");
        String appNames = properties.getProperty("supplyTodo.appNames");

        Session session = null;
        try {
            // 打开session
            session = sessionFactory.openSession();

            // 白名单人员登录名转换为id
            String byPersonSql = "";
            List<String> personIds = null;
            if (StringUtils.isNotBlank(targets)) {
                List<String> loginNames = Arrays.asList(targets.split(","));
                if (CollectionUtils.isNotEmpty(loginNames)) {
                    personIds = session.createQuery("" +
                            "select fdId from SysOrgElementSummary where fdLoginName in (?1) and fdIsAvailable = 1", String.class)
                            .setParameter(1, loginNames).list();
                }
                if (CollectionUtils.isNotEmpty(personIds)) {
                    byPersonSql = " and fdOwnerId in (:personIds)";
                } else {
                    log.warn("Abort executing because invalid targets '{}'", targets);
                    return;
                }
            }

            // 白名单系统
            String byAppNameSql = "";
            List<String> appNameList = null;
            if (StringUtils.isNotBlank(appNames)) {
                appNameList = Arrays.asList(appNames.split(","));
                if (CollectionUtils.isNotEmpty(appNameList)) {
                    byAppNameSql = " and fdAppName in (:appNameList)";
                }
            }

            // 查询已办数据
            Query<Long> totalQuery = session.createQuery("select count(1) from SysNotifyDone where fdType = "
                    + todoType + byPersonSql + byAppNameSql + " and fdFinishTime >= :startTime and fdFinishTime < :endTime", Long.class);
            if (StringUtils.isNotBlank(byPersonSql)) {
                totalQuery.setParameter("personIds", personIds);
            }
            if (StringUtils.isNotBlank(byAppNameSql)) {
                totalQuery.setParameter("appNameList", appNameList);
            }
            totalQuery.setParameter("startTime", Date.from(LocalDateTime.parse(startTime, DEFAULT_DATE_FORMATTER)
                    .atZone(ZoneId.systemDefault())
                    .toInstant()));
            totalQuery.setParameter("endTime", Date.from(LocalDateTime.parse(endTime, DEFAULT_DATE_FORMATTER)
                    .atZone(ZoneId.systemDefault())
                    .toInstant()));
            long total = totalQuery.getSingleResult();
            Query<SysNotifyDone> query = session.createQuery("from SysNotifyDone where fdType = "
                    + todoType + byPersonSql + byAppNameSql + " and fdFinishTime >= :startTime and fdFinishTime < :endTime", SysNotifyDone.class);
            if (StringUtils.isNotBlank(byPersonSql)) {
                query.setParameter("personIds", personIds);
            }
            if (StringUtils.isNotBlank(byAppNameSql)) {
                query.setParameter("appNameList", appNameList);
            }
            query.setParameter("startTime", Date.from(LocalDateTime.parse(startTime, DEFAULT_DATE_FORMATTER)
                    .atZone(ZoneId.systemDefault())
                    .toInstant()));
            query.setParameter("endTime", Date.from(LocalDateTime.parse(endTime, DEFAULT_DATE_FORMATTER)
                    .atZone(ZoneId.systemDefault())
                    .toInstant()));
            List<String> suppliedIds = new ArrayList<>();
            List<String> rejectIds = new ArrayList<>();
            int offset = 0;
            int pageSize = batchSize;
            int doneCount = 0;
            int errorCount = 0;
            int insertCount = 0;
            int rejectCount = 0;
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
                            if (!onlyCheck) {
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
                                    rejectIds.add(done.getFdId());
                                    rejectCount++;
                                    continue;
                                }
                                // 补待办
                                SysNotifyTodo todo = new SysNotifyTodo();
                                BeanUtils.copyProperties(todo, done);
                                session.save(todo);
                                // 删已办
//                                session.delete(done);
                                suppliedIds.add(todo.getFdId());
                                insertCount++;
                            }
                        }
                    }
                    doneCount += doneList.size();
                    log.info("checked({}/{}), error({}), reject({}), supplied({})",
                            doneCount, total, errorCount, rejectCount,
                            insertCount);
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
            log.info("...... rejectIds({})={} ......", rejectCount, JSONObject.toJSONString(rejectIds));
            log.info("...... suppliedIds({})={} ......", insertCount, JSONObject.toJSONString(suppliedIds));
        } catch (Exception e) {
            // 回滚事务
            if (session != null) {
                session.getTransaction().rollback();
                log.error("Supply todo rollback!", e);
            } else {
                log.error("Supply todo error!", e);
            }
        } finally {
            // 关闭session
            if (session != null) {
                session.close();
            }
        }
    }

    private static void deleteTodoById() {
        // 加载参数
        String delTodoIds = properties.getProperty("deleteTodoById.todoIds");
        String[] delTodoArray = new String[0];
        if (StringUtils.isNotBlank(delTodoIds)) {
            delTodoArray = delTodoIds.split(",");
        }
        Session session = null;
        try {
            // 打开session
            session = sessionFactory.openSession();
            // 开启事务
            try {
                session.beginTransaction();
                for (String todoId : delTodoArray) {
                    SysNotifyTodo todo = new SysNotifyTodo();
                    todo.setFdId(todoId);
                    session.delete(todo);
                }
                log.info("Success delete todo({})", delTodoArray.length);
                // 提交事务
                session.getTransaction().commit();
            } catch (Throwable thr) {
                log.error("Delete todo error!", thr);
            }
        } catch (Exception e) {
            // 回滚事务
            if (session != null) {
                session.getTransaction().rollback();
                log.error("Delete todo rollback!", e);
            } else {
                log.error("Delete todo error!", e);
            }
        } finally {
            // 关闭session
            if (session != null) {
                session.close();
            }
        }
    }

    private static void deleteDuplicateDone() {
        // 加载参数
        boolean onlyCheck = Boolean.parseBoolean(properties.getProperty("deleteDuplicateDone.onlyCheck", "true"));
        int todoType = Integer.parseInt(properties.getProperty("deleteDuplicateDone.todoType", "1"));
        int batchSize = Integer.parseInt(properties.getProperty("deleteDuplicateDone.batchSize", "1000"));
        String startTime = properties.getProperty("deleteDuplicateDone.startTime", "0001-01-01 00:00:00");
        String endTime = properties.getProperty("deleteDuplicateDone.endTime", DEFAULT_DATE_FORMATTER.format(Instant.now().atZone(ZoneId.systemDefault())));
        String targets = properties.getProperty("deleteDuplicateDone.targets");
        String appNames = properties.getProperty("deleteDuplicateDone.appNames");
        Session session = null;
        try {
            // 打开session
            session = sessionFactory.openSession();

            // 白名单人员登录名转换为id
            String byPersonSql = "";
            List<String> personIds = null;
            if (StringUtils.isNotBlank(targets)) {
                List<String> loginNames = Arrays.asList(targets.split(","));
                if (CollectionUtils.isNotEmpty(loginNames)) {
                    personIds = session.createQuery("" +
                            "select fdId from SysOrgElementSummary where fdLoginName in (?1) and fdIsAvailable = 1", String.class)
                            .setParameter(1, loginNames).list();
                }
                if (CollectionUtils.isNotEmpty(personIds)) {
                    byPersonSql = " and done.fdOwnerId in (:personIds)";
                } else {
                    log.warn("Abort executing because invalid targets '{}'", targets);
                    return;
                }
            }

            // 白名单系统
            String byAppNameSql = "";
            List<String> appNameList = null;
            if (StringUtils.isNotBlank(appNames)) {
                appNameList = Arrays.asList(appNames.split(","));
                if (CollectionUtils.isNotEmpty(appNameList)) {
                    byAppNameSql = " and done.fdAppName in (:appNameList)";
                }
            }

            // 查询已办是否有和待办id相同的数据
            Query<String> checkDoneQuery = session.createQuery("select fdId from SysNotifyDone done where done.fdType = "
                    + todoType + byPersonSql + byAppNameSql +
                    " and done.fdFinishTime >= :startTime and done.fdFinishTime < :endTime and exists(select 1 from SysNotifyTodo todo where todo.fdId = done.fdId)", String.class);
            if (StringUtils.isNotBlank(byPersonSql)) {
                checkDoneQuery.setParameter("personIds", personIds);
            }
            if (StringUtils.isNotBlank(byAppNameSql)) {
                checkDoneQuery.setParameter("appNameList", appNameList);
            }
            checkDoneQuery.setParameter("startTime", Date.from(LocalDateTime.parse(startTime, DEFAULT_DATE_FORMATTER)
                    .atZone(ZoneId.systemDefault())
                    .toInstant()));
            checkDoneQuery.setParameter("endTime", Date.from(LocalDateTime.parse(endTime, DEFAULT_DATE_FORMATTER)
                    .atZone(ZoneId.systemDefault())
                    .toInstant()));
            List<String> dupDoneIds = checkDoneQuery.list();
            // 是否仅查看
            long deleteCount = 0;
            if (!onlyCheck && CollectionUtils.isNotEmpty(dupDoneIds)) {
                for (int i = 0; i < dupDoneIds.size(); i += batchSize) {
                    List<String> batchIds = dupDoneIds.subList(i, Math.min(i + batchSize, dupDoneIds.size()));
                    // 开启事务
                    try {
                        session.beginTransaction();
                        deleteCount += session.createQuery("delete SysNotifyDone where fdId in (:ids)")
                                .setParameter("ids", batchIds).executeUpdate();
                        // 提交事务
                        session.getTransaction().commit();
                        log.info("success delete duplicate done({}/{})", deleteCount, dupDoneIds.size());
                    } catch (Throwable thr) {
                        // 回滚事务
                        if (session != null) {
                            session.getTransaction().rollback();
                            log.error("Delete duplicate done rollback!", thr);
                        } else {
                            log.error("Delete duplicate done error!", thr);
                        }
                    }
                }
            } else {
                log.info("find duplicate done({})", dupDoneIds.size());
            }
        } catch (Exception e) {
            // 回滚事务
            if (session != null) {
                session.getTransaction().rollback();
                log.error("Delete duplicate done rollback!", e);
            } else {
                log.error("Delete duplicate done error!", e);
            }
        } finally {
            // 关闭session
            if (session != null) {
                session.close();
            }
        }
    }
}
