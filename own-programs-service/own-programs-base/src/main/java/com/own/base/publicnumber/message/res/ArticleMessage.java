package com.own.base.publicnumber.message.res;

import java.util.List;

import com.own.base.publicnumber.message.common.Common;


/**
 * 回复文本消息
 * @author
 *
 */
public class ArticleMessage extends Common{

	private int ArticleCount;
	private List<Article> Articles;
	
	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	public List<Article> getArticles() {
		return Articles;
	}
	public void setArticles(List<Article> articles) {
		Articles = articles;
	}

}
