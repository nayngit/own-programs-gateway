package com.own.core.utils;

public class ReqNoUtils {

private static final ThreadLocal<String> requestNo = new ThreadLocal<>(); 
	
	public static void setRequestNo(String no) {
		requestNo.set(no);
	}
	public static String getRequestNo() {
		return requestNo.get();
	}
	public static void clear() {
		requestNo.remove();
	}
}
