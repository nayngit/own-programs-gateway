package com.own.core.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import com.own.base.publicnumber.message.res.Article;
import com.own.base.publicnumber.message.res.ArticleMessage;

/**
 * 图文消息返回
 * @author
 *
 */
public class ArticleUtil {

	public static String processArticle(Map<String, String> requestMap){
		String respMessage = null;
		try {
			//Map<String, String> requestMap = MessageUtil.xmlToMap(request);
			String fromUserName = requestMap.get("FromUserName");
			String toUserName = requestMap.get("ToUserName");
//			
			respMessage = TextUtil.respText(requestMap, "欢迎访问<a href=\"http://blog.csdn.net/lyq8479\">柳峰的博客</a>!",null);
			
			ArticleMessage articleMessage = new ArticleMessage();
			articleMessage.setToUserName(fromUserName);
			articleMessage.setFromUserName(toUserName);
			articleMessage.setCreateTime(new Date().getTime());
			articleMessage.setMsgType(TypeUtils.RESP_MESSAGE_TYPE_NEWS);
			
			ArrayList<Article> articleList = new ArrayList<Article>();
			
			int i = (new Random().nextInt(4)) + 1;
			//单图文消息
			if(i == 1){
				Article article = new Article();
				article.setTitle("微信公众号开发教程Java版");
				article.setDescription("展望，90后，微信公众帐号开发经验1个月。根据柳峰博客学习，也希望借此机会认识更多同行！");
				article.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
				article.setUrl("http://blog.csdn.net/lyq8479");
				articleList.add(article);
				
				articleMessage.setArticleCount(articleList.size());
				articleMessage.setArticles(articleList);
				
				respMessage = MessageUtils.newsMessageToXml(articleMessage);
			}else if(i == 2){
				//单图文消息-不含图片
				Article article = new Article();
				article.setTitle("微信公众号开发教程Java版");
				article.setDescription("展望，90后，微信公众帐号开发经验1个月。根据柳峰博客学习，也希望借此机会认识更多同行！");
				article.setPicUrl("");
				article.setUrl("http://blog.csdn.net/lyq8479");
				articleList.add(article);
				
				articleMessage.setArticleCount(articleList.size());
				articleMessage.setArticles(articleList);
				
				respMessage = MessageUtils.newsMessageToXml(articleMessage);
			}else if(i == 3){
				//多图文消息
				Article article1 = new Article();  
                article1.setTitle("微信公众帐号开发教程\n引言");  
                article1.setDescription("");  
                article1.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");  
                article1.setUrl("http://blog.csdn.net/lyq8479/article/details/8937622");  

                Article article2 = new Article();  
                article2.setTitle("第2篇\n微信公众帐号的类型");  
                article2.setDescription("");  
                article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");  
                article2.setUrl("http://blog.csdn.net/lyq8479/article/details/8941577");  

                Article article3 = new Article();  
                article3.setTitle("第3篇\n开发模式启用及接口配置");  
                article3.setDescription("");  
                article3.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");  
                article3.setUrl("http://blog.csdn.net/lyq8479/article/details/8944988");  

                articleList.add(article1);  
                articleList.add(article2);  
                articleList.add(article3);  
                articleMessage.setArticleCount(articleList.size());  
                articleMessage.setArticles(articleList);  
                respMessage = MessageUtils.newsMessageToXml(articleMessage);  
			}else if(i == 4){
				//多图文消息-首条消息不含图片
                Article article1 = new Article();  
                article1.setTitle("微信公众帐号开发教程Java版");  
                article1.setDescription("");  
                // 将图片置为空  
                article1.setPicUrl("");  
                article1.setUrl("http://blog.csdn.net/lyq8479");  

                Article article2 = new Article();  
                article2.setTitle("第4篇\n消息及消息处理工具的封装");  
                article2.setDescription("");  
                article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");  
                article2.setUrl("http://blog.csdn.net/lyq8479/article/details/8949088");  

                Article article3 = new Article();  
                article3.setTitle("第5篇\n各种消息的接收与响应");  
                article3.setDescription("");  
                article3.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");  
                article3.setUrl("http://blog.csdn.net/lyq8479/article/details/8952173");  

                Article article4 = new Article();  
                article4.setTitle("第6篇\n文本消息的内容长度限制揭秘");  
                article4.setDescription("");  
                article4.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");  
                article4.setUrl("http://blog.csdn.net/lyq8479/article/details/8967824");  

                articleList.add(article1);  
                articleList.add(article2);  
                articleList.add(article3);  
                articleList.add(article4);  
                articleMessage.setArticleCount(articleList.size());  
                articleMessage.setArticles(articleList);  
                respMessage = MessageUtils.newsMessageToXml(articleMessage);  
			}else if(i == 5){
				//多图文消息-最后一条消息不含图片
				 Article article1 = new Article();  
                 article1.setTitle("第7篇\n文本消息中换行符的使用");  
                 article1.setDescription("");  
                 article1.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");  
                 article1.setUrl("http://blog.csdn.net/lyq8479/article/details/9141467");  

                 Article article2 = new Article();  
                 article2.setTitle("第8篇\n文本消息中使用网页超链接");  
                 article2.setDescription("");  
                 article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");  
                 article2.setUrl("http://blog.csdn.net/lyq8479/article/details/9157455");  

                 Article article3 = new Article();  
                 article3.setTitle("如果觉得文章对你有所帮助，请通过博客留言或关注微信公众帐号xiaoqrobot来支持柳峰！");  
                 article3.setDescription("");  
                 // 将图片置为空  
                 article3.setPicUrl("");  
                 article3.setUrl("http://blog.csdn.net/lyq8479");  

                 articleList.add(article1);  
                 articleList.add(article2);  
                 articleList.add(article3);  
                 articleMessage.setArticleCount(articleList.size());  
                 articleMessage.setArticles(articleList);  
                 respMessage = MessageUtils.newsMessageToXml(articleMessage); 
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return respMessage;
	}
}
