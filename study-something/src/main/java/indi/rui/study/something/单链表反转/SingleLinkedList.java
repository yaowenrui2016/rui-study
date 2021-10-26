package indi.rui.study.something.单链表反转;

/**
 * 单链表
 *
 * @author: yaowr
 * @create: 2021-10-26
 */
public class SingleLinkedList<T> {

    private Node<T> head;

    private Node<T> tail;

    private int size;

    public SingleLinkedList() {
        size = 0;
    }

    //=================== 主要api ====================//

    public void add(T value) {
        Node<T> t = tail;
        if (t == null) {
            tail = new Node<>(value);
            head = tail;
        } else {
            t.next = new Node<>(value);
            tail = t.next;
        }
        ++size;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(index + " out of bounds " + (size - 1));
        }
        int count = 0;
        Node<T> p = head;
        while (count != index) {
            p = p.next;
            count++;
        }
        return p.value;
    }

    public T removeFirst() {
        Node<T> p = head;
        if (p != null) {
            head = p.next;
            --size;
            return p.value;
        } else {
            return null;
        }
    }

    public T getFirst() {
        if (head != null) {
            return head.value;
        }
        return null;
    }

    public T getLast() {
        if (tail != null) {
            return tail.value;
        }
        return null;
    }

    public void revers() {
        tail = head;        // 原链表的head变成反转链表的tail
        Node<T> h = head;   // h为遍历原链表的指针
        head = null;        // 反转链表的头部
        while (h != null) {
            Node<T> tmp = h.next;
            h.next = head;
            head = h;
            h = tmp;
        }
    }

    public int getSize() {
        return size;
    }


    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer("[");
        for (Node<T> p = head; p != null; p = p.next) {
            buf.append(p.value).append(", ");
        }
        if (buf.length() > 3) {
            buf.delete(buf.length() - 2, buf.length());
        }
        buf.append("]");
        return buf.toString();
    }

    static class Node<T> {
        Node<T> next;
        T value;

        public Node(T value) {
            this.value = value;
            this.next = null;
        }
    }
}
