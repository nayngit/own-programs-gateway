package com.own.core.service.impl;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.own.core.configuration.TuLingApiProperties;
import com.own.core.service.IPublicNumberService;
import com.own.core.utils.ArticleUtil;
import com.own.core.utils.LoggerProxyFactory;
import com.own.core.utils.MessageUtils;
import com.own.core.utils.TextUtil;
import com.own.core.utils.TypeUtils;

@Service
public class PublicNumberServiceImpl implements IPublicNumberService{

	private static final Logger LOG = LoggerProxyFactory.getLogger(PublicNumberServiceImpl.class);
	
	@Resource
	private TuLingApiProperties tuLingApiProperties;
	
	@Override
	public String publicNumberPost(HttpServletRequest request) {
		
		String respMessage = null;
		try {
			Map<String, String> requestMap = MessageUtils.xmlToMap(request);
			LOG.info("[个人公众号,request转Map],返回数据,requestMap:{}",new Object[]{JSON.toJSONString(requestMap)});
			String msgType = requestMap.get("MsgType");
			String key = tuLingApiProperties.getKey();
			if(TypeUtils.REQ_MESSAGE_TYPE_TEXT.equals(msgType)){//文本信息
				respMessage = TextUtil.respText(requestMap,null,key);
			}else if(TypeUtils.REQ_MESSAGE_TYPE_IMAGE.equals(msgType)){//图片消息
				respMessage = TextUtil.respText(requestMap,"您发送的是图片消息.\n图片链接是："+requestMap.get("PicUrl"),null);
			}else if(TypeUtils.REQ_MESSAGE_TYPE_LINK.equals(msgType)){//链接消息
				respMessage = TextUtil.respText(requestMap,"您发送的是链接消息.\n标题："+requestMap.get("Title")+"\n描述："+
						requestMap.get("Description")+"\n链接："+requestMap.get("Url"),null);
			}else if(TypeUtils.REQ_MESSAGE_TYPE_LOCATION.equals(msgType)){//地理位置消息
				respMessage = TextUtil.respText(requestMap,"您发送的是地理位置消息.\n纬度："+requestMap.get("Location_X")+
						"\n经度："+requestMap.get("Location_Y")+"\n"+requestMap.get("Label"),null);
			}else if(TypeUtils.REQ_MESSAGE_TYPE_VOICE.equals(msgType)){//音频消息
				requestMap.put("Content", requestMap.get("Recognition"));
				respMessage = TextUtil.respText(requestMap,null,key);
			}else if(TypeUtils.REQ_MESSAGE_TYPE_SHORTVIDEO.equals(msgType)){//小视频消息
				respMessage = TextUtil.respText(requestMap,"功能暂示开通，请等待！/:&-( \n您发送的是小视频消息",null);
			}else if(TypeUtils.REQ_MESSAGE_TYPE_VIDEO.equals(msgType)){//视频消息
				respMessage = TextUtil.respText(requestMap,"功能暂示开通，请等待！/:&-( \n您发送的是视频消息",null);
			}else if(TypeUtils.REQ_MESSAGE_TYPE_EVENT.equals(msgType)){//事件
				
				String eventType = requestMap.get("Event");
				if(TypeUtils.EVENT_TYPE_SUBSCRIBE.equals(eventType)){//subscribe(订阅)
					respMessage = ArticleUtil.processArticle(requestMap);
					//respMessage = EventUtil.respSubscribe(requestMap);
				}else if(TypeUtils.EVENT_TYPE_UNSUBSCRIBE.equals(eventType)){//unsubscribe(取消订阅)
					respMessage = TextUtil.respText(requestMap, "",null);
				}else if(TypeUtils.EVENT_TYPE_CLICK.equals(eventType)){//自定义菜单点击事件
					respMessage = TextUtil.respText(requestMap,"功能暂示开通，请等待！/:&-( \n您发送的是菜单点击事件",null);
				}
			}
		} catch (Exception e) {
			LOG.info("[个人公众号-处理消息] 发生异常,e:{}",new Object[]{e});
		}
		return respMessage;
	}

}
