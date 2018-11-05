package com.own.base.publicnumber.message.req;

/**
 * 请求语音消息
 * @author
 *
 */
public class VoiceMessage {

	private String MediaId;
	private String Format;
	private String Recognition;//语音识别开通后才会增加的字段
	
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getFormat() {
		return Format;
	}
	public void setFormat(String format) {
		Format = format;
	}
	public String getRecognition() {
		return Recognition;
	}
	public void setRecognition(String recognition) {
		Recognition = recognition;
	}
	
}
