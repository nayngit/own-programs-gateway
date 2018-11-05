package com.own.core.utils;

public class CdataUtil {

	private final static String[] cdata = {"ToUserName","FromUserName","MsgType","Content","PicUrl","MediaId",
									"Format","Recognition","ThumbMediaId","Label","Title","Description","Url",
									"Event","EventKey","Ticket","MusicUrl","HQMusicUrl","Recognition"};
	
	public static boolean isCdata(String str){
		for (String string : cdata) {
			if(str.equals(string))
				return true;
		}
		return false;
	}
}
