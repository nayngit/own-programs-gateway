package com.own.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.own.base.vo.Result;
import com.own.core.utils.LoggerProxyFactory;
import com.own.core.utils.cache.RedisClient;

@RestController
@RequestMapping("/v1/own")
public class TestController {
	
	private static final Logger LOG = LoggerProxyFactory.getLogger(TestController.class);

	@Resource
	private RedisClient redisClient;
	
	@RequestMapping(value = "/connect.do", method = RequestMethod.GET)
	public Result<String> testGet(){
		Result<String> result = new Result<String>();
		result.setBody("访问成功！");
		result.setSuccess(true);
		redisClient.setString("123", "456", 0);
		LOG.info("[redis测试] redis:{}",new Object[] {redisClient.getString("123")});
		return result;
	}
}
