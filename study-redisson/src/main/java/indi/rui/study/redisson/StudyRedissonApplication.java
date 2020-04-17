package indi.rui.study.redisson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@EnableAspectJAutoProxy
@SpringBootApplication
public class StudyRedissonApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyRedissonApplication.class, args);
	}

}
