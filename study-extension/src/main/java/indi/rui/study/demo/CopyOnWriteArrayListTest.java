package indi.rui.study.demo;

import java.util.concurrent.CopyOnWriteArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2019-10-21
 */
public class CopyOnWriteArrayListTest {

    public static void main(String[] args) {
        CopyOnWriteArrayList<Person> cow = new CopyOnWriteArrayList();

        Person p1 = new Person("001", "Liming");
        cow.add(p1);
        Person p1x = cow.set(0, p1);
        Person p2 = new Person("002", "Zhanghua");
        Person p2x = cow.set(0, p2);
        System.out.println(p1x == p2x);

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Person {
        private String id;
        private String name;
    }
}
