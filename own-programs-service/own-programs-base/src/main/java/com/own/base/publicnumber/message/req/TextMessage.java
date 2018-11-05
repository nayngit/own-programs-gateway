package com.own.base.publicnumber.message.req;

/**
 * 请求文本消息
 * @author
 *
 */
public class TextMessage extends BaseMessage{

	private String Content;
	
	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
