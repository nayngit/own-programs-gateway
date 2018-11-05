package com.own.base.publicnumber.message.event;

/**
 * 自定义菜单事件
 * @author
 *
 */
public class MenuEvent extends BaseEvent{

	private String EventKey;

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	
}
