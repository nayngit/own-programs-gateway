package com.own.core.utils.pressure;

public class PressureTestUtils {

	private static final ThreadLocal<Boolean> PRESSURE_TEST_FLAG = new ThreadLocal<Boolean>();
	
	private static final String REDIS_KEY_START_P="STPRESSURE_";
	
	private static final String DB_TABLE_START_P="__TEST_";
	
	public static void startPressureTest() {
		PRESSURE_TEST_FLAG.set(true);
	}
	public static boolean isPressureTest() {
		Boolean b = PRESSURE_TEST_FLAG.get();
		return b==null?false:b;
	}
	public static void clear() {
		PRESSURE_TEST_FLAG.remove();
	}
	
	public static String getRedisKeyStartPrefix() {
		return isPressureTest()?REDIS_KEY_START_P:"";
	}
	public static String getDBTableStartPrefix() {
		return isPressureTest()?DB_TABLE_START_P:"";
	}
}
