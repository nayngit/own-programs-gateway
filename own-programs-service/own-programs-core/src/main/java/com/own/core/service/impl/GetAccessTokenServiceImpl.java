package com.own.core.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.own.base.contants.PublicNumberContants;
import com.own.base.publicnumber.AccessTokenRes;
import com.own.common.utils.HttpUtils;
import com.own.core.configuration.PublicNumberApiProperties;
import com.own.core.service.IGetAccessTokenService;

@Service
public class GetAccessTokenServiceImpl implements IGetAccessTokenService{

	@Resource
	private PublicNumberApiProperties publicNumberApiProperties;
	
	public AccessTokenRes getAccessToken(){
		String url = PublicNumberContants.GET_PUBLIC_NUMBER_ACCESS_TOKEN_URL.replace("APPID", publicNumberApiProperties.getAppid()).replace("APPSECRET", publicNumberApiProperties.getAppsecret());
		String sendGet = HttpUtils.sendGet(url, "UTF-8");
		AccessTokenRes accessTokenRes = JSON.parseObject(sendGet, AccessTokenRes.class);
		return accessTokenRes;
	}
}
