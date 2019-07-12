package com.own.base.contants;

/**
 * 
 * 
 * 类名称：RedisKeys<br/>
 * 类描述：redis keys<br/>
 * 创建人：guoxin<br/>
 * 创建时间：2016年5月30日 下午4:17:07 <br/>
 * 
 * @version
 *
 */
public class RedisKeys {

	/**
	 * 分隔符
	 */
	private static final String SEPARATER = "_";
	
	/**
	 * 公众号accessToken缓存
	 */
	private static final String ACCESS_TOKEN_CACHE = "ACCESS_TOKEN_CACHE";
	
	public static String getAccessTokenString() {
		return ACCESS_TOKEN_CACHE;
	}
}
