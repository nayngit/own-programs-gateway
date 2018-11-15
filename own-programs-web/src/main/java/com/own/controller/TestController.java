package com.own.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.own.base.vo.Result;

@RestController
@RequestMapping("/v1/own")
public class TestController {
	
	private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

	@RequestMapping(value = "/connect.do", method = RequestMethod.GET)
	public Result<String> testGet(){
		LOG.info("[我的项目测试] 请求过来。。。",new Object[]{});
		Result<String> result = new Result<String>();
		result.setBody("访问成功！");
		result.setSuccess(true);
		return result;
	}
}
