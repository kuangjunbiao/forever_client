package com.gaoan.forever.download;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

/*   
 *  合肥工业大学 管理学院 qianyang 1563178220@qq.com
 */
public class Main {
	// log4j的是使用，不会的请看之前写的文章
	static final Log logger = LogFactory.getLog(Main.class);

	public static void main(String[] args) throws Exception {
		// 初始化一个httpclient
		HttpClient client = new DefaultHttpClient();
		// 我们要爬取的一个地址，这里可以从数据库中抽取数据，然后利用循环，可以爬取一个URL队列
		String url = "http://search.jd.com/Search?keyword=Python&enc=utf-8&book=y&wq=Python&pvid=33xo9lni.p4a1qb";
		// 抓取的数据
		List<JdModel> bookdatas = URLFecter.URLParser(client, url);
		// 循环输出抓取的数据
		for (JdModel jd : bookdatas) {
			logger.info("bookID:" + jd.getBookID() + "\t" + "bookPrice:" + jd.getBookPrice() + "\t" + "bookName:"
					+ jd.getBookName());
		}
		// 将抓取的数据插入数据库
		//MYSQLControl.executeInsert(bookdatas);
	}
}