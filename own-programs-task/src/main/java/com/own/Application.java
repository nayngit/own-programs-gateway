package com.own;

import java.io.PrintStream;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.weein.wxxcl.dao")
@ComponentScan(basePackages={"com.weein"}/*必须写，否则默认此类的 package*/)
public class Application extends SpringBootServletInitializer {

	
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Application.class);
        application.setBanner(new Banner() {
			@Override
			public void printBanner(Environment arg0, Class<?> arg1,
					PrintStream out) {
				out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
			}
		});
        application.run(args);
    }

}
