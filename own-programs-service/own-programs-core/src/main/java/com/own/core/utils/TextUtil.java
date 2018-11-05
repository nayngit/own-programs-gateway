package com.own.core.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.own.base.contants.PublicNumberContants;
import com.own.base.publicnumber.message.res.TextMessage;
import com.own.common.utils.HttpUtils;

public class TextUtil {

	public static String respText(Map<String,String> requestMap,String message,String key){
		
		String fromUserName = requestMap.get("FromUserName");
		String toUserName = requestMap.get("ToUserName");
		TextMessage text = new TextMessage();
		if(message == null){
			String content = requestMap.get("Content");
			setContent(text,fromUserName,content,key);
		}else{
			text.setContent(message);
		}
		text.setToUserName(fromUserName);
		text.setFromUserName(toUserName);
		text.setCreateTime(new Date().getTime());
		text.setMsgType(TypeUtils.RESP_MESSAGE_TYPE_TEXT);
		
		return MessageUtils.textMessageToXml(text);
	}
	
	private static void setContent(TextMessage text,String userid,String info,String key){
		Map<String,Object> map  = new HashMap<String, Object>();
		map.put("userid", userid);
		map.put("info", info);
		map.put("key", key);
		String sendPost = HttpUtils.sendPost(PublicNumberContants.ACCESS_TU_LING_URL, map, "UTF-8");
		JSONObject jsonObject = JSON.parseObject(sendPost);
		Integer code = jsonObject.getInteger("code");
		String t = jsonObject.getString("text");
		switch (code) {
			case 100000:text.setContent(t);	break;
			case 200000:text.setContent(t+"\n"+jsonObject.getString("url"));	break;
			case 302000:text.setContent(t+"\n"+TuLingApiUtil.getNews(jsonObject));	break;
			case 308000:text.setContent(t+"\n"+TuLingApiUtil.getCookBook(jsonObject));	break;
		default:text.setContent((String) TuLingApiUtil.getText().get(code));
			break;
		}
	}
}
