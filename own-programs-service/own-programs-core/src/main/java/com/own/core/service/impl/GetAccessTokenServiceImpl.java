package com.own.core.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.own.base.contants.PublicNumberContants;
import com.own.base.contants.RedisKeys;
import com.own.base.publicnumber.AccessTokenRes;
import com.own.common.utils.HttpUtils;
import com.own.core.configuration.PublicNumberApiProperties;
import com.own.core.service.IGetAccessTokenService;
import com.own.core.utils.LoggerProxyFactory;
import com.own.core.utils.cache.RedisClient;

@Service
public class GetAccessTokenServiceImpl implements IGetAccessTokenService{
	
	private static final Logger LOG = LoggerProxyFactory.getLogger(GetAccessTokenServiceImpl.class);

	@Resource
	private PublicNumberApiProperties publicNumberApiProperties;
	
	@Resource
	private RedisClient redisClient;
	
	public AccessTokenRes getAccessToken(){
		String url = PublicNumberContants.GET_PUBLIC_NUMBER_ACCESS_TOKEN_URL.replace("APPID", publicNumberApiProperties.getAppid()).replace("APPSECRET", publicNumberApiProperties.getAppsecret());
		String sendGet = HttpUtils.sendGet(url, "UTF-8");
		LOG.info("[获取微信token] 请求返回，sendGet:{}",new Object[] {sendGet});
		AccessTokenRes accessTokenRes = JSON.parseObject(sendGet, AccessTokenRes.class);
		return accessTokenRes;
	}

	@Override
	public String getAccessTokenString() {
		String accessTokenString = redisClient.getString(RedisKeys.getAccessTokenString());
		LOG.info("[获取微信token] 从缓存中取数据，accessTokenString:{}",new Object[] {accessTokenString});
		if(StringUtils.isEmpty(accessTokenString)) {
			AccessTokenRes accessToken = getAccessToken();
			LOG.info("[获取微信token] 调用api，accessToken:{}",new Object[] {JSON.toJSONString(accessToken)});
			accessTokenString = accessToken.getAccess_token();
			redisClient.setString(RedisKeys.getAccessTokenString(), accessTokenString, 7100);
		}
		return accessTokenString;
	}
	
	
}
