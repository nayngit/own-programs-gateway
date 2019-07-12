package com.own.core.utils.cache;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

@Configuration
public class JedisClusterConfig {

	@Resource
	private RedisProperties redisProperties;
	
	@Bean
	public JedisCluster getJedisCluster(){
		String[] clusterNodes = redisProperties.getClusterNodes().split(",");
		Set<HostAndPort> serverHs = new HashSet<HostAndPort>();
		for(String hostNode:clusterNodes){
			String[] strings = hostNode.split(":");
			HostAndPort hostAndPort = new HostAndPort(strings[0], Integer.valueOf(strings[1]));
			serverHs.add(hostAndPort);
		}
		return new JedisCluster(serverHs,2000,7,redisProperties.getPool());
	}
	
	@Bean
	public ShardedJedisPool getShardedJedisPool(){
		String[] clusterNodes = redisProperties.getClusterNodes().split(",");
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>(); 
		for(String hostNode:clusterNodes){
			String[] strings = hostNode.split(":");
			JedisShardInfo jedisShardInfo = new JedisShardInfo(strings[0], Integer.valueOf(strings[1]));
			shards.add(jedisShardInfo);
		}
		
		ShardedJedisPool shardedJedisPool = new ShardedJedisPool(redisProperties.getPool() , shards);
		return shardedJedisPool ;
	}
	
	public static void main(String[] args) {
		String[] clusterNodes = "188.131.169.171:6001,188.131.169.171:6002,188.131.169.171:6003,188.131.169.171:6004,188.131.169.171:6005,188.131.169.171:6006".split(",");
		Set<HostAndPort> serverHs = new HashSet<HostAndPort>();
		for(String hostNode:clusterNodes){
			String[] strings = hostNode.split(":");
			HostAndPort hostAndPort = new HostAndPort(strings[0], Integer.valueOf(strings[1]));
			serverHs.add(hostAndPort);
		}
		JedisCluster jedisCluster = new JedisCluster(serverHs,2000,10);
		for(int i=0;i<100;i++) {
			jedisCluster.set(i+"", "value"+i);
		}
	}
}
