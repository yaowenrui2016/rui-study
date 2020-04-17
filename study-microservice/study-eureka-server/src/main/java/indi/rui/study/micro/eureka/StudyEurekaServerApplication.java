package indi.rui.study.micro.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class StudyEurekaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyEurekaServerApplication.class, args);
    }

}
