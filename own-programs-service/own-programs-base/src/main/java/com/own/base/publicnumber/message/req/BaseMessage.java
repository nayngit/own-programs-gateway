package com.own.base.publicnumber.message.req;

import com.own.base.publicnumber.message.common.Common;

/**
 * 请求消息类型的共有属性
 * @author
 *
 */
public class BaseMessage extends Common{

	private String MsgId;

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
 
}
