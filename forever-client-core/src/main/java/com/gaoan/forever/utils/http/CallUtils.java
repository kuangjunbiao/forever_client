package com.gaoan.forever.utils.http;

import java.util.Map;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gaoan.forever.constant.ForeverConstant;
import com.gaoan.forever.constant.MessageEnum;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.dialog.Dialog;
import com.gaoan.forever.model.ResponseVo;
import com.gaoan.forever.utils.gson.GsonUtils;
import com.gaoan.forever.utils.gson.JsonUtils;
import com.google.gson.JsonObject;

public class CallUtils {

    private static final Logger logger = LoggerFactory.getLogger(CallUtils.class);

    public static Map<String, Object> get(JFrame jf, String url, Map<String, String> param, MessageEnum message) {
	return executeGet(jf, url, param, message, false);
    }

    /**
     * POST方式调用接口,成功不提示
     * 
     * @param jf
     * @param url
     * @param content
     * @param message
     */
    public static Map<String, Object> post(JFrame jf, String url, String content, MessageEnum message) {
	return executePost(jf, url, content, message, false);
    }

    /**
     * GET方式调用接口,并提示
     * 
     * @param jf
     * @param url
     * @param content
     * @param message
     * @param isTip
     *            成功是否提示 true:提示 false:不提示
     */
    public static Map<String, Object> executeGet(JFrame jf, String url, Map<String, String> param, MessageEnum message,
	    boolean isTip) {
	Map<String, Object> obj = null;
	String response;
	try {
	    response = LoginWithHttpclient.doGet(url, param);
	    logger.info("url = {}, response = {}", url, response);

	    ResponseVo responseObj = GsonUtils.fromJson(response, ResponseVo.class);
	    if (responseObj == null) {
		Dialog.showWarningDialog(jf, MessageInfoConstant.TITLE_PROMPT, MessageInfoConstant.CONTACT_ADMIN);
		return null;
	    }

	    if (ForeverConstant.SUCCESS_STATUS.equals(responseObj.getRet())) {
		obj = responseObj.getData();
		if (isTip) {
		    Dialog.showDialog(jf, message.getSuccessMsg());
		}
	    } else {
		Dialog.showWarningDialog(jf, MessageInfoConstant.TITLE_PROMPT, responseObj.getMsg());
		return null;
	    }
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    Dialog.showWarningDialog(jf, MessageInfoConstant.TITLE_PROMPT, message.getFailMsg());
	}
	return obj;
    }

    /**
     * POST方式调用接口,并提示
     * 
     * @param <T>
     * 
     * @param jf
     * @param url
     * @param content
     * @param message
     * @param isTip
     *            成功是否提示 true:提示 false:不提示
     */
    public static Map<String, Object> executePost(JFrame jf, String url, String content, MessageEnum message,
	    boolean isTip) {
	Map<String, Object> obj = null;
	String response;
	try {
	    // response = HttpClient1.postJsonF(url, content);
	    // response = HttpUrlUtils.postJson(url, content);
	    response = LoginWithHttpclient.doPostJson(url, content);
	    logger.info("url = {}, response = {}", url, response);

	    ResponseVo responseObj = GsonUtils.fromJson(response, ResponseVo.class);

	    if (responseObj == null) {
		Dialog.showWarningDialog(jf, MessageInfoConstant.TITLE_PROMPT, MessageInfoConstant.CONTACT_ADMIN);
		return null;
	    }

	    if (ForeverConstant.SUCCESS_STATUS.equals(responseObj.getRet())) {
		// 由于GSON时间转换会存在科学计数法,所以这里用JSON转换; JSON转换，ret获取不到;所以转换两次
		responseObj = JsonUtils.fromJson(response, ResponseVo.class);
		obj = responseObj.getData();
		if (isTip) {
		    Dialog.showDialog(jf, message.getSuccessMsg());
		}
	    } else {
		Dialog.showWarningDialog(jf, MessageInfoConstant.TITLE_PROMPT, responseObj.getMsg());
		return null;
	    }
	} catch (Exception e) {
	    logger.error(e.getMessage());
	    Dialog.showWarningDialog(jf, MessageInfoConstant.TITLE_PROMPT, message.getFailMsg());
	}
	return obj;
    }

}
