package com.own.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.own.common.utils.UUIDGenerator;
import com.own.core.utils.IpAddressUtil;
import com.own.core.utils.ReqNoUtils;

@Service
public class LogInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String ip = IpAddressUtil.getIpAddr(request);
		String requestNo = UUIDGenerator.getUUID()+"_"+ip;
		ReqNoUtils.setRequestNo(requestNo);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		ReqNoUtils.clear();
	}
}
