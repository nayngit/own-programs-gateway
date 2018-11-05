package com.own.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.own.base.publicnumber.message.res.Article;
import com.own.base.publicnumber.message.res.ArticleMessage;
import com.own.base.publicnumber.message.res.ImageMessage;
import com.own.base.publicnumber.message.res.MusicMessage;
import com.own.base.publicnumber.message.res.TextMessage;
import com.own.base.publicnumber.message.res.VideoMessage;
import com.own.base.publicnumber.message.res.VoiceMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

@SuppressWarnings("unchecked")
public class MessageUtils {
	
	/**
	 * 把xml转为map
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException{
		Map<String,String> map = new HashMap<String,String>();
		SAXReader reader = new SAXReader();
		InputStream ins = null;
		Document doc = null;
		try {
			ins = request.getInputStream();
			doc = reader.read(ins);
			Element root = doc.getRootElement();
			recursiveParseXML(root,map);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			ins.close();
		}
		return null;
	}
	
	/**
	 * 把xml中数据放入map中
	 * @param root
	 * @param map
	 */
	private static void recursiveParseXML(Element root,Map<String,String> map){
		//得到根过元素的所有子节点
		List<Element> elementList = root.elements();
		//判断有没有子元素列表
		if(elementList.size() == 0)
			map.put(root.getName(), root.getText());
		else{
			for (Element e : elementList) {
				recursiveParseXML(e,map);
			}
		}
	}
	
	/**
	 * 扩展xstream使其支持CDATA
	 */
	private static XStream xstream = new XStream(new XppDriver(){
		public HierarchicalStreamWriter createWriter(Writer out){
			return new PrettyPrintWriter(out){
				boolean cdata = false;//默认不需要加CDATA
				
				public void startNode(String name,Class clazz){
					super.startNode(name,clazz);
					cdata = CdataUtil.isCdata(name);//判断是否需要加CDATA
				}
				
				protected void writeText(QuickWriter writer,String text){
					if(cdata){
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					}else{
						writer.write(text);
					}
				}
			};
		}
	});
	
	/**
	 * 文本消息转XML		
	 * @param TextMessage
	 * @return
	 */
	public static String textMessageToXml(TextMessage textMessage){
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}
	
	/**
	 * 图片消息转XML		
	 * @param ImageMessage
	 * @return
	 */
	public static String imageMessageToXml(ImageMessage imageMessage){
		xstream.alias("xml", imageMessage.getClass());
		return xstream.toXML(imageMessage);
	}
	
	 /** 
     * 音乐消息对象转换成xml 
     *  
     * @param musicMessage 音乐消息对象 
     * @return xml 
     */  
    public static String musicMessageToXml(MusicMessage musicMessage) {  
        xstream.alias("xml", musicMessage.getClass());  
        return xstream.toXML(musicMessage);  
    } 
    
    /** 
     * 语音消息对象转换成xml 
     *  
     * @param VoiceMessage 语音消息对象 
     * @return xml 
     */  
    public static String voiceMessageToXml(VoiceMessage voiceMessage) {  
        xstream.alias("xml", voiceMessage.getClass());  
        return xstream.toXML(voiceMessage);  
    }
    
    /** 
     * 视频消息对象转换成xml 
     *  
     * @param VideoMessage 视频消息对象 
     * @return xml 
     */  
    public static String vedioMessageToXml(VideoMessage videoMessage) {  
        xstream.alias("xml", videoMessage.getClass());  
        return xstream.toXML(videoMessage);  
    }
    /** 
     * 图文消息对象转换成xml 
     *  
     * @param newsMessage 图文消息对象 
     * @return xml 
     */  
    public static String newsMessageToXml(ArticleMessage newsMessage) {  
        xstream.alias("xml", newsMessage.getClass());  
        xstream.alias("item", new Article().getClass());  
        return xstream.toXML(newsMessage);  
    } 
}
