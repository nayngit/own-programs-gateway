package com.own.core.utils;

import java.util.Date;
import java.util.Map;

import com.own.base.publicnumber.message.res.TextMessage;

public class EventUtil {

	public static String respSubscribe(Map<String,String> requestMap){//subscribe(订阅)
		
		String fromUserName = requestMap.get("FromUserName");
		String toUserName = requestMap.get("ToUserName");

		TextMessage text = new TextMessage();
		text.setContent("望仔感谢您的关注！");
		text.setToUserName(fromUserName);
		text.setFromUserName(toUserName);
		text.setCreateTime(new Date().getTime());
		text.setMsgType(TypeUtils.RESP_MESSAGE_TYPE_TEXT);
		
		return MessageUtils.textMessageToXml(text);
	}
	
	
}
