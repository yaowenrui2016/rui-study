package indi.rui.study.micro.eureka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@EnableEurekaServer
@SpringBootApplication
public class StudyEurekaServerApplication {

    public static void main(String[] args) {
        long begin = System.currentTimeMillis();
        ConfigurableApplicationContext context = SpringApplication.run(StudyEurekaServerApplication.class, args);
        log.info("系统启动成功：{}秒", (System.currentTimeMillis() - begin) / 1000);
    }
}
