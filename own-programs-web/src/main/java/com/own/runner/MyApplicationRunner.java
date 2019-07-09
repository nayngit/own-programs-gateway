package com.own.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.own.core.utils.GetAccessTokenThread;

@Component
public class MyApplicationRunner implements ApplicationRunner{

	@Override
	public void run(ApplicationArguments arg0) throws Exception {
		// TODO Auto-generated method stub
//		启动定时获取access_token的线程
		new Thread(new GetAccessTokenThread()).start();//获取微信access_token
	}

}
