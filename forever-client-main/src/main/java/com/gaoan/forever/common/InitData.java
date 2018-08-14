package com.gaoan.forever.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.gaoan.forever.config.ServerApiConfig;
import com.gaoan.forever.constant.ForeverConstant;
import com.gaoan.forever.model.ColorInfoModel;
import com.gaoan.forever.model.ResponseVo;
import com.gaoan.forever.model.SizeInfoModel;
import com.gaoan.forever.model.UserInfoModel;
import com.gaoan.forever.utils.gson.GsonUtils;
import com.gaoan.forever.utils.gson.JsonUtils;
import com.gaoan.forever.utils.http.HttpClientUtils;
import com.google.gson.reflect.TypeToken;

/**
 * 初始化查询条件
 * 
 * @author Administrator
 *
 */
public class InitData {

	private static final String PURCHASE_NAME_LIST_URL = ServerApiConfig.getApiGetPurchaseNameList();
	// private static final String GOODS_LIST_URL =
	// ServerApiConfig.getApiGetGoodsList();
	private static final String COLOR_LIST_URL = ServerApiConfig.getApiGetColorList();
	private static final String SIZE_LIST_URL = ServerApiConfig.getApiGetSizeList();
	private static final String USER_LIST_URL = ServerApiConfig.getApiGetUserList();

	public static Map<String, Object> map = new HashMap<String, Object>();

	public static void init() {
		// 查询进货单列表
		Map<String, Object> purchaseMap = getUrl(PURCHASE_NAME_LIST_URL);
		List<String> purchaseNameList = new ArrayList<String>();
		if (purchaseMap != null && !purchaseMap.isEmpty()) {
			purchaseNameList = GsonUtils.fromJson(purchaseMap.get("list"), new TypeToken<List<String>>() {
			});

			if (CollectionUtils.isNotEmpty(purchaseNameList)) {
				purchaseNameList.add(0, "");
			} else {
				purchaseNameList = new ArrayList<String>();
			}
		}
		map.put("purchaseList", purchaseNameList);

		/* 查询商品列表 */
		// Map<String, Object> goodsMap = getUrl(GOODS_LIST_URL);
		List<String> goodsList = new ArrayList<String>();
		/// 默认不查商品, 根据进货单再去查询
		// if (goodsMap != null && !goodsMap.isEmpty()) {
		// goodsList = GsonUtils.fromJson(goodsMap.get("list"), new
		// TypeToken<List<String>>() {
		// });
		//
		// if (CollectionUtils.isNotEmpty(goodsList)) {
		// goodsList.add(0, "");
		// } else {
		// purchaseNameList = new ArrayList<String>();
		// }
		// }
		map.put("goodsList", goodsList);

		/* 查询颜色列表 */
		Map<String, Object> colorMap = getUrl(COLOR_LIST_URL);
		List<ColorInfoModel> colorList = new ArrayList<ColorInfoModel>();
		if (colorMap != null && !colorMap.isEmpty()) {
			colorList = GsonUtils.fromJson(colorMap.get("list"), new TypeToken<List<ColorInfoModel>>() {
			});
			if (colorList == null) {
				colorList = new ArrayList<ColorInfoModel>();
			}
		}
		map.put("colorList", colorList);

		/* 查询尺寸列表 */
		Map<String, Object> sizeMap = getUrl(SIZE_LIST_URL);
		List<SizeInfoModel> sizeList = new ArrayList<SizeInfoModel>();
		if (sizeMap != null && !sizeMap.isEmpty()) {
			sizeList = GsonUtils.fromJson(sizeMap.get("list"), new TypeToken<List<SizeInfoModel>>() {
			});
			if (sizeList == null) {
				sizeList = new ArrayList<SizeInfoModel>();
			}
		}
		map.put("sizeList", sizeList);

		// 查询用户列表
		Map<String, Object> userMap = getUrl(USER_LIST_URL);
		List<UserInfoModel> userList = new ArrayList<UserInfoModel>();
		if (userMap != null && !userMap.isEmpty()) {
			userList = GsonUtils.fromJson(userMap.get("list"), new TypeToken<List<UserInfoModel>>() {
			});
			if (userList == null) {
				userList = new ArrayList<UserInfoModel>();
			}
		}
		map.put("userList", userList);
	}

	/**
	 * 调用URL获取返回参数
	 * 
	 * @param url
	 * @return
	 */
	private static Map<String, Object> getUrl(String url) {
		Map<String, Object> obj = null;

		String response = HttpClientUtils.doGet(url, null);

		ResponseVo responseObj = GsonUtils.fromJson(response, ResponseVo.class);
		if (responseObj == null) {
			return obj;
		}

		if (ForeverConstant.SUCCESS_STATUS.equals(responseObj.getRet())) {
			responseObj = JsonUtils.fromJson(response, ResponseVo.class);
			obj = responseObj.getData();
			if (obj == null) {
				obj = new HashMap<String, Object>();
			}
		}

		return obj;
	}

}
