package com.gaoan.forever.utils.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class LoginWithHttpclient {

    static CookieStore cookieStore = null;

    /**
     * 组装登录参数
     * 
     * @return
     */
    public static List<NameValuePair> getLoginNameValuePairList() {
	List<NameValuePair> params = new ArrayList<NameValuePair>();
	params.add(new BasicNameValuePair("sso_callback_uri", "/xxx/forward?locale=zh_CN"));
	params.add(new BasicNameValuePair("appName", "xxx"));
	params.add(new BasicNameValuePair("username", "xxx"));
	params.add(new BasicNameValuePair("password", "xxx"));
	return params;
    }

    /**
     * 组装操作参数
     * 
     * @return
     */
    public static List<NameValuePair> getQueryNameValuePairList() {
	List<NameValuePair> params = new ArrayList<NameValuePair>();
	params.add(new BasicNameValuePair("regionNo", "xxx"));
	params.add(new BasicNameValuePair("pageNo", "xxx"));
	params.add(new BasicNameValuePair("pageSize", "xxx"));
	return params;
    }

    /**
     * 将cookie保存到静态变量中供后续调用 JSESSIONID
     * 
     * @param httpResponse
     */
    public static void setCookieStore(HttpResponse httpResponse, String url) {
	if (url.indexOf("/login") == -1) {
	    return;
	}

	if (cookieStore == null) {
	    cookieStore = new BasicCookieStore();
	}
	String JSESSIONID = null;
	Header[] headers = httpResponse.getHeaders("Set-Cookie");
	for (Header header : headers) {
	    String setCookie = header.getValue();
	    if (setCookie.indexOf("SESSION=") != -1) {
		JSESSIONID = setCookie.substring("SESSION=".length(), setCookie.indexOf(";"));
		System.out.println("SESSION:" + JSESSIONID);
	    }
	}
	// 新建一个Cookie
	BasicClientCookie cookie = new BasicClientCookie("SESSION", JSESSIONID);
	cookie.setVersion(0);
	cookie.setDomain("127.0.0.1");
	cookie.setPath("/");
	cookieStore.addCookie(cookie);
    }

    public static String doGet(String url, Map<String, String> map) {
	String retStr = "";
	HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
	CloseableHttpClient closeableHttpClient = null;
	if (cookieStore != null) {
	    closeableHttpClient = httpClientBuilder.setDefaultCookieStore(cookieStore).build();
	} else {
	    closeableHttpClient = httpClientBuilder.build();
	}
	try {
	    String reqUrl = url;
	    if (map != null && !map.isEmpty()) {
		reqUrl = url + "?" + mapToUrl(map, "UTF-8");
	    }
	    HttpGet httpGet = new HttpGet(reqUrl);
	    // 设置请求和传输超时时间
	    RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000)
		    .build();
	    httpGet.setConfig(requestConfig);

	    CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
	    setCookieStore(response, url);

	    HttpEntity httpEntity = response.getEntity();
	    retStr = EntityUtils.toString(httpEntity, "UTF-8");
	    // 释放资源
	    closeableHttpClient.close();
	} catch (Exception e) {

	}
	return retStr;
    }

    public static String doPost(String postUrl, List<NameValuePair> parameterList) {
	String retStr = "";
	HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
	CloseableHttpClient closeableHttpClient = null;
	if (cookieStore != null) {
	    closeableHttpClient = httpClientBuilder.setDefaultCookieStore(cookieStore).build();
	} else {
	    closeableHttpClient = httpClientBuilder.build();
	}
	HttpPost httpPost = new HttpPost(postUrl);
	// 设置请求和传输超时时间
	RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
	httpPost.setConfig(requestConfig);
	httpPost.setHeader("Accept", "application/json");
	httpPost.setHeader("Content-Type", "application/json");
	try {
	    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameterList, "UTF-8");
	    httpPost.setEntity(entity);
	    CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
	    setCookieStore(response, postUrl);

	    HttpEntity httpEntity = response.getEntity();
	    retStr = EntityUtils.toString(httpEntity, "UTF-8");
	    // 释放资源
	    closeableHttpClient.close();
	} catch (Exception e) {
	}
	return retStr;
    }

    public static String doPostJson(String postUrl, String content) {
	String retStr = "";
	// 创建HttpClientBuilder
	HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
	CloseableHttpClient closeableHttpClient = null;
	if (cookieStore != null) {
	    closeableHttpClient = httpClientBuilder.setDefaultCookieStore(cookieStore).build();
	} else {
	    closeableHttpClient = httpClientBuilder.build();
	}
	HttpPost httpPost = new HttpPost(postUrl);
	httpPost.setHeader("Accept", "application/json");
	httpPost.setHeader("Content-Type", "application/json");
	// 设置请求和传输超时时间
	RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000).build();
	httpPost.setConfig(requestConfig);
	try {
	    String charSet = "UTF-8";
	    StringEntity entity = new StringEntity(content, charSet);
	    httpPost.setEntity(entity);
	    CloseableHttpResponse response = closeableHttpClient.execute(httpPost);
	    setCookieStore(response, postUrl);

	    HttpEntity httpEntity = response.getEntity();
	    retStr = EntityUtils.toString(httpEntity, "UTF-8");
	    // 释放资源
	    closeableHttpClient.close();
	} catch (Exception e) {
	}
	return retStr;
    }

    /**
     * 根据指定编码对参数进行编码
     *
     * @param params
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String mapToUrl(Map<String, String> params, String charset) throws UnsupportedEncodingException {
	StringBuilder sb = new StringBuilder();
	boolean isFirst = true;
	for (String key : params.keySet()) {
	    String value = params.get(key);
	    if (isFirst) {
		sb.append(key + "=" + URLEncoder.encode(value, charset));
		isFirst = false;
	    } else {
		if (value != null) {
		    sb.append("&" + key + "=" + URLEncoder.encode(value, charset));
		} else {
		    sb.append("&" + key + "=");
		}
	    }
	}
	return sb.toString();
    }
}