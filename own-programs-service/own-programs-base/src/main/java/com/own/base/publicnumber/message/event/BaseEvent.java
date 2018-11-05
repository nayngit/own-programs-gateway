package com.own.base.publicnumber.message.event;

import com.own.base.publicnumber.message.common.Common;

/**
 * 事件请求类型共同属性
 * @author
 *
 */
public class BaseEvent extends Common{

	private String Event;

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}
	
}
