package com.own.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.own.core.configuration.PublicNumberApiProperties;
import com.own.core.service.IPublicNumberService;
import com.own.core.utils.LoggerProxyFactory;
import com.own.core.utils.PublicNumberSignUtil;

@RestController
@RequestMapping("/v1/publicNumber")
public class PublicNumberController {

	private static final Logger LOG = LoggerProxyFactory.getLogger(PublicNumberController.class);
	
	@Resource
	private PublicNumberApiProperties publicNumberApiProperties;
	
	@Resource
	private IPublicNumberService publicNumberService;
	
	@RequestMapping(value = "/connect.do", method = {RequestMethod.GET,RequestMethod.POST})
    public void checkToken(HttpServletRequest request,HttpServletResponse response) throws IOException{
    	
    	// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");// 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        response.setCharacterEncoding("UTF-8");//在响应消息（回复消息给用户）时，也将编码方式设置为UTF-8，原理同上；
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        PrintWriter out = response.getWriter();
        
        try {
			if(isGet){
				String signature = request.getParameter("signature");
				String timestamp = request.getParameter("timestamp");
				String nonce = request.getParameter("nonce");
				String echostr = request.getParameter("echostr");
				
				if(PublicNumberSignUtil.checkSignature(publicNumberApiProperties.getTokenString(), signature, timestamp, nonce)){
					LOG.info("[个人公众号] token验证通过!");
					out.write(echostr);
				}
			}else{
				String respMessage = "异常消息！";
				
				try {
					respMessage = publicNumberService.publicNumberPost(request);
					LOG.info("[个人公众号，返回数据]，respMessage:{}",new Object[]{respMessage});
					out.write(respMessage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			LOG.error("[个人公众号] 发生异常,e:{}",new Object[] {e});
			e.printStackTrace();
		}finally{
			out.close();
		}
        
    }
}
