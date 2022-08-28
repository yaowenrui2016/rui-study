package indi.rui.study.something.LocalDateTime;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author: yaowr
 * @create: 2022-08-12
 */
public class Test {
    public static void main(String[] args) {
        testDateToLocalDateTime();
//        testLocalDateTimePlus();
    }

    private static void testLocalDateTimePlus() {
        LocalDateTime beginDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = LocalDateTime.parse("2022-10-01 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        long end = endDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        int count = 0;
        for (;
             beginDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() < end;
             beginDateTime = beginDateTime.plusDays(1)) {
            System.out.println(beginDateTime.getYear() + "-" + beginDateTime.getMonthValue() + "-" + beginDateTime.getDayOfMonth());
            count++;
        }
        System.out.println("总共：" + count + " 天");
    }

    private static void testDateToLocalDateTime() {
        LocalDateTime dateTime = LocalDateTime.from(new Date().toInstant().atZone(ZoneId.systemDefault()));
        System.out.println(dateTime.getYear());
        System.out.println(dateTime.getMonth().getValue());
        System.out.println(dateTime.getDayOfMonth());
        System.out.println(dateTime.getDayOfWeek().getValue());
    }
}
