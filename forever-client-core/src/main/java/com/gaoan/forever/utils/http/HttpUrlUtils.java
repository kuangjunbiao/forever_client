package com.gaoan.forever.utils.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HttpUrlUtils {

    public static String post(String reqUrl, Map<String, String> reqParams) throws Exception {
	return execute(reqUrl, reqParams, "POST");
    }

    public static String get(String reqUrl, Map<String, String> reqParams) throws Exception {
	return execute(reqUrl, reqParams, "GET");
    }

    public static String post(String reqUrl, String content) throws Exception {
	return execute(reqUrl, content, "POST");
    }

    public static String postJson(String reqUrl, String content) throws Exception {
	return execute(reqUrl, content, "POST", "application/json;charset=UTF-8");
    }

    public static String postJsonF(String reqUrl, String content) throws Exception {
	return executeF(reqUrl, content, "POST", "application/json;charset=UTF-8");
    }

    public static Map<String, String> postJson(String reqUrl, String content, String sign) throws Exception {
	// 设置请求header, 默认设置sign签名, 不设置则为空
	Map<String, String> headers = new HashMap<>();
	headers.put("sign", sign);
	// 设置返回hearder 不返回则为空
	List<String> resposeHeaderKeys = new ArrayList<String>();
	resposeHeaderKeys.add("sign");
	return execute(reqUrl, content, "POST", "application/json;charset=UTF-8", headers, 20 * 1000,
		resposeHeaderKeys);
    }

    public static String get(String reqUrl, String content) throws Exception {
	return execute(reqUrl, content, "GET");
    }

    public static String get(String reqUrl) throws Exception {
	return executeG(reqUrl, "GET", "application/x-www-form-urlencoded");
    }

    public static String execute(String reqUrl, Map<String, String> reqParams, String method) throws Exception {
	String content = mapToUrl(reqParams);
	return execute(reqUrl, content, method);
    }

    /**
     * HTTP请求
     *
     * @param reqUrl
     *            访问的服务器URL
     * @param content
     *            请求body内容
     * @param method
     *            请求方式POST/GET
     * @return
     * @throws IOException
     */
    public static String execute(String reqUrl, String content, String method) throws Exception {
	return execute(reqUrl, content, method, "application/x-www-form-urlencoded");
    }

    public static String execute(String reqUrl, Map<String, String> reqParams, String method, String contentType)
	    throws Exception {
	String content = mapToUrl(reqParams);
	return execute(reqUrl, content, method, contentType);
    }

    public static String execute(String reqUrl, Map<String, String> reqParams, String method, String contentType,
	    String charset) throws Exception {
	String content = mapToUrl(reqParams, charset);
	return execute(reqUrl, content, method, contentType);
    }

    public static String getSessionId(String urlStr) {
	OutputStream out = null;
	InputStream in = null;
	try {
	    URL url = new URL(urlStr);
	    HttpURLConnection con = (HttpURLConnection) url.openConnection();
	    con.setRequestMethod("POST");

	    // 打印请求头信息
	    Map hfs = con.getHeaderFields();
	    Set<String> keys = hfs.keySet();
	    for (String str : keys) {
		List<String> vs = (List) hfs.get(str);
		System.out.print(str + ":");
		for (String v : vs) {
		    System.out.print(v + "\t");
		}
		System.out.println();
	    }
	    System.out.println("-----------------------");
	    String cookieValue = con.getHeaderField("Set-Cookie");
	    System.out.println("cookie value:" + cookieValue);
	    String sessionId = cookieValue.substring(0, cookieValue.indexOf(";"));
	    return sessionId;
	} catch (Exception e) {
	    e.printStackTrace();
	    return null;
	} finally {
	    try {
		if (in != null)
		    in.close();
		if (out != null)
		    out.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    public static String execute(String reqUrl, String content, String method, String contentType) throws Exception {
	String response = "";
	String invokeUrl = reqUrl;
	URL serverUrl = new URL(invokeUrl);
	HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();

	conn.setReadTimeout(5 * 1000); // 缓存的最长时间
	conn.setRequestMethod(method);
	conn.setDoInput(true);// 允许输入
	conn.setDoOutput(true);// 允许输出
	conn.setUseCaches(false); // 不允许使用缓存
	conn.setRequestProperty("connection", "keep-alive");
	conn.setRequestProperty("Charsert", "UTF-8");
	conn.setRequestProperty("Accept",
		"text/html,application/xhtml+xml,application/xml,application/json;q=0.9,image/webp,*/*;q=0.8");
	// conn.setRequestProperty("Accept", "application/json");
	conn.setRequestProperty("Content-Type", contentType);

	conn.connect();
	conn.getOutputStream().write(content.getBytes());
	conn.getOutputStream().flush();

	InputStream is = conn.getInputStream();

	BufferedReader in = new BufferedReader(new InputStreamReader(is, "utf-8"));
	StringBuffer buffer = new StringBuffer();
	String line = "";
	while ((line = in.readLine()) != null) {
	    buffer.append(line);
	}
	response = URLDecoder.decode(buffer.toString(), "utf-8");
	conn.disconnect();
	return response;
    }

    public static String executeF(String reqUrl, String content, String method, String contentType) throws Exception {
	String response = "";
	String invokeUrl = reqUrl;
	URL serverUrl = new URL(invokeUrl);
	HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();

	conn.setReadTimeout(20 * 1000); // 缓存的最长时间
	conn.setRequestMethod(method);
	conn.setDoInput(true);// 允许输入
	conn.setDoOutput(true);// 允许输出
	conn.setUseCaches(false); // 不允许使用缓存
	conn.setRequestProperty("connection", "keep-alive");
	conn.setRequestProperty("Charsert", "UTF-8");
	conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	conn.setRequestProperty("Content-Type", contentType);
	conn.connect();
	conn.getOutputStream().write(content.getBytes());
	conn.getOutputStream().flush();

	InputStream is = conn.getInputStream();

	BufferedReader in = new BufferedReader(new InputStreamReader(is, "utf-8"));
	StringBuffer buffer = new StringBuffer();
	String line = "";
	while ((line = in.readLine()) != null) {
	    buffer.append(line);
	}
	response = buffer.toString();
	conn.disconnect();
	return response;
    }

    public static Map<String, String> execute(String reqUrl, String content, String method, String contentType,
	    Map<String, String> headers, int timeout, List<String> resposeHeaderKeys) throws Exception {
	Map<String, String> map = new HashMap<>();
	String invokeUrl = reqUrl;
	URL serverUrl = new URL(invokeUrl);
	HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();

	conn.setReadTimeout(timeout); // 缓存的最长时间
	conn.setRequestMethod(method);
	conn.setDoInput(true);// 允许输入
	conn.setDoOutput(true);// 允许输出
	conn.setUseCaches(false); // 不允许使用缓存
	if (null != headers) {
	    for (Map.Entry<String, String> entry : headers.entrySet()) {
		conn.setRequestProperty(entry.getKey(), entry.getValue());
	    }
	}
	conn.setRequestProperty("connection", "keep-alive");
	conn.setRequestProperty("Charsert", "UTF-8");
	conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	conn.setRequestProperty("Content-Type", contentType);
	conn.connect();
	conn.getOutputStream().write(content.getBytes());
	conn.getOutputStream().flush();

	InputStream is = conn.getInputStream();
	BufferedReader in = new BufferedReader(new InputStreamReader(is, "utf-8"));
	StringBuffer buffer = new StringBuffer();
	String line = "";
	while ((line = in.readLine()) != null) {
	    buffer.append(line);
	}
	Map<String, List<String>> headerMap = conn.getHeaderFields();
	for (String key : resposeHeaderKeys) {
	    List<String> values = headerMap.get(key);
	    if (values != null && values.size() >= 1) {
		map.put(key, values.get(0));
	    }
	}
	map.put("body", buffer.toString());
	conn.disconnect();
	return map;
    }

    public static String executeG(String reqUrl, String method, String contentType) throws Exception {
	String response = "";
	String invokeUrl = reqUrl;
	URL serverUrl = new URL(invokeUrl);
	HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();

	conn.setReadTimeout(5 * 1000); // 缓存的最长时间
	conn.setRequestMethod(method);
	conn.setDoInput(true);// 允许输入
	conn.setDoOutput(false);// 允许输出
	conn.setUseCaches(false); // 不允许使用缓存
	conn.setRequestProperty("connection", "keep-alive");
	conn.setRequestProperty("Charsert", "UTF-8");
	conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	conn.setRequestProperty("Content-Type", contentType);
	conn.connect();

	InputStream is = conn.getInputStream();

	BufferedReader in = new BufferedReader(new InputStreamReader(is, "utf-8"));
	StringBuffer buffer = new StringBuffer();
	String line = "";
	while ((line = in.readLine()) != null) {
	    buffer.append(line);
	}
	response = URLDecoder.decode(buffer.toString(), "utf-8");
	conn.disconnect();
	return response;
    }

    /**
     * 通过拼接的方式构造请求内容，实现参数传输以及文件传输
     *
     * @param actionUrl
     *            访问的服务器URL
     * @param params
     *            普通参数
     * @param files
     *            文件参数
     * @return
     * @throws IOException
     */
    public static void post(String actionUrl, Map<String, String> params, Map<String, File> files) throws IOException {

	String BOUNDARY = java.util.UUID.randomUUID().toString();
	String PREFIX = "--", LINEND = "\r\n";
	String MULTIPART_FROM_DATA = "multipart/form-data";
	String CHARSET = "UTF-8";

	URL uri = new URL(actionUrl);
	HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
	conn.setReadTimeout(5 * 1000); // 缓存的最长时间
	conn.setDoInput(true);// 允许输入
	conn.setDoOutput(true);// 允许输出
	conn.setUseCaches(false); // 不允许使用缓存
	conn.setRequestMethod("POST");
	conn.setRequestProperty("connection", "keep-alive");
	conn.setRequestProperty("Charsert", "UTF-8");
	conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

	// 首先组拼文本类型的参数
	StringBuilder sb = new StringBuilder();
	for (Map.Entry<String, String> entry : params.entrySet()) {
	    sb.append(PREFIX);
	    sb.append(BOUNDARY);
	    sb.append(LINEND);
	    sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
	    sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
	    sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
	    sb.append(LINEND);
	    sb.append(entry.getValue());
	    sb.append(LINEND);
	}

	DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
	outStream.write(sb.toString().getBytes());
	InputStream in = null;
	// 发送文件数据
	if (files != null) {
	    for (Map.Entry<String, File> file : files.entrySet()) {
		StringBuilder sb1 = new StringBuilder();
		sb1.append(PREFIX);
		sb1.append(BOUNDARY);
		sb1.append(LINEND);
		// name是post中传参的键 filename是文件的名称
		sb1.append(
			"Content-Disposition: form-data; name=\"file\"; filename=\"" + file.getKey() + "\"" + LINEND);
		sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
		sb1.append(LINEND);
		outStream.write(sb1.toString().getBytes());

		InputStream is = new FileInputStream(file.getValue());
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = is.read(buffer)) != -1) {
		    outStream.write(buffer, 0, len);
		}

		is.close();
		outStream.write(LINEND.getBytes());
	    }

	    // 请求结束标志
	    byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
	    outStream.write(end_data);
	    outStream.flush();
	    // 得到响应码
	    int res = conn.getResponseCode();
	    if (res == 200) {
		in = conn.getInputStream();
		int ch;
		StringBuilder sb2 = new StringBuilder();
		while ((ch = in.read()) != -1) {
		    sb2.append((char) ch);
		}
	    }
	    outStream.close();
	    conn.disconnect();
	}
	// return in.toString();
    }

    // 以数据流的形式传参
    public static String postFile(String actionUrl, Map<String, String> params, Map<String, byte[]> files)
	    throws Exception {
	String BOUNDARY = java.util.UUID.randomUUID().toString();
	String PREFIX = "--", LINEND = "\r\n";
	String MULTIPART_FROM_DATA = "multipart/form-data";
	String CHARSET = "UTF-8";

	URL uri = new URL(actionUrl);
	HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
	conn.setReadTimeout(6 * 1000); // 缓存的最长时间
	conn.setDoInput(true);// 允许输入
	conn.setDoOutput(true);// 允许输出
	conn.setUseCaches(false); // 不允许使用缓存
	conn.setRequestMethod("POST");
	conn.setRequestProperty("connection", "keep-alive");
	conn.setRequestProperty("Charsert", "UTF-8");
	conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA + ";boundary=" + BOUNDARY);

	// 首先组拼文本类型的参数
	StringBuilder sb = new StringBuilder();
	for (Map.Entry<String, String> entry : params.entrySet()) {
	    sb.append(PREFIX);
	    sb.append(BOUNDARY);
	    sb.append(LINEND);
	    sb.append("Content-Disposition: form-data; name=\"" + entry.getKey() + "\"" + LINEND);
	    sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
	    sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
	    sb.append(LINEND);
	    sb.append(entry.getValue());
	    sb.append(LINEND);
	}

	DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
	outStream.write(sb.toString().getBytes());
	InputStream is = null;
	// 发送文件数据
	if (files != null) {
	    for (Map.Entry<String, byte[]> file : files.entrySet()) {
		StringBuilder sb1 = new StringBuilder();
		sb1.append(PREFIX);
		sb1.append(BOUNDARY);
		sb1.append(LINEND);
		sb1.append("Content-Disposition: form-data; name=\"pic\"; filename=\"" + file.getKey() + "\"" + LINEND);
		sb1.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINEND);
		sb1.append(LINEND);
		outStream.write(sb1.toString().getBytes());

		outStream.write(file.getValue());

		outStream.write(LINEND.getBytes());
	    }
	}
	// 请求结束标志
	byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
	outStream.write(end_data);
	outStream.flush();
	// 得到响应码
	int res = conn.getResponseCode();
	String response = "";
	if (res == 200) {
	    is = conn.getInputStream();
	    BufferedReader in = new BufferedReader(new InputStreamReader(is, "utf-8"));
	    StringBuffer buffer = new StringBuffer();
	    String line = "";
	    while ((line = in.readLine()) != null) {
		buffer.append(line);
	    }
	    response = URLDecoder.decode(buffer.toString(), "utf-8");
	    conn.disconnect();

	}
	outStream.close();
	conn.disconnect();
	return response;
    }

    /**
     * 默认字符编码格式
     *
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String mapToUrl(Map<String, String> params) throws UnsupportedEncodingException {
	return mapToUrl(params, "UTF-8");
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

    public static String doPostForXml(String url, String xml) {
	URL net = null;
	try {
	    // 打开连接
	    net = new URL(url);
	    HttpURLConnection conn = (HttpURLConnection) net.openConnection();

	    conn.setDoOutput(true);
	    conn.setDoInput(true);
	    conn.setRequestMethod("POST");
	    // post方式不能使用缓存
	    conn.setUseCaches(false);

	    conn.setRequestProperty("Content-Type", "application/xml");
	    // 维持长连接
	    conn.setRequestProperty("Connection", "Keep-Alive");
	    // 设置浏览器编码
	    conn.setRequestProperty("Charset", "UTF-8");
	    // 将请求参数数据向服务器端发送
	    conn.getOutputStream().write(xml.getBytes("UTF-8"));
	    conn.getOutputStream().flush();
	    conn.getOutputStream().close();
	    if (conn.getResponseCode() == 200) {
		// 获得服务器端输出流
		InputStream in = conn.getInputStream();
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while ((len = in.read(buffer)) != -1) {
		    outSteam.write(buffer, 0, len);
		}
		outSteam.close();
		in.close();
		String response = new String(outSteam.toByteArray(), "UTF-8");
		return response;
	    } else {
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	}
	return null;
    }

    public static void main(String[] args) {
	String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><request><mobileInfo><imei>861836010013535</imei><mac"
		+ ">68:9c:5e:b8:78:67</mac><version>1.0.0.0</version><model>小宇宙×1</model><screenSize>720X1280"
		+ "</screenSize><platForm>4.2.1</platForm><os>Android</os></mobileInfo><channelId>-1</channelId><appId"
		+ ">101</appId><pid>555548</pid><digest>940765010d81c0ef77eb7fa4a319eb1e</digest></request>";
	String resp = doPostForXml("http://pushsdk.boxwan.cn/ads/requestAds.do", xml);
	System.out.println(resp);
    }
}
