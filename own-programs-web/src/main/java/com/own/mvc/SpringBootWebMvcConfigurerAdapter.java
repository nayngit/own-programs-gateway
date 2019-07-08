package com.own.mvc;

import java.nio.charset.Charset;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ServletWebArgumentResolverAdapter;

import com.own.interceptor.LogInterceptor;

@Configuration
@EnableConfigurationProperties(ServerProperties.class)
public class SpringBootWebMvcConfigurerAdapter extends WebMvcConfigurerAdapter{
	
	@Resource
	private EncryptWebArgumentResolver  encryptWebArgumentResolver;
	
	@Resource
	private LogInterceptor logInterceptor;

	@Override
	public void configureMessageConverters(
			List<HttpMessageConverter<?>> converters) {
		converters.add(0,new StringHttpMessageConverter(Charset.forName("UTF-8")));
		super.configureMessageConverters(converters);
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(logInterceptor)
		.addPathPatterns("/**")
		;
		
//		registry.addInterceptor(interfaceInterceptor)
//		.addPathPatterns("/**")
//		;
//		
//		//用于拦截微信的请求
//		registry.addInterceptor(weChatInterfaceInterceptor)
//		.addPathPatterns("/v1/wechatbdbrokernew/*")
//		.addPathPatterns("/v1/wechatbdbrokerrenewal/*")
//		;
//		
//		//用于拦截其它的请求
//		registry.addInterceptor(commonInterfaceInterceptor)
//		.addPathPatterns("/v1/commonbdbrokernew/*")
//		.addPathPatterns("/v1/commonbdbrokerrenewal/*")
//		;
		
		super.addInterceptors(registry);
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new ServletWebArgumentResolverAdapter(encryptWebArgumentResolver));
	}
	
}
