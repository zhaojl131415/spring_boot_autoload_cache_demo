package com.zhao.demo;

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
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement(proxyTargetClass = true)
@SpringBootApplication
@MapperScan("com.zhao.demo.mapper")
public class SpringBootAutoloadCacheDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAutoloadCacheDemoApplication.class, args);
	}


	private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
		ApplicationListener<ApplicationReadyEvent> readyListener=new ApplicationListener<ApplicationReadyEvent> () {

			@Override
			public void onApplicationEvent(ApplicationReadyEvent event) {
				ConfigurableApplicationContext context = event.getApplicationContext();
				UserMapper userMapper=context.getBean(UserMapper.class);
				userMapper.allUsers();

				UserBean user = new UserBean();
				user.setStatus(1);
				userMapper.listByCondition(user);
			}

		};
		return builder.sources(SpringBootAutoloadCacheDemoApplication.class).bannerMode(Banner.Mode.OFF).listeners(readyListener);
	}
}
