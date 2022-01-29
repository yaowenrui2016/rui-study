package indi.rui.study.jdbc;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.function.Function;

/**
 * @author: yaowr
 * @create: 2022-01-26
 */
@Slf4j
public class Main {

    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static {
        Properties props = new Properties();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream inputStream = classLoader.getResourceAsStream("config/bootstrap.properties");
            props.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("加载[bootstrap.properties]配置出错", e);
        }
        driver = props.getProperty("datasource.driver");
        url = props.getProperty("datasource.url");
        username = props.getProperty("datasource.username");
        password = props.getProperty("datasource.password");
    }

    private static <T> T executeQuery(String sql, Function<ResultSet, T> func) {
        return execute(stmt -> {
            ResultSet rs = null;
            try {
                rs = stmt.executeQuery(sql);
                return func.apply(rs);
            } catch (Exception e) {
                log.error("Execute query error!", e);
            } finally {
                // 关闭对象，回收数据库资源
                if (rs != null) { //关闭结果集对象
                    try {
                        rs.close();
                    } catch (SQLException e) {
                        log.error("Close 'ResultSet' error!", e);
                    }
                }
            }
            return null;
        });
    }

    private static int executeUpdate(String sql) {
        Integer rows = execute(stmt -> {
            try {
                return stmt.executeUpdate(sql);
            } catch (Exception e) {
                log.error("Execute query error!", e);
            }
            return null;
        });
        if (rows == null) {
            throw new RuntimeException("Execute update error!");
        }
        return rows;
    }

    private static <T> T execute(Function<Statement, T> func) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();
            if (func != null) {
                return func.apply(stmt);
            }
        } catch (Exception e) {
            log.error("Execute query error!", e);
        } finally {
            if (stmt != null) { // 关闭数据库操作对象
                try {
                    stmt.close();
                } catch (SQLException e) {
                    log.error("Close 'Statement' error!", e);
                }
            }
            if (conn != null) { // 关闭数据库连接对象
                try {
                    if (!conn.isClosed()) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    log.error("Close 'Connection' error!", e);
                }
            }
        }
        return null;
    }

    public static List<JSONObject> querySourceApp() {
        List<JSONObject> notifyList = executeQuery("select * from sys_notify_source_app", rs -> {
            try {
                List<JSONObject> rtnList = new ArrayList<JSONObject>();
                while (rs.next()) {
                    JSONObject obj = new JSONObject();
                    obj.put("fdId", rs.getObject("fd_id"));
                    obj.put("fdTenantId", rs.getObject("fd_tenant_id"));
                    obj.put("fdName", rs.getObject("fd_name"));
                    obj.put("fdCode", rs.getObject("fd_code"));
                    obj.put("fdEnabled", rs.getObject("fd_enabled"));
                    obj.put("fdIsPreset", rs.getObject("fd_is_preset"));
                    obj.put("fdCreatorId", rs.getObject("fd_creator_id"));
                    obj.put("fdSourceId", rs.getObject("fd_source_id"));
                    obj.put("fdCreateTime", rs.getObject("fd_create_time"));
                    obj.put("fdLastModifiedTime", rs.getObject("fd_last_modified_time"));
                    rtnList.add(obj);
                }
                return rtnList;
            } catch (SQLException e) {
                log.error("Process resultSet error!", e);
            }
            return null;
        });
        log.info("Query notify result: {}",
                JSONObject.toJSONString(notifyList, SerializerFeature.PrettyFormat));
        return notifyList;
    }

    public static List<JSONObject> querySourceModule() {
        List<JSONObject> notifyList = executeQuery("select * from sys_notify_source_module", rs -> {
            try {
                List<JSONObject> rtnList = new ArrayList<JSONObject>();
                while (rs.next()) {
                    JSONObject obj = new JSONObject();
                    obj.put("fdId", rs.getObject("fd_id"));
                    obj.put("fdTenantId", rs.getObject("fd_tenant_id"));
                    obj.put("fdName", rs.getObject("fd_name"));
                    obj.put("fdCode", rs.getObject("fd_code"));
                    obj.put("fdDomain", rs.getObject("fd_domain"));
                    obj.put("fdEnabled", rs.getObject("fd_enabled"));
                    obj.put("fdIsPreset", rs.getObject("fd_is_preset"));
                    obj.put("fdCreatorId", rs.getObject("fd_creator_id"));
                    obj.put("fdSourceId", rs.getObject("fd_source_id"));
                    obj.put("fdCreateTime", rs.getObject("fd_create_time"));
                    obj.put("fdLastModifiedTime", rs.getObject("fd_last_modified_time"));
                    rtnList.add(obj);
                }
                return rtnList;
            } catch (SQLException e) {
                log.error("Process resultSet error!", e);
            }
            return null;
        });
        log.info("Query notify result: {}",
                JSONObject.toJSONString(notifyList, SerializerFeature.PrettyFormat));
        return notifyList;
    }

    public static void addSourceModule(List<JSONObject> sourceModules) {
        StringBuilder valuesString = new StringBuilder();
        for (JSONObject obj : sourceModules) {
            valuesString.append("('").append(obj.get("fdId")).append("',");
            valuesString.append(obj.get("fdTenantId")).append(",");
            valuesString.append("'").append(obj.get("fdName")).append("',");
            valuesString.append("'").append(obj.get("fdCode")).append("',");
            if (obj.containsKey("fdDomain")) {
                valuesString.append("'").append(obj.get("fdDomain")).append("',");
            } else {
                valuesString.append("null,");
            }
            valuesString.append(obj.get("fdEnabled")).append(",");
            valuesString.append(obj.get("fdIsPreset")).append(",");
            valuesString.append("'").append(obj.get("fdCreatorId")).append("',");
            valuesString.append("'").append(obj.get("fdSourceId")).append("',");
            valuesString.append(obj.get("fdCreateTime")).append("),");
        }
        valuesString.deleteCharAt(valuesString.length() - 1);
        String sql = "insert into sys_notify_source_module(fd_id, fd_tenant_id, fd_name, fd_code, fd_domain, fd_enabled, fd_is_preset, fd_creator_id, fd_source_id, fd_create_time) values" + valuesString.toString();
        log.info("execute sql: {}", sql);
        int affectRows = executeUpdate(sql);
        log.info("Add modules: {}", affectRows);
    }

    private static void mockAddModules() {
        List<JSONObject> modules = FileUtils.loadJSON4List("modules.json", JSONObject.class);
        addSourceModule(modules);
    }

    public static void main(String[] args) {
        log.info("开始");
//        mockAddModules();
//        querySourceApp();
        querySourceModule();
    }
}
