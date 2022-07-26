package com.landray.notify.update;

import com.landray.notify.update.model.SourceAppModule;
import com.landray.notify.update.util.DBUtil;
import com.landray.notify.update.util.SessionFactoryHolder;
import com.landray.notify.update.util.TransactionUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: yaowr
 * @create: 2022-07-25
 */
@Slf4j
public class MkNotifySourceAppMigrate {

    private static final String PREVIOUS_TABLE_NAME = "sys_notify_source_m_apps";
    private static final String SOURCE_APP_SOURCE_ID_ADMIN_MANUAL = "Admin Manual";

    private static SessionFactory sessionFactory = SessionFactoryHolder.getInstance();
    private static EntityManager entityManager = SessionFactoryHolder.createEntityManager();

    public static void main(String[] args) {
        migrateSourceAppModule();
    }

    public static void migrateSourceAppModule() {
        try {
            /**
             * 第一个元素：值为1表示旧表存在，为0表示不存在
             * 第二个元素：存放查找到的旧表数据行数
             * 第三个元素：存放迁移成功的数据行数
             */
            Map<String, Object> props = sessionFactory.getProperties();
            String database = (String) props.get("hibernate.database");
            final int[] report = new int[]{0, 0, 0};
            TransactionUtil.inNewTransaction(() -> {
                if (!DBUtil.checkExists(entityManager, database, PREVIOUS_TABLE_NAME)) {
                    return;
                }
                report[0] = 1;
                // 查询备份表
                String sql = "select fd_app_id as \"fdAppId\", fd_module_id as \"fdModuleId\" from " + PREVIOUS_TABLE_NAME;
                List<SourceAppModule> prevSourceAppModuleList = entityManager.createNativeQuery(sql)
                        .unwrap(NativeQueryImpl.class)
                        .setResultTransformer(Transformers.aliasToBean(SourceAppModule.class))
                        .setFirstResult(0)
                        .setMaxResults(1000)
                        .list();
                report[1] = prevSourceAppModuleList.size();
                if (prevSourceAppModuleList.size() > 0) {
                    // 去除appId和moduleId重复的数据
                    Map<String, List<SourceAppModule>> filteredBaksMap = prevSourceAppModuleList.stream()
                            .collect(Collectors.groupingBy(o -> o.getFdAppId() + ":" + o.getFdModuleId()));
                    // 插入数据到新表
                    for (String keyInfo : filteredBaksMap.keySet()) {
                        String[] info = keyInfo.split(":");
                        SourceAppModule vo = new SourceAppModule();
                        vo.setFdAppId(info[0]);
                        vo.setFdModuleId(info[1]);
                        vo.setFdSourceId(SOURCE_APP_SOURCE_ID_ADMIN_MANUAL);
                        add(vo);
                    }
                    report[2] = filteredBaksMap.size();
                }
                // 删除旧表
                entityManager.createNativeQuery("drop table " + PREVIOUS_TABLE_NAME).executeUpdate();
            });

            if (report[0] == 1) {
                log.info("Previous table '{}' found {} rows , migrate {} rows.",
                        PREVIOUS_TABLE_NAME,
                        report[1],
                        report[2]
                );
            } else {
                log.warn("Previous table '{}' not found!", PREVIOUS_TABLE_NAME);
            }
        } catch (Throwable e) {
            log.error("Migrate source app module exception!", e);
        }
    }

    private static void add(SourceAppModule appModule) {
        TransactionUtil.inNewTransaction(() -> {
            entityManager.persist(appModule);
        });
    }
}
