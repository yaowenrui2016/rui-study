package indi.rui.study.websocket.sharding;

import com.alibaba.druid.pool.DruidDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ComplexShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.NoneShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author: yaowr
 * @create: 2020-06-08
 */
@Configuration
public class ShardingDataSourceConfiguration {

    @Bean
    public DataSource shardingDataSource(DataSourceProperties properties) throws SQLException {

        DataSource druidDataSource = properties.initializeDataSourceBuilder().type(DruidDataSource.class).build();

//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setDriverClassName(properties.getDriverClassName());
//        druidDataSource.setUrl(properties.getUrl());
//        druidDataSource.setUsername(properties.getUsername());
//        druidDataSource.setPassword(properties.getPassword());

        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("ds0", druidDataSource);

        // 配置sysmsg表规则及分表策略
        TableRuleConfiguration sysmsgTableRuleConfig = new TableRuleConfiguration("sysmsg", "ds0.sysmsg_${0..31}");
        sysmsgTableRuleConfig.setTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("fd_id", new TableShardingAlgorithm()));

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.setDefaultDataSourceName("ds0");
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new NoneShardingStrategyConfiguration());
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new NoneShardingStrategyConfiguration());
        shardingRuleConfig.getTableRuleConfigs().add(sysmsgTableRuleConfig);

        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new Properties());
    }

}
