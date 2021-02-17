package org.zxd;

import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication
@SpringBootApplication(exclude = RedissonAutoConfiguration.class)
public class CommonWebFrameworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonWebFrameworkApplication.class, args);
	}
}