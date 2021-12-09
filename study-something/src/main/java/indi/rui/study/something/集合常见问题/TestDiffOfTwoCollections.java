package indi.rui.study.something.集合常见问题;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author: yaowr
 * @create: 2021-12-09
 */
public class TestDiffOfTwoCollections {
    public static void main(String[] args) {
        List<Integer> aList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        List<Integer> bList = new ArrayList<>(Arrays.asList(3, 4, 5, 6, 7));
        Iterator<Integer> aIte = aList.iterator();
        while (aIte.hasNext()) {
            Integer a = aIte.next();
            Iterator<Integer> bIte = bList.iterator();
            while (bIte.hasNext()) {
                Integer b = bIte.next();
                if (a.equals(b)) {
                    aIte.remove();
                    bIte.remove();
                }
            }
        }
        System.err.println("aList: " + Arrays.toString(aList.toArray()));
        System.err.println("bList: " + Arrays.toString(bList.toArray()));
    }
}
