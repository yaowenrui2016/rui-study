package indi.rui.study.websocket.sharding;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author: yaowr
 * @create: 2020-06-22
 */
@Slf4j
public class TableShardingAlgorithm implements PreciseShardingAlgorithm<String> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        String tableName = shardingValue.getLogicTableName() + "_" + (shardingValue.getValue().hashCode() % availableTargetNames.size());
        if (availableTargetNames.contains(tableName)) {
            log.info("=================选择表：" + tableName);
            return tableName;
        }
        log.warn("=================未找到表：" + tableName);
        return null;
    }
}
