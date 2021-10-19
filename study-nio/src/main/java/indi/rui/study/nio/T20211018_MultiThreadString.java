package indi.rui.study.nio;


import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author: yaowr
 * @create: 2021-10-18
 */
public class T20211018_MultiThreadString {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                String a = "snapshot";
                System.out.println(a.hashCode());
            }).start();
        }

    }

    private static Unsafe getUnsafe() throws Throwable {

        Class<?> unsafeClass = Unsafe.class;

        for (Field f : unsafeClass.getDeclaredFields()) {

            if ("theUnsafe".equals(f.getName())) {

                f.setAccessible(true);

                return (Unsafe) f.get(null);

            }

        }

        throw new IllegalAccessException("no declared field: theUnsafe");

    }
}
