package com.own.core.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TuLingApiUtil {

	public static Map<String,Object> getText(){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("40001","不好意思，服务有误！");
		map.put("40002", "您想要问什么?");
		map.put("40004", "今天我累了，明天再聊吧！再见！");
		map.put("40007", "不好意思，服务繁忙!");
		return map;
	}
	
	public static String getNews(JSONObject json){
		StringBuilder builder = new StringBuilder();
		JSONArray jsonArray = json.getJSONArray("list");
		int size = jsonArray.size();
		int i = new Random().nextInt(size);
		//for(int i=0;i<jsonArray.size();i++){//内容太多，微信不响应
			JSONObject jsonObject = jsonArray.getJSONObject(i);//从查找的新闻中随机选一个
			builder.append(jsonObject.get("article")).append("\n");
			builder.append(jsonObject.get("source")).append("\n");
			builder.append(jsonObject.get("icon")).append("\n");
			builder.append(jsonObject.get("detailurl")).append("\n");
		//}
		return builder.toString();
	}
	
	public static String getCookBook(JSONObject json){
		StringBuilder builder = new StringBuilder();
		JSONArray jsonArray = json.getJSONArray("list");
		int size = jsonArray.size();
		int i = new Random().nextInt(size);
		//for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			builder.append(jsonObject.get("name")).append("\n");
			builder.append(jsonObject.get("icon")).append("\n");
			builder.append(jsonObject.get("info")).append("\n");
			builder.append(jsonObject.get("detailurl")).append("\n");
		//}
		return builder.toString();
	}
	
	
}
