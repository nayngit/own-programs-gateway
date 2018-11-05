package com.own.core.configuration.task;

import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;

//@Component
@ConfigurationProperties(prefix="thread.pool.task")
public class ThreadPoolTaskProperties {

	/**
	 * 线程池维护线程的最少数量
	 */
	private int corePoolSize = 10;
	/**
	 * 线程池维护线程所允许的空闲时间
	 */
	private int keepAliveSeconds = 1800;
	/**
	 * 线程池维护线程的最大数量
	 */
	private int maxPoolSize = 500;
	/**
	 * 线程池所使用的缓冲队列
	 */
	private int queueCapacity = 10;
	
	public int getCorePoolSize() {
		return corePoolSize;
	}
	public void setCorePoolSize(int corePoolSize) {
		this.corePoolSize = corePoolSize;
	}
	public int getKeepAliveSeconds() {
		return keepAliveSeconds;
	}
	public void setKeepAliveSeconds(int keepAliveSeconds) {
		this.keepAliveSeconds = keepAliveSeconds;
	}
	public int getMaxPoolSize() {
		return maxPoolSize;
	}
	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}
	public int getQueueCapacity() {
		return queueCapacity;
	}
	public void setQueueCapacity(int queueCapacity) {
		this.queueCapacity = queueCapacity;
	}
}
