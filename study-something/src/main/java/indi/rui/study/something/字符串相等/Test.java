package indi.rui.study.something.字符串相等;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: yaowr
 * @create: 2021-11-03
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("yaowenrui");
        list.add(new String("yaowenrui"));
        list.add(String.valueOf("yaowenrui"));
        list.add(new StringBuffer("yaowenrui").toString());
        log.info("list size: {}", list.size());

        String s1 = new String("yaowenrui");
        String s2 = "yaowenrui";
        log.info("contains s1: {}", list.remove(s1));
        log.info("contains s2: {}", list.remove(s2));

        log.info("list size: {}", list.size());
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            if (s.equals("yaowenrui")) {
                iterator.remove();
            }
        }
        log.info("list size: {}", list.size());
    }
}
