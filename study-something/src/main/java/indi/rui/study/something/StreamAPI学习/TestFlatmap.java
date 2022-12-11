package indi.rui.study.something.StreamAPI学习;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: yaowr
 * @create: 2021-11-05
 */
public class TestFlatmap {
    public static void main(String[] args) {
//        streamSort();
        sort();
    }

    private static void sort() {
        List<String> listA = Arrays.asList("c", "a", "b");
        System.out.println(Arrays.toString(listA.toArray()));
        Collections.sort(listA);
        System.out.println(Arrays.toString(listA.toArray()));
    }

    private static void streamSort() {
        List<String> listA = Arrays.asList("c", "a", "b");
        List<String> listB = listA.stream().sorted().collect(Collectors.toList());
        System.out.println(Arrays.toString(listA.toArray()));
        System.out.println(Arrays.toString(listB.toArray()));
    }
}
