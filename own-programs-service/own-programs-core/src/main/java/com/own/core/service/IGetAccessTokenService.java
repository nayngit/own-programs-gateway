package com.own.core.service;

import com.own.base.publicnumber.AccessTokenRes;

public interface IGetAccessTokenService {

	public AccessTokenRes getAccessToken();
	
	public String getAccessTokenString();
}
