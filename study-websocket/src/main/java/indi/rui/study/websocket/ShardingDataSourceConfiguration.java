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

//    @Bean
//    public DataSource shardingDataSource(DataSourceProperties properties) throws SQLException {
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setDriverClassName(properties.getDriverClassName());
//        druidDataSource.setUrl(properties.getUrl());
//        druidDataSource.setUsername(properties.getUsername());
//        druidDataSource.setPassword(properties.getPassword());
//
//        Map<String, DataSource> dataSourceMap = new HashMap<>();
//        dataSourceMap.put("ds0", druidDataSource);
//
//        // 配置sysmsg_entity表规则及分表策略
//        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration("sysmsg_entity", "ds0.sysmsg_entity_${0..9}");
//        orderTableRuleConfig.setTableShardingStrategyConfig(new InlineShardingStrategyConfiguration("fd_id", "sysmsg_entity_${fd_id % 10}"));
//
//        // 配置分片规则
//        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
//        shardingRuleConfig.setDefaultDataSourceName("ds0");
//        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new NoneShardingStrategyConfiguration());
//        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new NoneShardingStrategyConfiguration());
//        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);
//
//        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new Properties());
//    }

}
