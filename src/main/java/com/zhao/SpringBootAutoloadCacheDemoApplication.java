package com.zhao;

import com.zhao.demo.entity.UserBean;
import com.zhao.demo.mapper.UserMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication
@MapperScan("com.zhao.demo.mapper")
public class SpringBootAutoloadCacheDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAutoloadCacheDemoApplication.class, args);
	}
}
