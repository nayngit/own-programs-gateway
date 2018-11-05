package com.own.core.configuration.task;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public class MyThreadPoolTaskExecutor extends ThreadPoolTaskExecutor implements InitializingBean{


	public void initThreadpoolTask(ThreadPoolTaskProperties threadPoolTaskProperties){
		setCorePoolSize(threadPoolTaskProperties.getCorePoolSize());
		setKeepAliveSeconds(threadPoolTaskProperties.getKeepAliveSeconds());
		setMaxPoolSize(threadPoolTaskProperties.getMaxPoolSize());
		setQueueCapacity(threadPoolTaskProperties.getQueueCapacity());
	}

	private static final long serialVersionUID = -8520354586746733871L;
}
