package com.own.base.publicnumber.message.req;

/**
 * 请求视频消息
 * @author
 *
 */
public class VideoMessage extends BaseMessage{

	private String MediaId;
	private String ThumbMediaId;
	
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getThumbMediaId() {
		return ThumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
}
