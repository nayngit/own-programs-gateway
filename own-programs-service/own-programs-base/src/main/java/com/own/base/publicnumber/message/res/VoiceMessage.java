package com.own.base.publicnumber.message.res;

import com.own.base.publicnumber.message.common.Common;

/**
 * 回复语音消息
 * @author
 *
 */
public class VoiceMessage extends Common{

	private Voice voice;

	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}
	
}
