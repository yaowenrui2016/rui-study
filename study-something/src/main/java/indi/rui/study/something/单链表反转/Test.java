package indi.rui.study.something.单链表反转;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Random;

/**
 * @author: yaowr
 * @create: 2021-10-26
 */
@Slf4j
public class Test {
    private static final int LEG = 100;

    public static void main(String[] args) {
        // 添加到链表
        SingleLinkedList<Integer> list = new SingleLinkedList<>();
        for (int i = 0; i < LEG; i++) {
            list.add(i);
        }
        log.info("原链表: size={}, head={}, tail={}, list={}",
                list.getSize(),
                list.getFirst(),
                list.getLast(),
                list.toString());
        // 随机访问链表
        Random random = null;
        try {
            random = new Random(System.currentTimeMillis());
            int idx = LEG > 0 ? random.nextInt(LEG) : 0;
            log.info("get index [{}]={}", idx, list.get(idx));
        } catch (Exception e) {
            log.error("test exception", e);
        }
        // 移除链表头部元素
        try {
            int b;
            int amount = (b = LEG / 2) > 0 ? random.nextInt(b) : 1;
            int[] removedArray = new int[amount];
            for (int i = 0; i < amount; i++) {
                removedArray[i] = list.removeFirst();
            }
            log.info("removed: amount={}, array={}", amount, Arrays.toString(removedArray));
        } catch (Exception e) {
            log.error("test exception", e);
        }
        // 链表反转
        list.revers();
        log.info("反转链表: size={}, head={}, tail={}, list={}",
                list.getSize(),
                list.getFirst(),
                list.getLast(),
                list.toString());
    }
}
