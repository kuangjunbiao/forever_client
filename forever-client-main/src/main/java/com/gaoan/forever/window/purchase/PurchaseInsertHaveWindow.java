package com.gaoan.forever.window.purchase;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.math.BigDecimal;
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
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.gaoan.forever.combobox.KeyValComboBox;
import com.gaoan.forever.config.ServerApiConfig;
import com.gaoan.forever.constant.MessageEnum;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.dialog.Dialog;
import com.gaoan.forever.model.ColorInfoModel;
import com.gaoan.forever.model.KeyValBoxVo;
import com.gaoan.forever.model.PurchaseOrderInfoModel;
import com.gaoan.forever.model.RepaintContentParamVo;
import com.gaoan.forever.model.SizeInfoModel;
import com.gaoan.forever.tab.PurchaseOrderTab;
import com.gaoan.forever.utils.character.StringUtils;
import com.gaoan.forever.utils.component.ComponentUtils;
import com.gaoan.forever.utils.gson.GsonUtils;
import com.gaoan.forever.utils.http.CallUtils;
import com.google.gson.reflect.TypeToken;

public class PurchaseInsertHaveWindow {

	private static final Logger logger = LoggerFactory.getLogger(PurchaseInsertHaveWindow.class);
	private static final String INSERT_PURCHASE_URL = ServerApiConfig.getApiInsertPurchaseOrderByHave();
	private static final String PURCHASE_NAME_LIST_URL = ServerApiConfig.getApiGetPurchaseNameList();
	private static final String GOODS_LIST_URL = ServerApiConfig.getApiGetGoodsList();
	private static final String COLOR_LIST_URL = ServerApiConfig.getApiGetColorList();
	private static final String SIZE_LIST_URL = ServerApiConfig.getApiGetSizeList();

	/**
	 * 展示窗口
	 * 
	 * @param relativeWindow
	 */
	public static void show(JFrame relativeWindow, RepaintContentParamVo<PurchaseOrderInfoModel> parentParamVo) {
		// 创建一个新窗口
		JFrame insertJFrame = new JFrame(MessageInfoConstant.INSERT_HAVE);

		insertJFrame.setSize(400, 340);

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
		// purchaseNameOptions.setMaximumSize(new Dimension(160, 30));
		// purchaseNameOptions.setPreferredSize(new Dimension(160, 30));

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

		// 數量BOX
		JLabel qtyLabel = new JLabel(MessageInfoConstant.QTY);
		JTextField qtyText = new JTextField();
		qtyLabel.setBounds(80, 180, 80, 35);
		qtyText.setBounds(160, 185, 140, 25);

		// 价格BOX
		JLabel priceLabel = new JLabel(MessageInfoConstant.COST_PRICE);
		JTextField priceText = new JTextField();
		priceLabel.setBounds(80, 220, 80, 35);
		priceText.setBounds(160, 225, 140, 25);

		// 按钮BOX
		JButton confirmBtn = new JButton(MessageInfoConstant.COMFIRM);
		JButton cancelBtn = new JButton(MessageInfoConstant.CANCLE);
		confirmBtn.setBounds(110, 260, 70, 30);
		cancelBtn.setBounds(200, 260, 70, 30);

		panel.add(purchaseNameLabel);
		panel.add(purchaseNameOptions);
		panel.add(goodsNameLabel);
		panel.add(goodsNameOptions);
		panel.add(colorNameLabel);
		panel.add(colorComboBox);
		panel.add(sizeNameLabel);
		panel.add(sizeComboBox);
		panel.add(qtyLabel);
		panel.add(qtyText);
		panel.add(priceLabel);
		panel.add(priceText);
		panel.add(confirmBtn);
		panel.add(cancelBtn);

		RepaintContentParamVo<PurchaseOrderInfoModel> currParamVo = new RepaintContentParamVo<PurchaseOrderInfoModel>();
		currParamVo.setWindow(insertJFrame);
		currParamVo.setPurchaseOptions(purchaseNameOptions);
		currParamVo.setGoodsOptions(goodsNameOptions);
		currParamVo.setColorComboBox(colorComboBox);
		currParamVo.setSizeComboBox(sizeComboBox);
		currParamVo.setQtyText(qtyText);
		currParamVo.setPriceText(priceText);
		currParamVo.setConfirmButton(confirmBtn);
		currParamVo.setCancelButton(cancelBtn);

		addListener(currParamVo, parentParamVo);

		insertJFrame.setContentPane(panel);
		insertJFrame.setVisible(true);
	}

	public static void addListener(RepaintContentParamVo<PurchaseOrderInfoModel> currParamVo,
			RepaintContentParamVo<PurchaseOrderInfoModel> parentParamVo) {
		JFrame insertJFrame = currParamVo.getWindow();
		JComboBox<String> purchaseNameOptions = currParamVo.getPurchaseOptions();
		JComboBox<String> goodsNameOptions = currParamVo.getGoodsOptions();
		KeyValComboBox<KeyValBoxVo> colorComboBox = currParamVo.getColorComboBox();
		KeyValComboBox<KeyValBoxVo> sizeComboBox = currParamVo.getSizeComboBox();
		JTextField qtyText = currParamVo.getQtyText();
		JTextField priceText = currParamVo.getPriceText();
		JButton confirmBtn = currParamVo.getConfirmButton();
		JButton cancelBtn = currParamVo.getCancelButton();

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
						// 初始化商品列表下拉框
						ComponentUtils.initBoxData(goodsNameOptions, goodsList);
					}
				}
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
				String qty = qtyText.getText();
				String price = priceText.getText();

				if (!StringUtils.isNum(qty) || !StringUtils.isNum(price)) {
					Dialog.showWarningDialog(insertJFrame, MessageInfoConstant.TITLE_PROMPT,
							MessageInfoConstant.PLEASE_CHECK_PARAM);
					return;
				}

				logger.info("purchaseName = {}, goodsName = {}, colorId = {}, sizeId = {}, qty = {}, price = {}",
						purchaseName, goodsName, colorIdStr, sizeIdStr, qty, price);

				// 请求参数
				PurchaseOrderInfoModel queryObj = new PurchaseOrderInfoModel();
				queryObj.setPurchaseOrderName(purchaseName);
				queryObj.setGoodsName(goodsName);
				queryObj.setColorId(Long.valueOf(colorIdStr));
				queryObj.setSizeId(Long.valueOf(sizeIdStr));
				queryObj.setQuantity(Long.valueOf(qty));
				queryObj.setCostPrice(new BigDecimal(price));

				Map<String, Object> result = CallUtils.executePost(insertJFrame, INSERT_PURCHASE_URL, null,
						JSON.toJSONString(queryObj), MessageEnum.INSERT_MSG, true);
				if (result != null) {
					insertJFrame.dispose();
					PurchaseOrderTab.repaintContentBox(parentParamVo);
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
