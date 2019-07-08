package com.own.core.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerProxyFactory implements InvocationHandler{

	// 维护目标对象
		private Logger logger;

		private boolean init;

		private static Map<String, Method> methods = new HashMap<String, Method>();

		static {
			Class<Logger> clazz = Logger.class;
			try {
				Method debugStringMethod = clazz.getMethod("debug", String.class, Object[].class);
				Method infoStringMethod = clazz.getMethod("info", String.class, Object[].class);
				Method errorStringMethod = clazz.getMethod("error", String.class, Object[].class);
				Method traceStringMethod = clazz.getMethod("trace", String.class, Object[].class);
				Method warnStringMethod = clazz.getMethod("warn", String.class, Object[].class);

				methods.put("debug", debugStringMethod);
				methods.put("info", infoStringMethod);
				methods.put("error", errorStringMethod);
				methods.put("trace", traceStringMethod);
				methods.put("warn", warnStringMethod);
			} catch (NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}

		public static Logger getLogger(Class<?> clazz) {
			return new LoggerProxyFactory(clazz).getInstance();
		}
		
		public static Logger getLogger(String name) {
			return new LoggerProxyFactory(name).getInstance();
		}
		
		private LoggerProxyFactory(Class<?> clazz) {
			init = true;
			this.logger = LoggerFactory.getLogger(clazz);
		}
		
		private LoggerProxyFactory(String name) {
			init = true;
			this.logger = LoggerFactory.getLogger(name);
		}

		public static void main(String[] args) throws NoSuchMethodException, SecurityException {
			Logger log1 = LoggerFactory.getLogger(LoggerProxyFactory.class);

			Method[] methods = log1.getClass().getMethods();
			for (Method method : methods) {
				System.out.println(method.getName());
				System.out.println(method);
			}

			Logger log = (Logger) new LoggerProxyFactory(LoggerProxyFactory.class).getInstance();
			ReqNoUtils.setRequestNo("no");
			 log.info("aaaaa");
			 log.info("aaaaa:{}", "1");
			 log.info("aaaaa:{},:{}", "1", "2");
			 log.info("aaaaa:{},:{},:{},:{}", "1", "2", "3", "4");
			 log.error("aaaaa");
			 log.error("aaaaa:{}", "1");
			 log.error("aaaaa:{},:{}", "1", "2");
			 log.error("aaaaa:{},:{},:{},:{}", "1", "2", "3", "4");
			log.error("aaaaa", new Exception());
			log.error("aaaaa:{}", "1", new Exception());
			log.error("aaaaa:{},:{}", "1", "2", new Exception());
			log.error("aaaaa:{},:{},:{},:{}", "1", "2", "3", "4", new Exception());
			log1.error("aaaaa:{},:{},:{},:{}", new Object[] {"1", "2", "3", "4", new Exception()});

		}

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			// 执行目标对象的方法
			Object returnValue = null;
			String name = method.getName();
			switch (name) {
			case "debug":
			case "info":
			case "warn":
			case "error":
			case "trace":
				int length = args.length;
				if (length > 0 && args[0] instanceof String) {
					if (length > 1) {
						if (args[1] instanceof Object[]) {
							Object[] objs = (Object[]) args[1];
							Object[] newObjs = new Object[objs.length + 1];
							if (objs[objs.length - 1] instanceof Throwable) {
								System.arraycopy(objs, 0, newObjs, 0, objs.length - 1);
								newObjs[newObjs.length - 1] = objs[objs.length - 1];
								newObjs[newObjs.length - 2] = ReqNoUtils.getRequestNo();
							} else {
								System.arraycopy(objs, 0, newObjs, 0, objs.length);
								newObjs[newObjs.length - 1] = ReqNoUtils.getRequestNo();
							}
							args[0] += ",requestNo:{}";
							args[1] = newObjs;
						} else {
							Object[] newArgs = new Object[2];
							Object[] newObjs = new Object[args.length];
							if (args[args.length - 1] instanceof Throwable) {
								if (args.length > 2) {
									System.arraycopy(args, 1, newObjs, 0, args.length - 2);
									newObjs[newObjs.length - 1] = args[args.length - 1];
									newObjs[newObjs.length - 2] = ReqNoUtils.getRequestNo();
								} else {
									newObjs[0] = ReqNoUtils.getRequestNo();
									newObjs[1] = args[args.length - 1];
								}
							} else {
								System.arraycopy(args, 1, newObjs, 0, args.length - 1);
								newObjs[args.length - 1] = ReqNoUtils.getRequestNo();
							}
							newArgs[0] = args[0] + ",requestNo:{}";
							newArgs[1] = newObjs;
							args = newArgs;
						}
					} else {
						Object[] newArgs = new Object[2];
						Object[] newObjs = new Object[1];
						newArgs[0] = args[0] + ",requestNo:{}";
						newObjs[0] = ReqNoUtils.getRequestNo();
						newArgs[1] = newObjs;
						args = newArgs;
					}
					// Object[] newArgs = new Object[length + 1];
					// System.arraycopy(args, 0, newArgs, 0, length);
					// newArgs[length] = ReqNoUtils.getRequestNo();
					// newArgs[0] += ",requestNo:{}";
					returnValue = methods.get(name).invoke(logger, args);
				} else {
					returnValue = method.invoke(logger, args);
				}
				break;
			default:
				returnValue = method.invoke(logger, args);
				break;
			}

			return returnValue;
		}

		public Logger getInstance() {
			if (!init) {
				return null;
			}
			return (Logger) Proxy.newProxyInstance(logger.getClass().getClassLoader(), logger.getClass().getInterfaces(), this);
		}
}
