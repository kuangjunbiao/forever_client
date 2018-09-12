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
import com.gaoan.forever.utils.gson.JsonUtils;
import com.gaoan.forever.utils.http.CallUtils;
import com.gaoan.forever.window.purchase.PurchaseInsertUnHaveWindow;
import com.google.gson.reflect.TypeToken;

public class SalesUpdateWindow {

	private static final Logger logger = LoggerFactory.getLogger(PurchaseInsertUnHaveWindow.class);

	private static final String QUERY_SALES_ORDER_DETAIL_URL = ServerApiConfig.getApiGetSalesOrderDetail();
	private static final String UPDATE_SALES_URL = ServerApiConfig.getApiUpdateSalesOrder();
	private static final String PURCHASE_NAME_LIST_URL = ServerApiConfig.getApiGetPurchaseNameList();
	private static final String GOODS_LIST_URL = ServerApiConfig.getApiGetGoodsList();
	private static final String COLOR_LIST_URL = ServerApiConfig.getApiGetColorList();
	private static final String SIZE_LIST_URL = ServerApiConfig.getApiGetSizeList();

	/**
	 * 展示窗口
	 * 
	 * @param relativeWindow
	 */
	@SuppressWarnings("unchecked")
	public static void show(JFrame relativeWindow, Long orderId,
			RepaintContentParamVo<SalesOrderInfoModel> parentParamVo) {
		// 创建一个新窗口
		JFrame updateJFrame = new JFrame(MessageInfoConstant.UPDATE);

		updateJFrame.setSize(400, 480);

		// 把新窗口的位置设置到 relativeWindow 窗口的中心
		updateJFrame.setLocationRelativeTo(relativeWindow);

		// 点击窗口关闭按钮, 执行销毁窗口操作（如果设置为 EXIT_ON_CLOSE, 则点击新窗口关闭按钮后, 整个进程将结束）
		updateJFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// 窗口设置为不可改变大小
		updateJFrame.setResizable(false);

		JPanel panel = new JPanel(null);

		// 根据编号查询当前角色信息
		Map<String, String> queryOrderParam = new HashMap<String, String>();
		queryOrderParam.put("orderId", orderId.toString());
		Map<String, Object> orderDetail = CallUtils.get(relativeWindow, QUERY_SALES_ORDER_DETAIL_URL, queryOrderParam,
				MessageEnum.SEARCH_MSG);
		Map<String, Object> orderDetailMap = (Map<String, Object>) orderDetail.get("info");
		SalesOrderInfoModel model = JsonUtils.fromJson(orderDetailMap, SalesOrderInfoModel.class);

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

		// 隐藏ID
		JTextField idText = new JTextField(model.getId().toString());
		idText.setVisible(false);

		// 商品BOX
		JLabel purchaseNameLabel = new JLabel(MessageInfoConstant.PURCHASE_ORDER_NAME);
		JComboBox<String> purchaseNameOptions = new JComboBox<String>(purchaseNameList.toArray(new String[] {}));
		purchaseNameOptions.setSelectedItem(model.getPurchaseOrderName());
		purchaseNameLabel.setBounds(80, 20, 80, 35);
		purchaseNameOptions.setBounds(160, 25, 140, 25);

		Map<String, String> goodsReq = new HashMap<String, String>();
		goodsReq.put("purchaseOrderName", (String) purchaseNameOptions.getSelectedItem());
		// 查询商品列表
		Map<String, Object> goodsMap = CallUtils.get(relativeWindow, GOODS_LIST_URL, goodsReq, MessageEnum.SEARCH_MSG);
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

		// 商品BOX
		JLabel goodsNameLabel = new JLabel(MessageInfoConstant.GOODS_NAME);
		JComboBox<String> goodsNameOptions = new JComboBox<String>(goodsList.toArray(new String[] {}));
		goodsNameOptions.setSelectedItem(model.getGoodsName());
		goodsNameLabel.setBounds(80, 60, 80, 35);
		goodsNameOptions.setBounds(160, 65, 140, 25);

		// 颜色集合
		Map<String, Object> colorResult = CallUtils.get(updateJFrame, COLOR_LIST_URL, null, MessageEnum.SEARCH_MSG);
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
		colorComboBox.setSelectedKey(model.getColorId());
		colorNameLabel.setBounds(80, 100, 80, 35);
		colorComboBox.setBounds(160, 105, 140, 25);

		// 尺寸集合
		Map<String, Object> sizeResult = CallUtils.get(updateJFrame, SIZE_LIST_URL, null, MessageEnum.SEARCH_MSG);
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
		sizeComboBox.setSelectedKey(model.getSizeId());
		sizeNameLabel.setBounds(80, 140, 80, 35);
		sizeComboBox.setBounds(160, 145, 140, 25);

		// 吊牌价格BOX
		JLabel tagPriceLabel = new JLabel(MessageInfoConstant.TAG_PRICE);
		JTextField tagPriceText = new JTextField(model.getTagPrice().setScale(0, BigDecimal.ROUND_UP).toPlainString());

		tagPriceLabel.setBounds(80, 180, 80, 35);
		tagPriceText.setBounds(160, 185, 140, 25);

		// 成交价BOX
		JLabel sellPriceLabel = new JLabel(MessageInfoConstant.SELL_PRICE);
		JTextField sellPriceText = new JTextField(
				model.getSellPrice().setScale(0, BigDecimal.ROUND_UP).toPlainString());
		sellPriceLabel.setBounds(80, 220, 80, 35);
		sellPriceText.setBounds(160, 225, 140, 25);

		BigDecimal discount = model.getSellPrice().divide(model.getTagPrice(), 2, BigDecimal.ROUND_DOWN)
				.multiply(BigDecimal.TEN).setScale(1, BigDecimal.ROUND_DOWN);
		// 折扣BOX
		JLabel discountLabel = new JLabel(MessageFormat.format("约{0}折", discount.toPlainString()));
		discountLabel.setBounds(310, 220, 80, 35);

		// 數量BOX
		JLabel qtyLabel = new JLabel(MessageInfoConstant.QTY);
		JTextField qtyText = new JTextField(String.valueOf(model.getQuantity()));
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
		payTypeComboBox.setSelectedKey(model.getPayType());

		// 支付方式BOX
		JLabel payTypeLabel = new JLabel(MessageInfoConstant.PAY_TYPE);
		payTypeLabel.setBounds(80, 300, 80, 35);
		payTypeComboBox.setBounds(160, 305, 140, 25);

		// 备注BOX
		JLabel remarkLabel = new JLabel(MessageInfoConstant.REMARK);
		JTextArea remarkArea = new JTextArea(model.getRemark());
		remarkLabel.setBounds(80, 340, 80, 35);
		remarkArea.setBounds(160, 345, 140, 50);

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
		paramVo.setWindow(updateJFrame);
		paramVo.setIdText(idText);
		paramVo.setPurchaseOptions(purchaseNameOptions);
		paramVo.setGoodsOptions(goodsNameOptions);
		paramVo.setColorComboBox(colorComboBox);
		paramVo.setSizeComboBox(sizeComboBox);
		paramVo.setPriceText(sellPriceText);
		paramVo.setQtyText(qtyText);
		paramVo.setTagPriceText(tagPriceText);
		paramVo.setDiscountLabel(discountLabel);
		paramVo.setPayTypeComboBox(payTypeComboBox);
		paramVo.setRemarkArea(remarkArea);
		paramVo.setConfirmButton(confirmBtn);
		paramVo.setCancelButton(cancelBtn);

		addListener(paramVo, parentParamVo);

		updateJFrame.setContentPane(panel);
		updateJFrame.setVisible(true);

	}

	public static void addListener(RepaintContentParamVo<SalesOrderInfoModel> paramVo,
			RepaintContentParamVo<SalesOrderInfoModel> parentParamVo) {

		JFrame updateJFrame = paramVo.getWindow();
		JTextField idText = paramVo.getIdText();
		JComboBox<String> purchaseNameOptions = paramVo.getPurchaseOptions();
		JComboBox<String> goodsNameOptions = paramVo.getGoodsOptions();
		KeyValComboBox<KeyValBoxVo> colorComboBox = paramVo.getColorComboBox();
		KeyValComboBox<KeyValBoxVo> sizeComboBox = paramVo.getSizeComboBox();
		JTextField priceText = paramVo.getPriceText();
		JTextField qtyText = paramVo.getQtyText();
		KeyValComboBox<KeyValBoxVo> payTypeComboBox = paramVo.getPayTypeComboBox();
		JTextField tagPriceText = paramVo.getTagPriceText();
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

					Map<String, Object> goodsMap = CallUtils.get(updateJFrame, GOODS_LIST_URL, goodsReq,
							MessageEnum.SEARCH_MSG);

					if (goodsMap != null && !goodsMap.isEmpty()) {
						List<String> goodsList = GsonUtils.fromJson(goodsMap.get("list"),
								new TypeToken<List<String>>() {
						});

						// 列表第一个新增空字符串
						goodsList.add(0, "");
						// 初始化商品列表下拉框
						ComponentUtils.initBoxData(goodsNameOptions, goodsList);
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

				Long id = Long.valueOf(idText.getText());
				String purchaseName = (String) purchaseNameOptions.getSelectedItem();
				String goodsName = (String) goodsNameOptions.getSelectedItem();
				String colorIdStr = colorComboBox.getSelectedKey();
				String sizeIdStr = sizeComboBox.getSelectedKey();
				String price = priceText.getText();
				String qty = qtyText.getText();
				String payType = (String) payTypeComboBox.getSelectedKey();
				String tagPrice = tagPriceText.getText();
				String remark = remarkArea.getText();

				if (!StringUtils.isNum(qty) || !StringUtils.isNum(tagPrice) || !StringUtils.isNum(price)) {
					Dialog.showWarningDialog(updateJFrame, MessageInfoConstant.TITLE_PROMPT,
							MessageInfoConstant.PLEASE_CHECK_PARAM);
					return;
				}

				logger.info(
						"id = {}, purchaseName = {}, goodsName = {}, tagPrice = {}, price = {}, qty = {}, payType = {}",
						id, purchaseName, goodsName, tagPrice, price, qty, payType);

				// 请求参数
				SalesOrderInfoModel queryObj = new SalesOrderInfoModel();
				queryObj.setId(id);
				queryObj.setPurchaseOrderName(purchaseName);
				queryObj.setGoodsName(goodsName);
				queryObj.setColorId(StringUtils.isEmpty(colorIdStr) ? null : Long.valueOf(colorIdStr));
				queryObj.setSizeId(StringUtils.isEmpty(sizeIdStr) ? null : Long.valueOf(sizeIdStr));
				queryObj.setQuantity(Long.valueOf(qty));
				queryObj.setSellPrice(new BigDecimal(price));
				queryObj.setTagPrice(new BigDecimal(tagPrice));
				queryObj.setPayType(Integer.valueOf(payType));
				queryObj.setRemark(remark);

				Map<String, Object> result = CallUtils.executePost(updateJFrame, UPDATE_SALES_URL, null,
						JSON.toJSONString(queryObj), MessageEnum.UPDATE_MSG, true);
				if (result != null) {
					updateJFrame.dispose();
					SalesOrderTab.repaintContentBox(parentParamVo);
				}
			}
		});

		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("新增已有界面, 点击取消按钮.");
				updateJFrame.dispose();
			}
		});

	}

}
