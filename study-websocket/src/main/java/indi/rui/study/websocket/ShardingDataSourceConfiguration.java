package indi.rui.study.websocket;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.NoneShardingStrategyConfiguration;
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
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(properties.getDriverClassName());
        druidDataSource.setUrl(properties.getUrl());
        druidDataSource.setUsername(properties.getUsername());
        druidDataSource.setPassword(properties.getPassword());

        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("ds0", druidDataSource);

        // 配置sysmsg表规则及分表策略
        TableRuleConfiguration sysmsgTableRuleConfig = new TableRuleConfiguration("sysmsg", "ds0.sysmsg_${0..9}");
        sysmsgTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("fd_id", "sysmsg_${fd_id % 10}"));
//        // 配置person表规则及分表策略
//        TableRuleConfiguration personTableRuleConfig = new TableRuleConfiguration("person", "ds0.person_${0..9}");
//        personTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("fd_id", "person_${fd_id % 10}"));

        // 配置分片规则
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.setDefaultDataSourceName("ds0");
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new NoneShardingStrategyConfiguration());
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new NoneShardingStrategyConfiguration());
        shardingRuleConfig.getTableRuleConfigs().add(sysmsgTableRuleConfig);
//        shardingRuleConfig.getTableRuleConfigs().add(personTableRuleConfig);

        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new Properties());
    }

}
