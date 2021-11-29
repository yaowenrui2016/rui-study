package indi.rui.study.hibernate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class

StudyHibernateApplication {

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        SpringApplication.run(StudyHibernateApplication.class, args);
        long end = System.currentTimeMillis();
        log.info("系统启动成功！耗时:[{}秒]", (end - begin) / 1000f);
    }
}
