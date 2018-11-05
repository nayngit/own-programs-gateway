package com.own.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.own.base.vo.Result;

@RestController
@RequestMapping("/v1/own")
public class TestController {

	@RequestMapping(value = "/connect.do", method = RequestMethod.GET)
	public Result<String> testGet(){
		Result<String> result = new Result<String>();
		result.setBody("访问成功！");
		result.setSuccess(true);
		return result;
	}
}
