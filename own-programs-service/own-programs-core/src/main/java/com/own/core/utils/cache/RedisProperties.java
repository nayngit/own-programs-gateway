package com.own.core.utils.cache;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "redis.cache")
public class RedisProperties {

	private String clusterNodes;
	private Integer commandTimeout;
	
	private GenericObjectPoolConfig pool;
	public String getClusterNodes() {
		return clusterNodes;
	}
	public void setClusterNodes(String clusterNodes) {
		this.clusterNodes = clusterNodes;
	}
	public Integer getCommandTimeout() {
		return commandTimeout;
	}
	public void setCommandTimeout(Integer commandTimeout) {
		this.commandTimeout = commandTimeout;
	}
	public GenericObjectPoolConfig getPool() {
		return pool;
	}
	public void setPool(GenericObjectPoolConfig pool) {
		this.pool = pool;
	}
}
