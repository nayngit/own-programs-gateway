package com.own.test;

import java.io.IOException;

import javax.annotation.Resource;

import org.junit.Test;

import com.own.core.configuration.task.MyThreadPoolTaskExecutor;

public class ThreadPoolTaskExecutorTest extends BaseTest{

	@Resource
	private MyThreadPoolTaskExecutor myThreadPoolTaskExecutor;
	
	@Test
	public void test01(){
		
		for (int i = 0; i < 10; i++) {
			SpringThread s = new SpringThread(i);
			myThreadPoolTaskExecutor.execute(s);
		}
		
		System.out.println("main process is finish .....");
		
		try {
			System.in.read();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Test
	public void test02(){
		myThreadPoolTaskExecutor.execute(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("2222222222222");
				
			}
		});
	}
}
