package indi.rui.study.something.集合常见问题;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author: yaowr
 * @create: 2021-11-03
 */
@Slf4j
public class TestMapValues {
    public static void main(String[] args) {
        Map<Integer, String> map =new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put(i, i + "_val");
        }
        log.info("map size: {}", map.size());
        Collection<String> values = map.values();
        log.info("values size: {}", values.size());
        removeSome(values);
        log.info("map size: {}", map.size());
        log.info("values size: {}", values.size());
    }

    private static void removeSome(Collection<String> values) {
        Iterator<String> iterator = values.iterator();
        while (iterator.hasNext()) {
            String value = iterator.next();
            if (value.equals("3_val") || value.equals("6_val")) {
                iterator.remove();
            }
        }
        values.remove("9_val");
    }
}
