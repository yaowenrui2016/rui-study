package indi.rui.study.hibernate.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.EmptyInterceptor;

/**
 * @author: yaowr
 * @create: 2021-07-01
 */
@Slf4j
public class StudyHibernateAccountTableNameInterceptor extends EmptyInterceptor {

    @Override
    public String onPrepareStatement(String sql) {
        if (sql.contains("study_hibernate_account")) {
            sql = sql.replaceAll("study_hibernate_account", "study_hibernate_account_202105");
        }
        log.info("执行SQL：{}", sql);

        return sql;
    }
}
