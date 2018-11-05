package com.own.common.utils;

import java.security.MessageDigest;

/**
 * 类 名: MD5<br/>
 * 描 述: MD5 加密工具类<br/>
 * 作 者: 展望<br/>
 * 创 建： 2018-04-26<br/>
 *
 * 历 史: (版本) 作者 时间 注释
 */
public class MD5 {
	
	/** 0xff */
	private static final int OX_FF = 0xff;
	
	/** 16 */
	private static final int SIXTEEN = 16;

	/**
	 * 描 述：加密方法<br/>
	 * 作 者：郭昕<br/>
	 * 历 史: (版本) 作者 时间 注释 <br/>
	 * @param s 待加密字符串
	 * @return 加密字符串
	 */
    public final static String crypto(final String s) {
        try {
            byte[] btInput = s.getBytes("UTF-8");
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(btInput);
            byte[] md = mdInst.digest();
            StringBuffer sb = new StringBuffer();
            for (byte element : md) {
                int val = element & OX_FF;
                if (val < SIXTEEN) {
                    sb.append("0");
                }
                sb.append(Integer.toHexString(val));
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }
    public static void main(String[] args) {
		System.out.println(MD5.crypto("12345687"));
	}
}
