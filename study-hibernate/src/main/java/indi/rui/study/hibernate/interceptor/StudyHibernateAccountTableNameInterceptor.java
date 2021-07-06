package indi.rui.study.hibernate.interceptor;

import indi.rui.study.hibernate.service.ThreadLocalTableNameHolder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.EmptyInterceptor;

/**
 * 替换表名拦截器
 *
 * @author: yaowr
 * @create: 2021-07-01
 */
@Slf4j
public class StudyHibernateAccountTableNameInterceptor extends EmptyInterceptor {

    private static final String ORIGIN_TABLE_NAME = "study_hibernate_account";

    @Override
    public String onPrepareStatement(String sql) {
        if (sql.contains(ORIGIN_TABLE_NAME)) {
            Integer suffixNo = ThreadLocalTableNameHolder.getTalbe();
            if (suffixNo != null) {
                sql = sql.replaceAll(ORIGIN_TABLE_NAME, ORIGIN_TABLE_NAME + "_" + suffixNo);
            }
        }
        log.info("执行SQL：{}", sql);

        return sql;
    }
}
