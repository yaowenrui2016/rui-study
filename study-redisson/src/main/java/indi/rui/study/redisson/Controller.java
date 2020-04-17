package indi.rui.study.redisson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.redisson.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2020-01-31
 */
@Slf4j
@RestController
@RequestMapping("redisson")
public class Controller {
    public static final String KEY_PREFIX = "indi:rui:study:redisson:";

    @Autowired
    private RedissonClient redisson;

    @PostMapping("put/{key}")
    public String put(@PathVariable("key") String key) {
        long start = System.currentTimeMillis();
        RLocalCachedMap map = redisson.getLocalCachedMap(KEY_PREFIX.concat(key), LocalCachedOptions.options);
        // RMap map = redisson.getMap(KEY_PREFIX.concat(key));
        map.expire(5, TimeUnit.MINUTES);
        map.clear();
        Map<Integer, Object> tmp = new HashMap<>();
        for (int i = 0; i < 10000; i++) {
            tmp.put(i, i);
        }
        map.putAll(tmp);
        log.info("耗时：【{} 毫秒】", System.currentTimeMillis() - start);
        return key;
    }

    @PostMapping("get/{key}")
    public List<Integer> get(@PathVariable("key") String key) {
        long start = System.currentTimeMillis();
        // RLocalCachedMap<Integer, Object> map = redisson.getLocalCachedMap(KEY_PREFIX.concat(key),
        // LocalCachedOptions.options);
        RMap<Integer, Object> map = redisson.getMap(KEY_PREFIX.concat(key));
        log.info("缓存【{}】的map大小：{}, , 耗时：【{} 毫秒】", key, map.size(), System.currentTimeMillis() - start);
        return map.keySet().stream().sorted().collect(Collectors.toList());
    }

    @PostMapping("set/{key}")
    public String set(@RequestBody Map<Integer, Object> body, @PathVariable("key") String key) {
        long start = System.currentTimeMillis();
        // RLocalCachedMap map = redisson.getLocalCachedMap(KEY_PREFIX.concat(key), LocalCachedOptions.options);
        RMap<Integer, Object> map = redisson.getMap(KEY_PREFIX.concat(key));
        map.putAll(body);
        log.info("缓存【{}】的map大小：{}, 耗时：【{} 毫秒】", key, map.size(), System.currentTimeMillis() - start);
        return JSON.toJSONString(body);
    }

    @PostMapping("getAndInc/{amount}")
    public long getAndInc(@PathVariable int amount) {
        long start = System.currentTimeMillis();
        RAtomicLong count = redisson.getAtomicLong(KEY_PREFIX.concat("test_getAndInc"));
        long begin = count.get();
        count.compareAndSet(begin, begin + amount);
        for (long i = begin; i < amount + begin; i++) {
        }
        log.info("耗时：【{} 毫秒】", System.currentTimeMillis() - start);
        return count.get();
    }

    @PostMapping("addList/{amount}")
    public long addList(@PathVariable int amount) {
        long start = System.currentTimeMillis();
        RList<Integer> list = redisson.getList(KEY_PREFIX.concat("test_addList"));
//        List<Integer> tmp = new ArrayList<>(amount);
        for (int i = 0; i < amount; i++) {
            list.add(i);
        }
//        list.addAll(tmp);
        log.info("耗时：【{} 毫秒】", System.currentTimeMillis() - start);
        return list.size();
    }
}
