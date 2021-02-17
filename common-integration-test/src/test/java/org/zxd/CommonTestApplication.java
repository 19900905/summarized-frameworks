package org.zxd;

import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(exclude = RedissonAutoConfiguration.class)
public class CommonTestApplication {
	public static void main(String[] args) {
		SpringApplication.run(CommonTestApplication.class, args);
	}
}