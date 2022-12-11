package indi.rui.study.something.LinkedHasMap;

import java.util.*;

/**
 * @author: yaowr
 * @create: 2022-12-09
 */
public class Test {
    public static void main(String[] args) {

//        Map<String, Integer> map = new HashMap<>();
        Map<String, Integer> map = new LinkedHashMap<>();
        int count = 1;
        for (String key : Arrays.asList("zhangs", "lis", "wangw", "yaowr", "lib")) {
            map.put(key, count++);
        }
        // 方式一：keySet遍历
        System.out.println(Arrays.toString(map.keySet().toArray()));
        System.out.println(Arrays.toString(map.values().toArray()));
        // 方式二：iterator遍历
        List<String> keys = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            keys.add(entry.getKey());
            values.add(entry.getValue());
        }
        System.out.println(Arrays.toString(keys.toArray()));
        System.out.println(Arrays.toString(values.toArray()));
    }
}
