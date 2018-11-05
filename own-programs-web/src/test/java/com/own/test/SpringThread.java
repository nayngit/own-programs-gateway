package com.own.test;

public class SpringThread implements Runnable{

	private int parameter;
	
	public SpringThread(int parameter){
		this.parameter = parameter;
	}
	
	
	public int getParameter() {
		return parameter;
	}


	public void setParameter(int parameter) {
		this.parameter = parameter;
	}


	@Override
	public void run() {
		
		System.out.println(Thread.currentThread().getName() + ":执行了..." + parameter);
		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
			//e.printStackTrace();
			//Thread.currentThread().interrupt();
		}
		
	}
	
}
