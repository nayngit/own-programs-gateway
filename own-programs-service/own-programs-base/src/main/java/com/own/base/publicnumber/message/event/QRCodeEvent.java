package com.own.base.publicnumber.message.event;

/**
 * 扫描带参数二维码事件
 * @author
 *
 */
public class QRCodeEvent extends BaseEvent{

	private String EventKey;
	private String Ticket;
	
	public String getEventKey() {
		return EventKey;
	}
	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}
	public String getTicket() {
		return Ticket;
	}
	public void setTicket(String ticket) {
		Ticket = ticket;
	}
	
}
