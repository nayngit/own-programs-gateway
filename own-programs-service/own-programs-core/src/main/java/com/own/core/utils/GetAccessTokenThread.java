package com.own.core.utils;

import javax.annotation.Resource;

import com.own.base.publicnumber.AccessTokenRes;
import com.own.core.configuration.PublicNumberApiProperties;
import com.own.core.service.IGetAccessTokenService;

/**
 * 定时获取微信access_token的线程
 * @author
 *
 */
public class GetAccessTokenThread implements Runnable{

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
				
				System.out.println("token:" + accessToken.getAccess_token());
				if(null != accessToken){
					Thread.sleep((accessToken.getExpires_in() - 200) * 1000); 
				}else{
					Thread.sleep(60 * 1000);
				}
			} catch (Exception e) {
				try {
					Thread.sleep(60 * 1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
	}

}
