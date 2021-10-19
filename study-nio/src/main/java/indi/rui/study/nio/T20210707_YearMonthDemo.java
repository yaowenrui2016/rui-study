package indi.rui.study.nio;

import java.time.LocalDate;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;

/**
 * @author: yaowr
 * @create: 2021-07-07
 */
public class T20210707_YearMonthDemo {
    public static void main(String[] args) {
        System.out.println(Year.now().getValue());
        System.out.println(Year.now().atDay(1)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli());
        System.out.println(LocalDate.now().withDayOfMonth(1).minusMonths(11)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli());
    }
}
