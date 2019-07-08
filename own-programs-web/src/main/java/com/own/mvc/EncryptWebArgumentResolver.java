package com.own.mvc;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.alibaba.fastjson.JSON;

@Component
public class EncryptWebArgumentResolver implements WebArgumentResolver{

//	private static final Logger LOG = LoggerFactory.getLogger(EncryptWebArgumentResolver.class);
//	private static Set<String> common_ig = new HashSet<String>();
//	static {
//		common_ig.add("mchId");
//		common_ig.add("sign");
//	}
//	
//	@Resource
//	private WechatApiBodyLogSave wechatApiBodyLogSave;
	
	@Override
	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest webRequest) throws Exception {
//		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
//		List<String> list = MethodParamNamesUtils.getParameterNames(methodParameter.getMethod());
//		String paramName = list.get(methodParameter.getParameterIndex());
//		if(request.getAttribute("isSignFlag")==null) {
//			return request.getParameter(paramName);
//		}
//		Object webArgumentResolverType = request.getAttribute("webArgumentResolverType");
//		WebArgumentResolverTypeEnum webArgumentResolverTypeEnum = WebArgumentResolverTypeEnum.NONE;
//		if(webArgumentResolverType!=null) {
//			webArgumentResolverTypeEnum=	(WebArgumentResolverTypeEnum) webArgumentResolverType;
//		}
//		switch(webArgumentResolverTypeEnum) {
//		case COMMON_REQUEST:
//			return commonResolveArgument(request, paramName, methodParameter);
//		case WECHAT_REQUEST:
//			return wechatResolveArgument(request, paramName, methodParameter);
//		}
		return null;
	}
	
//	private Object wechatResolveArgument(HttpServletRequest request, String paramName,
//			MethodParameter methodParameter) {
//		Object xmlObj = request.getAttribute("xml");
//		Class<?> targetClass = methodParameter.getParameterType();
//		if(WechatBaseReq.class.isAssignableFrom(targetClass)) {
//			if(xmlObj==null) {
//				return null;
//			}
//			HashMap<String, Object> map = XmlUtil.Dom2Map(xmlObj+"");
//			Object obj = map.get("wxlifepay");
//			Object object = JSON.parseObject(JSON.toJSONString(obj),targetClass);
//			wechatApiBodyLogSave.sendRequestBody((WechatBaseReq)object);
//			return object;
//		}
//		return null;
//	}
//
//	private Object commonResolveArgument(HttpServletRequest request,String paramName,MethodParameter methodParameter) {
//		
//		Class<?> targetClass = methodParameter.getParameterType();
//		if(targetClass==MchInfoDO.class) {
//			return request.getAttribute("mchInfoDO");
//		}
//		if(common_ig.contains(paramName)) {
//			return null;
//		}
//		Object param = null;
//		if(PressureTestUtils.isPressureTest()) {
//			String paramstr = request.getParameter(paramName);
//			if(paramstr==null) {
//				param= paramstr;
//			}else {
//				param=reload(param, targetClass);
//			}
//		}else {
//			param = request.getAttribute(paramName);
//			if(param!=null) {
//				param=reload(param, targetClass);
//			}
//		}
//		LOG.info("[添加加密后参数] method:{},paramName:{},param:{}",new Object[] {methodParameter.getMethod(),paramName,param});
//        
//		return param;
//	}
//
//	private Object reload(Object paramStr,Class<?> targetClass) {
//		Object param = null;
//		if(org.springframework.util.ClassUtils.isAssignable(Number.class, targetClass)) {
//			param=org.springframework.util.NumberUtils.parseNumber(paramStr+"", (Class<Number>) targetClass);
//		}else {
//			if(targetClass==String.class) {
//				param=paramStr+"";
//			}else {
//				param=paramStr;
//			}
//			
//		}
//		return param;
//	}
}
