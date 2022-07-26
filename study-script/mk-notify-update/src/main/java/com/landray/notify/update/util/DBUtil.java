package com.landray.notify.update.util;


import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author: yaowr
 * @create: 2021-12-01
 */
public class DBUtil {

    private static final String DEFAULT_SCHEMA = "mysql";

    enum DBType {
        MYSQL,
        ORACLE,
        SQLSERVER;

        static DBType of(String dbName) {
            for (DBType dbType : DBType.values()) {
                if (dbType.name().equalsIgnoreCase(dbName)) {
                    return dbType;
                }
            }
            return null;
        }
    }

    /**
     * 检查表是否存在
     *
     * @param entityManager
     * @param database      '
     * @param table
     * @return
     */
    public static boolean checkExists(EntityManager entityManager, String database, String table) {
        DBType dbType = DBType.of(database);
        boolean exists = false;
        switch (dbType) {
            case MYSQL:
                exists = getMysqlCheck(entityManager, table);
                break;
            case SQLSERVER:
                exists = getSqlServerCheck(entityManager, table);
                break;
            case ORACLE:
                exists = getOracleCheck(entityManager, table);
                break;
        }
        return exists;
    }


    /**
     * 检查MySQL数据库下是否存在表
     *
     * @param entityManager
     * @param table
     * @return
     */
    private static boolean getMysqlCheck(EntityManager entityManager, String table) {
        String sql = "select count(1) from information_schema.tables where table_schema= ? and table_name= ?";
        BigInteger result = (BigInteger) entityManager.createNativeQuery(sql)
                .setParameter(1, DEFAULT_SCHEMA)
                .setParameter(2, table)
                .getSingleResult();
        return result.intValue() > 0;
    }

    /**
     * 检查SqlServer数据库下是否存在表
     *
     * @param entityManager
     * @param tableName
     * @return
     */
    private static boolean getSqlServerCheck(EntityManager entityManager, String tableName) {
        String sql = "select count(1) from sysObjects where Id=OBJECT_ID(N'" + tableName + "') and xtype='U'";
        int result = (Integer) entityManager.createNativeQuery(sql)
                .getSingleResult();
        return result > 0;
    }

    /**
     * 检查Oracle数据库下是否存在表
     *
     * @param entityManager
     * @param tableName
     * @return
     */
    private static boolean getOracleCheck(EntityManager entityManager, String tableName) {
        String sql = "select count(1) from user_tables where table_name =upper('" + tableName + "')";
        BigDecimal result = (BigDecimal) entityManager.createNativeQuery(sql)
                .getSingleResult();
        return result.intValue() > 0;
    }
}
