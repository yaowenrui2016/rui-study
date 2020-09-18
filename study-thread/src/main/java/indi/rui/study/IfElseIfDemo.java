package indi.rui.study;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2019-11-07
 */
@Slf4j
public class IfElseIfDemo {
    public static void main(String[] args) {
        // 互斥的
        long num = System.currentTimeMillis();
        if (num % 2 == 0) {
            log.info("[{}]被2整除", num);
        } else if (num % 3 == 0) {
            log.info("[{}]被3整除", num);
        } else {
            log.info("[{}]既不能被2也不能被3整除", num);
        }
    }
}
