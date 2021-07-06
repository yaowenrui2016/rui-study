package indi.rui.study.hibernate.service;

import indi.rui.study.hibernate.entity.StudyHibernateAccountTableShard;

/**
 * @author: yaowr
 * @create: 2021-07-05
 */
public class ThreadLocalTableNameHolder {

    private static final ThreadLocal<Integer> localTableName = new ThreadLocal<>();

    public static void presetTable(Integer suffixNo) {
        Integer oldSuffixNo = localTableName.get();
        if (oldSuffixNo == null) {
            localTableName.set(suffixNo);
        }
    }

    public static Integer getTalbe() {
        return localTableName.get();
    }

    public static void removeTable() {
        localTableName.remove();
    }
}
