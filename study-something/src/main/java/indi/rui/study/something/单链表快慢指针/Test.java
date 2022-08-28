package indi.rui.study.something.单链表快慢指针;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2022-08-28
 */
public class Test {

    /**
     * 链表头
     */
    private static Node head;

    public static void main(String[] args) {
        // 初始化链表
        head = new Node(0);
        Node last = head;
        for (int i = 1; i < 10; i++) {
            Node n = new Node(i);
            if (last != null) {
                last.next = n;
            }
            last = n;
        }

        // 遍历链表
        traverse(head);
    }

    private static void traverse(Node head) {
        // 慢指针p
        Node p = head;
        for (Node n = head; n != null; p = p.next) {
            // 快指针n走两步
            n = n.next;
            if (n != null) {
                n = n.next;
            }
        }
        System.out.println("value = " + p.value);
    }


    @Getter
    @Setter
    public static class Node {

        public Node(Integer value) {
            this.value = value;
        }

        /**
         * 节点值
         */
        private Integer value;

        /**
         * 下个节点引用
         */
        private Node next;
    }
}
