package com.landray.notify.update;

import com.landray.notify.update.model.SourceAppModule;
import com.landray.notify.update.util.DBUtil;
import com.landray.notify.update.util.IDGenerator;
import com.landray.notify.update.util.TransactionUtil;
import indi.rui.study.unittest.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;

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

    public static void main(String[] args) {
        migrateSourceAppModule();
    }

    public static void migrateSourceAppModule() {
        try {
            /**
             * 第一个元素：值为1表示旧表存在，需要执行数据迁移；为0表示不存在，不需要执行数据迁移
             * 第二个元素：存放查找到的旧表数据行数
             * 第三个元素：存放迁移成功的数据行数
             */
            String database = PropertiesUtil.getProperty("database");
            String schema = PropertiesUtil.getProperty("schema");
            final int[] report = new int[]{0, 0, 0};
            TransactionUtil.inTransaction(entityManager -> {
                if (!DBUtil.checkExists(entityManager, database, schema, PREVIOUS_TABLE_NAME)) {
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
                        SourceAppModule appModule = new SourceAppModule();
                        appModule.setFdId(IDGenerator.generateID());
                        appModule.setFdAppId(info[0]);
                        appModule.setFdModuleId(info[1]);
                        appModule.setFdSourceId(SOURCE_APP_SOURCE_ID_ADMIN_MANUAL);
                        entityManager.persist(appModule);
                    }
                    report[2] = filteredBaksMap.size();
                }
                // 删除旧表
                entityManager.createNativeQuery("drop table " + PREVIOUS_TABLE_NAME).executeUpdate();
            });

            if (report[0] == 1) {
                log.info("Found [{}] rows in previous table '{}'  , migrate [{}] rows into new table 'sys_notify_source_app_module'.",
                        report[1],
                        PREVIOUS_TABLE_NAME,
                        report[2]
                );
            } else {
                log.info("Not fount previous table '{}'", PREVIOUS_TABLE_NAME);
            }
        } catch (Throwable e) {
            log.error("Migrate source app module exception!", e);
        }
    }
}
