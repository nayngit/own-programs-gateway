package com.own.common.utils;

import java.util.Random;

public class RandomUtils {
	private static final Random random = new Random();
	
	/**
	 * 
	 * getICode<br/>
	 * 描述：获取验证码，length为长度<br/>
	 * 作者：zhanwang <br/>
	 * @param length
	 * @return
	 */
	public static String getICode(int length){
		StringBuilder stringBuilder = new StringBuilder();
		while(length>0){
			stringBuilder.append(random.nextInt(10));
			length--;
		}
		return stringBuilder.toString();
	}
	
	/**
	 * getRandomInt<br/>
	 * 描述：获取min 到 max之间的随机数<br/>
	 * 作者：zhanwang <br/>
	 * @param min
	 * @param max
	 * @return
	 */
	public static Integer getRandomInt(Integer min, Integer max){
		return random.nextInt(max - min) + min;
		//int randNumber = rand.nextInt(MAX - MIN + 1) + MIN;
	}
	
}
