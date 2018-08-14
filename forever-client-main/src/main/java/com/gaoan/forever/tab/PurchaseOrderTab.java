package com.gaoan.forever.tab;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gaoan.forever.constant.MessageEnum;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.model.OrderQueryConditionModel;
import com.gaoan.forever.model.PurchaseOrderInfoModel;
import com.gaoan.forever.tablemodel.PurchaseOrderTableModel;
import com.gaoan.forever.utils.component.ComponentUtils;
import com.gaoan.forever.utils.gson.GsonUtils;
import com.gaoan.forever.utils.http.CallUtils;
import com.github.pagehelper.PageInfo;
import com.google.gson.reflect.TypeToken;

public class PurchaseOrderTab {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderTab.class);

    private static final String purchaseOrderUrl = "http://127.0.0.1:8066/api/purchase/getPurchaseOrderList";

    private static final String purchaseNameListUrl = "http://127.0.0.1:8066/api/stock/getPurchaseNameList";

    private static final String goodsListUrl = "http://127.0.0.1:8066/api/stock/getGoodsList";

    public static JComponent show(JFrame tabbedPane) {

	// 查询进货单列表
	Map<String, Object> purchaseMap = CallUtils.get(tabbedPane, purchaseNameListUrl, null, MessageEnum.SEARCH_MSG);

	List<String> purchaseNameList = new ArrayList<String>();
	if (purchaseMap != null && !purchaseMap.isEmpty()) {
	    PageInfo<String> pageInfo = GsonUtils.fromJson(purchaseMap.get("pageInfo"),
		    new TypeToken<PageInfo<String>>() {
		    });

	    if (pageInfo != null) {
		purchaseNameList = pageInfo.getList();
		if (CollectionUtils.isNotEmpty(purchaseNameList)) {
		    purchaseNameList.add(0, "");
		} else {
		    purchaseNameList = new ArrayList<String>();
		}
	    }
	}

	// 查询条件-初始化进货单标头
	JLabel purchaseLabel = new JLabel(MessageInfoConstant.PURCHASE_ORDER_NAME);
	// 查询条件-进货单名称下拉列表框
	JComboBox<String> purchaseOptions = new JComboBox<String>(purchaseNameList.toArray(new String[] {}));
	purchaseOptions.setMaximumSize(new Dimension(120, 25));
	purchaseOptions.setPreferredSize(new Dimension(120, 25));

	// 查询商品列表
	Map<String, Object> goodsMap = CallUtils.get(tabbedPane, goodsListUrl, null, MessageEnum.SEARCH_MSG);
	List<String> goodsList = new ArrayList<String>();
	if (goodsMap != null && !goodsMap.isEmpty()) {
	    PageInfo<String> pageInfo = GsonUtils.fromJson(goodsMap.get("pageInfo"), new TypeToken<PageInfo<String>>() {
	    });
	    if (pageInfo != null) {
		goodsList = pageInfo.getList();
		if (CollectionUtils.isNotEmpty(goodsList)) {
		    goodsList.add(0, "");
		} else {
		    goodsList = new ArrayList<String>();
		}
	    }
	}

	// 查询条件-初始化商品名称标头
	JLabel goodsLabel = new JLabel(MessageInfoConstant.GOODS_NAME);
	// 查询条件-初始化商品下拉列表
	JComboBox<String> goodsOptions = new JComboBox<String>(goodsList.toArray(new String[] {}));
	goodsOptions.setMaximumSize(new Dimension(120, 25));
	goodsOptions.setPreferredSize(new Dimension(120, 25));

	// 查询条件-初始化开始时间标头
	JLabel startTimeLabel = new JLabel(MessageInfoConstant.START_TIME);
	// 查询条件-开始时间
	JTextField startTimeField = new JTextField();
	startTimeField.setMaximumSize(new Dimension(120, 25));
	startTimeField.setPreferredSize(new Dimension(120, 25));

	// 查询条件-初始查询按钮
	JButton searchBtn = new JButton(MessageInfoConstant.SEARCH);

	// 初始化查询条件栏
	Box conditionBox = Box.createHorizontalBox();
	conditionBox.add(purchaseLabel);
	conditionBox.add(purchaseOptions);
	conditionBox.add(goodsLabel);
	conditionBox.add(goodsOptions);
	conditionBox.add(startTimeLabel);
	conditionBox.add(startTimeField);
	conditionBox.add(searchBtn);

	// 查询订货单列表
	Map<String, Object> purchaseOrderMap = CallUtils.post(tabbedPane, purchaseOrderUrl,
		GsonUtils.toJson(new OrderQueryConditionModel()), MessageEnum.SEARCH_MSG);

	JTable table = null;
	PurchaseOrderTableModel tableModel = new PurchaseOrderTableModel();

	PageInfo<PurchaseOrderInfoModel> pageInfo = null;

	List<PurchaseOrderInfoModel> orderList = null;

	if (purchaseOrderMap == null || purchaseOrderMap.isEmpty()) {
	    orderList = new ArrayList<PurchaseOrderInfoModel>();
	    table = new JTable();
	} else {
	    pageInfo = GsonUtils.fromJson(purchaseOrderMap.get("pageInfo"),
		    new TypeToken<PageInfo<PurchaseOrderInfoModel>>() {
		    });

	    if (pageInfo != null) {
		orderList = pageInfo.getList();
	    }
	    // 初始化tableModel
	    tableModel.setData(orderList);
	    table = new JTable(tableModel);

	    // 设置表格内容颜色
	    table.setForeground(Color.BLACK); // 字体颜色
	    table.setFont(new Font(null, Font.PLAIN, 14)); // 字体样式
	    table.setSelectionForeground(Color.DARK_GRAY); // 选中后字体颜色
	    table.setSelectionBackground(Color.LIGHT_GRAY); // 选中后字体背景
	    table.setGridColor(Color.GRAY); // 网格颜色

	    // 设置表头
	    table.getTableHeader().setFont(new Font(null, Font.BOLD, 14)); // 设置表头名称字体样式
	    table.getTableHeader().setForeground(Color.RED); // 设置表头名称字体颜色
	    table.getTableHeader().setResizingAllowed(false); // 设置不允许手动改变列宽
	    table.getTableHeader().setReorderingAllowed(false); // 设置不允许拖动重新排序各列

	    // 设置行高
	    table.setRowHeight(30);

	    // 第一列列宽设置为50
	    table.getColumnModel().getColumn(0).setPreferredWidth(50);

	    // 设置滚动面板视口大小（超过该大小的行数据，需要拖动滚动条才能看到）
	    table.setPreferredScrollableViewportSize(new Dimension(400, 400));
	}

	// 把 表格 放到 滚动面板 中（表头将自动添加到滚动面板顶部）
	JScrollPane scrollPane = new JScrollPane(table);

	// 初始化内容栏
	Box contentBox = Box.createHorizontalBox();
	contentBox.add(scrollPane);

	// 初始化整个页面
	Box vBox = Box.createVerticalBox();

	// 初始化页码栏
	Box bottomBox = ComponentUtils.initBottomBox(pageInfo, purchaseOrderUrl, new OrderQueryConditionModel(),
		tabbedPane, tableModel, vBox);

	vBox.add(conditionBox);
	vBox.add(contentBox);
	vBox.add(bottomBox);

	// 添加事件监听
	addListener(tabbedPane, purchaseOptions, goodsOptions, searchBtn, tableModel, vBox);

	return vBox;

    }

    /**
     * 添加监听
     * 
     * @param tabbedPane
     * @param purchaseOptions
     * @param goodsOptions
     * @param searchBtn
     * @param tableModel
     */
    private static void addListener(JFrame tabbedPane, JComboBox<String> purchaseOptions,
	    JComboBox<String> goodsOptions, JButton searchBtn, PurchaseOrderTableModel tableModel, Box mainBox) {

	// 进货单下拉框添加监听事件
	purchaseOptions.addItemListener(new ItemListener() {
	    @Override
	    public void itemStateChanged(ItemEvent e) {
		// 处理选中的状态
		if (e.getStateChange() == ItemEvent.SELECTED) {
		    logger.info("选中: {} = {} = {}", purchaseOptions.getSelectedIndex(),
			    purchaseOptions.getSelectedItem(), purchaseOptions.getName());

		    Map<String, String> goodsReq = new HashMap<String, String>();
		    goodsReq.put("purchaseOrderName", (String) purchaseOptions.getSelectedItem());

		    Map<String, Object> goodsMap = CallUtils.get(tabbedPane, goodsListUrl, goodsReq,
			    MessageEnum.SEARCH_MSG);

		    if (goodsMap != null && !goodsMap.isEmpty()) {
			PageInfo<Object> pageInfo = GsonUtils.fromJson(goodsMap.get("pageInfo"),
				new TypeToken<PageInfo<Object>>() {
			});

			List<Object> goodsList = new ArrayList<Object>();
			if (pageInfo != null) {
			    goodsList = pageInfo.getList();
			    // 列表第一个新增空字符串
			    goodsList.add(0, "");
			}
			// 初始化商品列表下拉框
			ComponentUtils.initBoxData(goodsOptions, goodsList);
		    }

		}
	    }
	});

	// 查询按钮添加监听
	searchBtn.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		String purchaseOrderName = (String) purchaseOptions.getSelectedItem();
		String goodsName = (String) goodsOptions.getSelectedItem();

		OrderQueryConditionModel model = new OrderQueryConditionModel();
		model.setPurchaseOrderName(purchaseOrderName);
		model.setGoodsName(goodsName);
		Map<String, Object> purchaseOrderMap = CallUtils.post(tabbedPane, purchaseOrderUrl,
			GsonUtils.toJson(model), MessageEnum.SEARCH_MSG);

		PageInfo<PurchaseOrderInfoModel> pageInfo = GsonUtils.fromJson(purchaseOrderMap.get("pageInfo"),
			new TypeToken<PageInfo<PurchaseOrderInfoModel>>() {
		});

		List<PurchaseOrderInfoModel> orderList = new ArrayList<PurchaseOrderInfoModel>();
		if (pageInfo != null) {
		    orderList = pageInfo.getList();
		}
		// 数据改变，重新加载数据
		tableModel.setData(orderList);
		tableModel.fireTableDataChanged();

		mainBox.remove(2);
		mainBox.add(ComponentUtils.initBottomBox(pageInfo, purchaseOrderUrl, model, tabbedPane, tableModel,
			mainBox));
		mainBox.repaint();
	    }
	});
    }
}
