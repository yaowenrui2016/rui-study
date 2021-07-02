package indi.rui.study.hibernate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringBootApplication
public class

StudyHibernateApplication implements ApplicationListener<ApplicationReadyEvent> {

    public static void main(String[] args) {
        SpringApplication.run(StudyHibernateApplication.class, args);
    }

    @Transactional
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationPreparedEvent) {
        log.info("系统启动成功！");
    }
}
