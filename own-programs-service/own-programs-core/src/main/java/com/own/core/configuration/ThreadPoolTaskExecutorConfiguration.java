package com.own.core.configuration;

import javax.annotation.Resource;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.own.core.configuration.task.MyThreadPoolTaskExecutor;
import com.own.core.configuration.task.ThreadPoolTaskProperties;

@Configuration
@EnableConfigurationProperties(value=ThreadPoolTaskProperties.class)
public class ThreadPoolTaskExecutorConfiguration {

	@Resource
	private ThreadPoolTaskProperties threadPoolTaskProperties;
	
	@Bean
	public MyThreadPoolTaskExecutor initMpThreadPoolTaskExecutor(){
		
		MyThreadPoolTaskExecutor myThreadPoolTaskExecutor = new MyThreadPoolTaskExecutor();
		myThreadPoolTaskExecutor.initThreadpoolTask(threadPoolTaskProperties);
		return myThreadPoolTaskExecutor;
	}
}
