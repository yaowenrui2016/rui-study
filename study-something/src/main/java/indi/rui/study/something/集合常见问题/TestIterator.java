package indi.rui.study.something.集合常见问题;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author: yaowr
 * @create: 2021-11-04
 */
@Slf4j
public class TestIterator {
    public static void main(String[] args) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.put(i, i);
        }
        new Thread(()->{
            Iterator<Integer> iterator1 = map.values().iterator();
            while (iterator1.hasNext()) {
                Integer value = iterator1.next();
                log.info("iterator1 value: {}", value);
                if (value > 5) {
                    iterator1.remove();
                }
            }
        }, "iterator-1").start();

       new Thread(()->{
           Iterator<Integer> iterator2 = map.values().iterator();
           while (iterator2.hasNext()) {
               Integer value = iterator2.next();
               log.info("iterator2 value: {}", value);
           }
       }, "iterator-2").start();
    }
}
