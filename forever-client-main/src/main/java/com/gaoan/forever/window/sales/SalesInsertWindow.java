package com.gaoan.forever.window.sales;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.gaoan.forever.combobox.KeyValComboBox;
import com.gaoan.forever.config.ServerApiConfig;
import com.gaoan.forever.constant.ForeverConstant;
import com.gaoan.forever.constant.MessageEnum;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.dialog.Dialog;
import com.gaoan.forever.model.ColorInfoModel;
import com.gaoan.forever.model.KeyValBoxVo;
import com.gaoan.forever.model.RepaintContentParamVo;
import com.gaoan.forever.model.SalesOrderInfoModel;
import com.gaoan.forever.model.SizeInfoModel;
import com.gaoan.forever.tab.SalesOrderTab;
import com.gaoan.forever.utils.character.StringUtils;
import com.gaoan.forever.utils.component.ComponentUtils;
import com.gaoan.forever.utils.gson.GsonUtils;
import com.gaoan.forever.utils.http.CallUtils;
import com.google.gson.reflect.TypeToken;

public class SalesInsertWindow {

	private static final Logger logger = LoggerFactory.getLogger(SalesInsertWindow.class);

	private static final String INSERT_SALES_URL = ServerApiConfig.getApiInsertSalesOrder();
	private static final String PURCHASE_NAME_LIST_URL = ServerApiConfig.getApiGetPurchaseNameList();
	private static final String GOODS_LIST_URL = ServerApiConfig.getApiGetGoodsList();
	private static final String GOODS_COLOR_LIST_URL = ServerApiConfig.getApiGetGoodsColorList();
	private static final String GOODS_SIZE_LIST_URL = ServerApiConfig.getApiGetGoodsSizeList();
	private static final String COLOR_LIST_URL = ServerApiConfig.getApiGetColorList();
	private static final String SIZE_LIST_URL = ServerApiConfig.getApiGetSizeList();

	/**
	 * 展示窗口
	 * 
	 * @param relativeWindow
	 */
	public static void show(JFrame relativeWindow, RepaintContentParamVo<SalesOrderInfoModel> parentParamVo) {
		// 创建一个新窗口
		JFrame insertJFrame = new JFrame(MessageInfoConstant.INSERT);

		insertJFrame.setSize(400, 480);

		// 把新窗口的位置设置到 relativeWindow 窗口的中心
		insertJFrame.setLocationRelativeTo(relativeWindow);

		// 点击窗口关闭按钮, 执行销毁窗口操作（如果设置为 EXIT_ON_CLOSE, 则点击新窗口关闭按钮后, 整个进程将结束）
		insertJFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// 窗口设置为不可改变大小
		insertJFrame.setResizable(false);

		JPanel panel = new JPanel(null);

		// 查询进货单列表
		Map<String, Object> purchaseMap = CallUtils.get(relativeWindow, PURCHASE_NAME_LIST_URL, null,
				MessageEnum.SEARCH_MSG);

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

		// 商品BOX
		JLabel purchaseNameLabel = new JLabel(MessageInfoConstant.PURCHASE_ORDER_NAME);
		JComboBox<String> purchaseNameOptions = new JComboBox<String>(purchaseNameList.toArray(new String[] {}));
		purchaseNameLabel.setBounds(80, 20, 80, 35);
		purchaseNameOptions.setBounds(160, 25, 140, 25);

		// 查询商品列表
		// Map<String, Object> goodsMap = CallUtils.get(relativeWindow,
		// GOODS_LIST_URL, null, MessageEnum.SEARCH_MSG);
		List<String> goodsList = new ArrayList<String>();
		/// 默认不查商品, 根据进货单再去查询
		// if (goodsMap != null && !goodsMap.isEmpty()) {
		// goodsList = GsonUtils.fromJson(goodsMap.get("list"), new
		// TypeToken<List<String>>() {
		// });
		// if (CollectionUtils.isNotEmpty(goodsList)) {
		// goodsList.add(0, "");
		// } else {
		// goodsList = new ArrayList<String>();
		// }
		// }

		// 商品BOX
		JLabel goodsNameLabel = new JLabel(MessageInfoConstant.GOODS_NAME);
		JComboBox<String> goodsNameOptions = new JComboBox<String>(goodsList.toArray(new String[] {}));
		goodsNameLabel.setBounds(80, 60, 80, 35);
		goodsNameOptions.setBounds(160, 65, 140, 25);

		// 颜色集合
		Map<String, Object> colorResult = CallUtils.get(insertJFrame, COLOR_LIST_URL, null, MessageEnum.SEARCH_MSG);
		List<ColorInfoModel> colorList = GsonUtils.fromJson(colorResult.get("list"),
				new TypeToken<List<ColorInfoModel>>() {
				});
		Vector<KeyValBoxVo> colorVector = new Vector<KeyValBoxVo>();
		colorVector.add(new KeyValBoxVo("", ""));
		if (CollectionUtils.isNotEmpty(colorList)) {
			KeyValBoxVo keyValBoxVo;
			for (ColorInfoModel color : colorList) {
				keyValBoxVo = new KeyValBoxVo(color.getId().toString(), color.getColorName());
				colorVector.add(keyValBoxVo);
			}
		}

		// 颜色BOX
		JLabel colorNameLabel = new JLabel(MessageInfoConstant.COLOR_NAME);
		KeyValComboBox<KeyValBoxVo> colorComboBox = new KeyValComboBox<KeyValBoxVo>(colorVector);
		colorNameLabel.setBounds(80, 100, 80, 35);
		colorComboBox.setBounds(160, 105, 140, 25);

		// 尺寸集合
		Map<String, Object> sizeResult = CallUtils.get(insertJFrame, SIZE_LIST_URL, null, MessageEnum.SEARCH_MSG);
		List<SizeInfoModel> sizeList = GsonUtils.fromJson(sizeResult.get("list"), new TypeToken<List<SizeInfoModel>>() {
		});
		Vector<KeyValBoxVo> sizeVector = new Vector<KeyValBoxVo>();
		sizeVector.add(new KeyValBoxVo("", ""));
		if (CollectionUtils.isNotEmpty(sizeList)) {
			KeyValBoxVo keyValBoxVo;
			for (SizeInfoModel size : sizeList) {
				keyValBoxVo = new KeyValBoxVo(size.getId().toString(), size.getSizeName());
				sizeVector.add(keyValBoxVo);
			}
		}

		// 尺寸BOX
		JLabel sizeNameLabel = new JLabel(MessageInfoConstant.SIZE_NAME);
		KeyValComboBox<KeyValBoxVo> sizeComboBox = new KeyValComboBox<KeyValBoxVo>(sizeVector);
		sizeNameLabel.setBounds(80, 140, 80, 35);
		sizeComboBox.setBounds(160, 145, 140, 25);

		// 吊牌价格BOX
		JLabel tagPriceLabel = new JLabel(MessageInfoConstant.TAG_PRICE);
		JTextField tagPriceText = new JTextField();
		tagPriceLabel.setBounds(80, 180, 80, 35);
		tagPriceText.setBounds(160, 185, 140, 25);

		// 成交价格BOX
		JLabel sellPriceLabel = new JLabel(MessageInfoConstant.SELL_PRICE);
		JTextField sellPriceText = new JTextField();
		sellPriceLabel.setBounds(80, 220, 80, 35);
		sellPriceText.setBounds(160, 225, 140, 25);

		// 折扣BOX
		JLabel discountLabel = new JLabel();
		discountLabel.setBounds(310, 220, 80, 35);

		// 數量BOX
		JLabel qtyLabel = new JLabel(MessageInfoConstant.QTY);
		JTextField qtyText = new JTextField();
		qtyLabel.setBounds(80, 260, 80, 35);
		qtyText.setBounds(160, 265, 140, 25);

		// 封装支付方式Vector
		Vector<KeyValBoxVo> payTypeVector = new Vector<KeyValBoxVo>();
		KeyValBoxVo wxPayTypeVo = new KeyValBoxVo(ForeverConstant.PAY_TYPE_WX, MessageInfoConstant.PAY_TYPE_WX);
		KeyValBoxVo zfbPayTypeVo = new KeyValBoxVo(ForeverConstant.PAY_TYPE_ZFB, MessageInfoConstant.PAY_TYPE_ZFB);
		KeyValBoxVo cashPayTypeVo = new KeyValBoxVo(ForeverConstant.PAY_TYPE_CASH, MessageInfoConstant.PAY_TYPE_CASH);
		payTypeVector.add(wxPayTypeVo);
		payTypeVector.add(zfbPayTypeVo);
		payTypeVector.add(cashPayTypeVo);

		KeyValComboBox<KeyValBoxVo> payTypeComboBox = new KeyValComboBox<KeyValBoxVo>(payTypeVector);

		// 支付方式BOX
		JLabel payTypeLabel = new JLabel(MessageInfoConstant.PAY_TYPE);
		payTypeLabel.setBounds(80, 300, 80, 35);
		payTypeComboBox.setBounds(160, 300, 140, 25);

		// 备注BOX
		JLabel remarkLabel = new JLabel(MessageInfoConstant.REMARK);
		JTextArea remarkArea = new JTextArea();
		remarkLabel.setBounds(80, 340, 80, 35);
		remarkArea.setBounds(160, 340, 140, 50);

		// 按钮BOX
		JButton confirmBtn = new JButton(MessageInfoConstant.COMFIRM);
		JButton cancelBtn = new JButton(MessageInfoConstant.CANCLE);
		confirmBtn.setBounds(110, 410, 70, 30);
		cancelBtn.setBounds(200, 410, 70, 30);

		panel.add(purchaseNameLabel);
		panel.add(purchaseNameOptions);
		panel.add(goodsNameLabel);
		panel.add(goodsNameOptions);
		panel.add(colorNameLabel);
		panel.add(colorComboBox);
		panel.add(sizeNameLabel);
		panel.add(sizeComboBox);
		panel.add(tagPriceLabel);
		panel.add(tagPriceText);
		panel.add(sellPriceLabel);
		panel.add(sellPriceText);
		panel.add(discountLabel);
		panel.add(qtyLabel);
		panel.add(qtyText);
		panel.add(payTypeLabel);
		panel.add(payTypeComboBox);
		panel.add(remarkLabel);
		panel.add(remarkArea);
		panel.add(confirmBtn);
		panel.add(cancelBtn);

		RepaintContentParamVo<SalesOrderInfoModel> paramVo = new RepaintContentParamVo<SalesOrderInfoModel>();
		paramVo.setWindow(insertJFrame);
		paramVo.setPurchaseOptions(purchaseNameOptions);
		paramVo.setGoodsOptions(goodsNameOptions);
		paramVo.setColorComboBox(colorComboBox);
		paramVo.setSizeComboBox(sizeComboBox);
		paramVo.setTagPriceText(tagPriceText);
		paramVo.setPriceText(sellPriceText);
		paramVo.setDiscountLabel(discountLabel);
		paramVo.setQtyText(qtyText);
		paramVo.setPayTypeComboBox(payTypeComboBox);
		paramVo.setRemarkArea(remarkArea);
		paramVo.setConfirmButton(confirmBtn);
		paramVo.setCancelButton(cancelBtn);

		addListener(paramVo, parentParamVo);

		insertJFrame.setContentPane(panel);
		insertJFrame.setVisible(true);
	}

	public static void addListener(RepaintContentParamVo<SalesOrderInfoModel> paramVo,
			RepaintContentParamVo<SalesOrderInfoModel> parentParamVo) {

		JFrame insertJFrame = paramVo.getWindow();
		JComboBox<String> purchaseNameOptions = paramVo.getPurchaseOptions();
		JComboBox<String> goodsNameOptions = paramVo.getGoodsOptions();
		KeyValComboBox<KeyValBoxVo> colorComboBox = paramVo.getColorComboBox();
		KeyValComboBox<KeyValBoxVo> sizeComboBox = paramVo.getSizeComboBox();
		KeyValComboBox<KeyValBoxVo> payTypeComboBox = paramVo.getPayTypeComboBox();
		JTextField qtyText = paramVo.getQtyText();
		JTextField tagPriceText = paramVo.getTagPriceText();
		JTextField priceText = paramVo.getPriceText();
		JLabel discountLabel = paramVo.getDiscountLabel();
		JTextArea remarkArea = paramVo.getRemarkArea();
		JButton confirmBtn = paramVo.getConfirmButton();
		JButton cancelBtn = paramVo.getCancelButton();

		// 进货单下拉框添加监听事件
		purchaseNameOptions.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// 处理选中的状态
				if (e.getStateChange() == ItemEvent.SELECTED) {
					logger.info("选中: {} = {} = {}", purchaseNameOptions.getSelectedIndex(),
							purchaseNameOptions.getSelectedItem(), purchaseNameOptions.getName());

					Map<String, String> goodsReq = new HashMap<String, String>();
					goodsReq.put("purchaseOrderName", (String) purchaseNameOptions.getSelectedItem());

					Map<String, Object> goodsMap = CallUtils.get(insertJFrame, GOODS_LIST_URL, goodsReq,
							MessageEnum.SEARCH_MSG);

					if (goodsMap != null && !goodsMap.isEmpty()) {
						List<String> goodsList = GsonUtils.fromJson(goodsMap.get("list"),
								new TypeToken<List<String>>() {
						});

						// 列表第一个新增空字符串
						goodsList.add(0, "");
						// 颜色、尺寸列表下拉框重置选择
						colorComboBox.setSelectedIndex(0);
						sizeComboBox.setSelectedIndex(0);
						// 初始化商品列表下拉框
						ComponentUtils.initBoxData(goodsNameOptions, goodsList);
					}
				}
			}
		});

		// 商品列表下拉框添加监听事件
		goodsNameOptions.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				// 处理选中的状态
				if (e.getStateChange() == ItemEvent.SELECTED) {
					logger.info("选中: {} = {} = {}", goodsNameOptions.getSelectedIndex(),
							goodsNameOptions.getSelectedItem(), goodsNameOptions.getName());

					String goodsName = (String) goodsNameOptions.getSelectedItem();
					if (org.apache.commons.lang3.StringUtils.isNotBlank(goodsName)) {

						Map<String, Object> reqParam = new HashMap<String, Object>();
						reqParam.put("purchaseOrderName", (String) purchaseNameOptions.getSelectedItem());
						reqParam.put("goodsName", (String) goodsNameOptions.getSelectedItem());

						Map<String, Object> colorMap = CallUtils.post(insertJFrame, GOODS_COLOR_LIST_URL,
								GsonUtils.toJson(reqParam), MessageEnum.SEARCH_MSG);

						if (colorMap != null && !colorMap.isEmpty()) {
							/* 初始化商品顏色列表下拉框 */
							List<ColorInfoModel> goodsColorList = GsonUtils.fromJson(colorMap.get("list"),
									new TypeToken<List<ColorInfoModel>>() {
							});

							List<Map<String, String>> colorMapList = new ArrayList<Map<String, String>>();
							if (CollectionUtils.isNotEmpty(goodsColorList)) {
								colorMapList.add(new HashMap<String, String>());
								goodsColorList.forEach((color) -> {
									Map<String, String> map = new HashMap<String, String>();
									map.put("key", color.getId() == null ? "" : color.getId().toString());
									map.put("value", color.getColorName());
									colorMapList.add(map);
								});
							}
							ComponentUtils.initBoxData(colorComboBox, colorMapList);

							// 初始化吊牌价
							BigDecimal tagPrice = (BigDecimal) colorMap.get("tagPrice");
							tagPriceText.setText(tagPrice == null ? "" : String.valueOf(tagPrice.longValue()));
						}
					} else {
						colorComboBox.setSelectedIndex(0);
						sizeComboBox.setSelectedIndex(0);
					}
				}
			}
		});

		// 颜色列表下拉框添加监听事件
		colorComboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// 处理选中的状态
				if (e.getStateChange() == ItemEvent.SELECTED) {
					logger.info("选中: {} = {} = {}", colorComboBox.getSelectedIndex(), colorComboBox.getSelectedItem(),
							colorComboBox.getName());

					KeyValBoxVo colorOption = (KeyValBoxVo) colorComboBox.getSelectedItem();
					if (org.apache.commons.lang3.StringUtils.isNotBlank(colorOption.getValue())) {

						Map<String, Object> reqParam = new HashMap<String, Object>();
						reqParam.put("purchaseOrderName", (String) purchaseNameOptions.getSelectedItem());
						reqParam.put("goodsName", (String) goodsNameOptions.getSelectedItem());
						reqParam.put("colorId", Long.valueOf(colorComboBox.getSelectedKey()));

						Map<String, Object> sizeMap = CallUtils.post(insertJFrame, GOODS_SIZE_LIST_URL,
								GsonUtils.toJson(reqParam), MessageEnum.SEARCH_MSG);

						if (sizeMap != null && !sizeMap.isEmpty()) {
							/* 初始化尺寸列表下拉框 */
							List<SizeInfoModel> goodsSizeList = GsonUtils.fromJson(sizeMap.get("list"),
									new TypeToken<List<SizeInfoModel>>() {
							});

							List<Map<String, String>> sizeMapList = new ArrayList<Map<String, String>>();
							if (CollectionUtils.isNotEmpty(goodsSizeList)) {
								sizeMapList.add(new HashMap<String, String>());
								goodsSizeList.forEach((size) -> {
									Map<String, String> map = new HashMap<String, String>();
									map.put("key", size.getId() == null ? "" : size.getId().toString());
									map.put("value", size.getSizeName());
									sizeMapList.add(map);
								});
							}
							ComponentUtils.initBoxData(sizeComboBox, sizeMapList);
						}
					} else {
						sizeComboBox.setSelectedIndex(0);
					}
				}
			}
		});

		priceText.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				logger.info("priceText失去焦点.");
				if (StringUtils.isNotEmpty(tagPriceText.getText()) && StringUtils.isNotEmpty(priceText.getText())) {
					BigDecimal discount = new BigDecimal(priceText.getText())
							.divide(new BigDecimal(tagPriceText.getText()), 2, BigDecimal.ROUND_DOWN)
							.multiply(BigDecimal.TEN).setScale(1, BigDecimal.ROUND_DOWN);
					discountLabel.setText(MessageFormat.format("约{0}折", discount.toPlainString()));
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				logger.info("priceText获取焦点.");
			}
		});

		tagPriceText.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				logger.info("tagPriceText失去焦点.");
				if (StringUtils.isNotEmpty(tagPriceText.getText()) && StringUtils.isNotEmpty(priceText.getText())) {
					BigDecimal discount = new BigDecimal(priceText.getText())
							.divide(new BigDecimal(tagPriceText.getText()), 2, BigDecimal.ROUND_DOWN)
							.multiply(BigDecimal.TEN).setScale(1, BigDecimal.ROUND_DOWN);
					discountLabel.setText(MessageFormat.format("约{0}折", discount.toPlainString()));
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				logger.info("tagPriceText获取焦点.");
			}
		});

		confirmBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("新增已有页面,点击确认按钮.");

				String purchaseName = (String) purchaseNameOptions.getSelectedItem();
				String goodsName = (String) goodsNameOptions.getSelectedItem();
				String colorIdStr = colorComboBox.getSelectedKey();
				String sizeIdStr = sizeComboBox.getSelectedKey();
				String payType = payTypeComboBox.getSelectedKey();
				String qty = qtyText.getText();
				String tagPrice = tagPriceText.getText();
				String price = priceText.getText();
				String remark = remarkArea.getText();

				if (!StringUtils.isNum(qty) || !StringUtils.isNum(tagPrice) || !StringUtils.isNum(price)) {
					Dialog.showWarningDialog(insertJFrame, MessageInfoConstant.TITLE_PROMPT,
							MessageInfoConstant.PLEASE_CHECK_PARAM);
					return;
				}

				logger.info(
						"purchaseName = {}, goodsName = {}, colorId = {}, sizeId = {}, price = {}, tagPrice = {}, qty = {}, payType = {} ",
						purchaseName, goodsName, colorIdStr, sizeIdStr, price, tagPrice, qty, payType);

				// 请求参数
				SalesOrderInfoModel queryObj = new SalesOrderInfoModel();
				queryObj.setPurchaseOrderName(purchaseName);
				queryObj.setGoodsName(goodsName);
				queryObj.setColorId(StringUtils.isEmpty(colorIdStr) ? null : Long.valueOf(colorIdStr));
				queryObj.setSizeId(StringUtils.isEmpty(sizeIdStr) ? null : Long.valueOf(sizeIdStr));
				queryObj.setTagPrice(new BigDecimal(tagPrice));
				queryObj.setSellPrice(new BigDecimal(price));
				queryObj.setQuantity(Long.valueOf(qty));
				queryObj.setPayType(Integer.valueOf(payType));
				queryObj.setRemark(remark);

				Map<String, Object> result = CallUtils.executePost(insertJFrame, INSERT_SALES_URL, null,
						JSON.toJSONString(queryObj), MessageEnum.INSERT_MSG, true);
				if (result != null) {
					insertJFrame.dispose();
					SalesOrderTab.repaintContentBox(parentParamVo);
				}
			}
		});

		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("新增已有界面, 点击取消按钮.");
				insertJFrame.dispose();
			}
		});

	}

}
