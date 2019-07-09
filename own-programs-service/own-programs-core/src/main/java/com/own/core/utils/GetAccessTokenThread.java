package com.own.core.utils;

import javax.annotation.Resource;

import org.slf4j.Logger;

import com.own.base.publicnumber.AccessTokenRes;
import com.own.core.configuration.PublicNumberApiProperties;
import com.own.core.service.IGetAccessTokenService;

/**
 * 定时获取微信access_token的线程
 * @author
 *
 */
public class GetAccessTokenThread implements Runnable{
	
	private static final Logger LOG = LoggerProxyFactory.getLogger(GetAccessTokenThread.class);

	@Resource
	private PublicNumberApiProperties publicNumberApiProperties;
	
	@Resource
	private IGetAccessTokenService getAccessTokenService;
	
	public static AccessTokenRes accessToken = null;
	
	@Override
	public void run() {
		
		while(true){
			try {
				accessToken = getAccessTokenService.getAccessToken();
				
				LOG.info("[获取token] token:{}",new Object[] {accessToken.getAccess_token()});
				if(null != accessToken){
					Thread.sleep((accessToken.getExpires_in() - 200) * 1000); 
				}else{
					Thread.sleep(60 * 1000);
				}
			} catch (Exception e) {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e1) {
					LOG.error("[获取token] 发生异常,e1:{}",new Object[] {e1});
				}
				LOG.error("[获取token] 发生异常,e:{}",new Object[] {e});
			}
		}
	}

}
