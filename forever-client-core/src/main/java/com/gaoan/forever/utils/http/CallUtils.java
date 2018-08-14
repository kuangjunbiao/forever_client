package com.gaoan.forever.utils.http;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gaoan.forever.constant.ForeverConstant;
import com.gaoan.forever.constant.MessageEnum;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.dialog.Dialog;
import com.gaoan.forever.model.ResponseVo;
import com.gaoan.forever.utils.gson.GsonUtils;
import com.gaoan.forever.utils.gson.JsonUtils;

public class CallUtils {

	private static final Logger logger = LoggerFactory.getLogger(CallUtils.class);

	public static Map<String, Object> get(JFrame jf, String url, Map<String, String> param, MessageEnum message) {
		return executeGet(jf, url, param, message, false);
	}

	public static Map<String, Object> getForDownload(JFrame jf, String url, Map<String, String> param,
			MessageEnum message, String successMsg, String path) {
		return executeGetForDownload(jf, url, param, message, successMsg, path, true);
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
		return executePost(jf, url, null, content, message, false);
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
			response = HttpClientUtils.doGet(url, param);
			logger.info("url = {}, response = {}", url, response);

			ResponseVo responseObj = GsonUtils.fromJson(response, ResponseVo.class);
			if (responseObj == null) {
				Dialog.showWarningDialog(jf, MessageInfoConstant.TITLE_PROMPT, MessageInfoConstant.CONTACT_ADMIN);
				return null;
			}

			if (ForeverConstant.SUCCESS_STATUS.equals(responseObj.getRet())) {
				responseObj = JsonUtils.fromJson(response, ResponseVo.class);
				obj = responseObj.getData();
				if (obj == null) {
					obj = new HashMap<String, Object>();
				}
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
	 * GET方式调用接口,并提示
	 * 
	 * @param jf
	 * @param url
	 * @param content
	 * @param message
	 * @param isTip
	 *            成功是否提示 true:提示 false:不提示
	 */
	public static Map<String, Object> executeGetForDownload(JFrame jf, String url, Map<String, String> param,
			MessageEnum message, String successMsg, String path, boolean isTip) {
		Map<String, Object> obj = null;
		try {
			boolean result = HttpClientUtils.doGetForDownload(url, param, path);
			logger.info("url = {}, result = {}", url, result);

			if (!result) {
				Dialog.showWarningDialog(jf, MessageInfoConstant.TITLE_PROMPT, MessageInfoConstant.CONTACT_ADMIN);
				return null;
			}

			if (isTip) {
				Dialog.showDialog(jf, successMsg);
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
	public static Map<String, Object> executePost(JFrame jf, String url, Map<String, String> param, String content,
			MessageEnum message, boolean isTip) {
		Map<String, Object> obj = null;
		String response;
		try {
			// response = HttpClient1.postJsonF(url, content);
			// response = HttpUrlUtils.postJson(url, content);
			response = HttpClientUtils.doPostJson(url, param, content);
			logger.info("url = {}, response = {}", url, response);

			// 需要用gson转,否则ret获取不到
			ResponseVo responseObj = GsonUtils.fromJson(response, ResponseVo.class);

			if (responseObj == null) {
				Dialog.showWarningDialog(jf, MessageInfoConstant.TITLE_PROMPT, MessageInfoConstant.CONTACT_ADMIN);
				return null;
			}

			if (ForeverConstant.SUCCESS_STATUS.equals(responseObj.getRet())) {
				// 确认请求正常后,需要用json转换，否则时间字段会变成科学计数法
				responseObj = JsonUtils.fromJson(response, ResponseVo.class);
				obj = responseObj.getData();
				if (obj == null) {
					obj = new HashMap<String, Object>();
				}
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
