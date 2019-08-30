package com.own.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/v1/redirect")
public class RedirectController {

	@RequestMapping(value = "/redirect.do", method = RequestMethod.GET)
    public ModelAndView redirect(HttpServletRequest request, HttpServletResponse response,ModelMap modelMap) {
   
		System.out.println("访问成功。。。。");
                Cookie cookie = new Cookie("redirect", "redirectm");
//                cookie.setPath("/");
//                cookie.setMaxAge(60*30);
                cookie.setDomain("192.168.0.104");
        response.addCookie(cookie );
        String url = "https://xyylcdn.weein.cn/membercenter/membercenter-h5-view/activity_basketball/index.html?activityId=basketball";
        return new ModelAndView("redirect:" + url);
    }
	
}
