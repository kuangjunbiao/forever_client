package com.gaoan.forever.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import org.apache.commons.collections.CollectionUtils;

import com.gaoan.forever.combobox.KeyValComboBox;
import com.gaoan.forever.config.ServerApiConfig;
import com.gaoan.forever.constant.MessageEnum;
import com.gaoan.forever.model.ColorInfoModel;
import com.gaoan.forever.model.KeyValBoxVo;
import com.gaoan.forever.model.SizeInfoModel;
import com.gaoan.forever.model.UserInfoModel;
import com.gaoan.forever.utils.component.ComponentUtils;
import com.gaoan.forever.utils.gson.GsonUtils;
import com.gaoan.forever.utils.http.CallUtils;
import com.google.gson.reflect.TypeToken;

public class InitCondition {

	private static final String PURCHASE_NAME_LIST_URL = ServerApiConfig.getApiGetPurchaseNameList();
	private static final String GOODS_LIST_URL = ServerApiConfig.getApiGetGoodsList();
	private static final String COLOR_LIST_URL = ServerApiConfig.getApiGetColorList();
	private static final String SIZE_LIST_URL = ServerApiConfig.getApiGetSizeList();
	private static final String USER_LIST_URL = ServerApiConfig.getApiGetUserList();

	public static void initPurchaseAndGoodsCondition(JFrame jf, JComboBox<String> purchaseOptions,
			JComboBox<String> goodsOptions) {
		// 1.初始化订货单下拉框
		// 查询进货单列表
		Map<String, Object> purchaseMap = CallUtils.get(jf, PURCHASE_NAME_LIST_URL, null, MessageEnum.SEARCH_MSG);

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

		ComponentUtils.initBoxData(purchaseOptions, purchaseNameList);

		// 2.初始化商品名称下拉框
		// 查询商品列表
		Map<String, Object> goodsMap = CallUtils.get(jf, GOODS_LIST_URL, null, MessageEnum.SEARCH_MSG);
		List<String> goodsList = new ArrayList<String>();
		if (goodsMap != null && !goodsMap.isEmpty()) {
			goodsList = GsonUtils.fromJson(goodsMap.get("list"), new TypeToken<List<String>>() {
			});
			if (CollectionUtils.isNotEmpty(goodsList)) {
				goodsList.add(0, "");
			} else {
				goodsList = new ArrayList<String>();
			}
		}

		ComponentUtils.initBoxData(goodsOptions, goodsList);
	}

	public static void initColorCondition(JFrame jf, KeyValComboBox<KeyValBoxVo> colorComboBox) {
		// 1.初始化顏色下拉框
		// 查询颜色列表
		Map<String, Object> colorMap = CallUtils.get(jf, COLOR_LIST_URL, null, MessageEnum.SEARCH_MSG);

		final List<Map<String, String>> colorMapList = new ArrayList<Map<String, String>>();
		if (colorMap != null && !colorMap.isEmpty()) {
			List<ColorInfoModel> colorList = GsonUtils.fromJson(colorMap.get("list"),
					new TypeToken<List<ColorInfoModel>>() {
					});

			if (CollectionUtils.isNotEmpty(colorList)) {
				colorMapList.add(new HashMap<String, String>());
				colorList.forEach((color) -> {
					Map<String, String> map = new HashMap<String, String>();
					map.put("key", color.getId().toString());
					map.put("value", color.getColorName());
					colorMapList.add(map);
				});
			}
		}
		ComponentUtils.initBoxData(colorComboBox, colorMapList);
	}

	public static void initSizeCondition(JFrame jf, KeyValComboBox<KeyValBoxVo> sizeComboBox) {
		// 初始化尺寸下拉框
		// 查询尺寸列表
		Map<String, Object> sizeMap = CallUtils.get(jf, SIZE_LIST_URL, null, MessageEnum.SEARCH_MSG);

		final List<Map<String, String>> sizeMapList = new ArrayList<Map<String, String>>();
		if (sizeMap != null && !sizeMap.isEmpty()) {
			List<SizeInfoModel> sizeList = GsonUtils.fromJson(sizeMap.get("list"),
					new TypeToken<List<SizeInfoModel>>() {
					});

			if (CollectionUtils.isNotEmpty(sizeList)) {
				sizeMapList.add(new HashMap<String, String>());
				sizeList.forEach((size) -> {
					Map<String, String> map = new HashMap<String, String>();
					map.put("key", size.getId().toString());
					map.put("value", size.getSizeName());
					sizeMapList.add(map);
				});
			}
		}
		ComponentUtils.initBoxData(sizeComboBox, sizeMapList);

	}

	public static void initUserCondition(JFrame jf, KeyValComboBox<KeyValBoxVo> userComboBox) {
		// 初始化用户下拉框
		// 查询用户列表
		Map<String, Object> userMap = CallUtils.get(jf, USER_LIST_URL, null, MessageEnum.SEARCH_MSG);

		final List<Map<String, String>> userMapList = new ArrayList<Map<String, String>>();
		if (userMap != null && !userMap.isEmpty()) {
			List<UserInfoModel> userList = GsonUtils.fromJson(userMap.get("list"),
					new TypeToken<List<UserInfoModel>>() {
					});

			if (CollectionUtils.isNotEmpty(userList)) {
				userMapList.add(new HashMap<String, String>());
				userList.forEach((user) -> {
					Map<String, String> map = new HashMap<String, String>();
					map.put("key", user.getId().toString());
					map.put("value", user.getUserName());
					userMapList.add(map);
				});
			}
		}
		ComponentUtils.initBoxData(userComboBox, userMapList);
	}
}
