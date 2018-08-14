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
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jdesktop.swingx.JXDatePicker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gaoan.forever.combobox.KeyValComboBox;
import com.gaoan.forever.common.InitCondition;
import com.gaoan.forever.common.InitData;
import com.gaoan.forever.config.ServerApiConfig;
import com.gaoan.forever.constant.MessageEnum;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.model.ColorInfoModel;
import com.gaoan.forever.model.KeyValBoxVo;
import com.gaoan.forever.model.OrderQueryConditionModel;
import com.gaoan.forever.model.RepaintContentParamVo;
import com.gaoan.forever.model.SizeInfoModel;
import com.gaoan.forever.model.StockInfoModel;
import com.gaoan.forever.tablemodel.StockTableModel;
import com.gaoan.forever.utils.component.ComponentUtils;
import com.gaoan.forever.utils.date.DateUtil;
import com.gaoan.forever.utils.gson.GsonUtils;
import com.gaoan.forever.utils.http.CallUtils;
import com.github.pagehelper.PageInfo;
import com.google.gson.reflect.TypeToken;

public class StockTab {

	private static final Logger logger = LoggerFactory.getLogger(StockTab.class);
	private static final String STOCK_URL = ServerApiConfig.getApiGetStockList();
	private static final String GOODS_LIST_URL = ServerApiConfig.getApiGetGoodsList();

	public static JComboBox<String> purchaseOptions;
	public static JComboBox<String> goodsOptions;
	public static KeyValComboBox<KeyValBoxVo> colorComboBox;
	public static KeyValComboBox<KeyValBoxVo> sizeComboBox;
	public static JFrame jf;

	@SuppressWarnings("unchecked")
	public static JComponent show(JFrame window) {

		jf = window;
		// 查询进货单列表
		List<String> purchaseNameList = (List<String>) InitData.map.get("purchaseList");

		// 查询条件-初始化进货单标头
		JLabel purchaseLabel = new JLabel(MessageInfoConstant.PURCHASE_ORDER_NAME);
		// 查询条件-进货单名称下拉列表框
		purchaseOptions = new JComboBox<String>(purchaseNameList.toArray(new String[] {}));
		purchaseOptions.setMaximumSize(new Dimension(120, 25));
		purchaseOptions.setPreferredSize(new Dimension(120, 25));

		// 查询商品列表
		List<String> goodsList = (List<String>) InitData.map.get("goodsList");
		// 查询条件-初始化商品名称标头
		JLabel goodsLabel = new JLabel(MessageInfoConstant.GOODS_NAME);
		// 查询条件-初始化商品下拉列表
		goodsOptions = new JComboBox<String>(goodsList.toArray(new String[] {}));
		goodsOptions.setMaximumSize(new Dimension(120, 25));
		goodsOptions.setPreferredSize(new Dimension(120, 25));

		// 颜色集合
		List<ColorInfoModel> colorList = (List<ColorInfoModel>) InitData.map.get("colorList");
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
		colorComboBox = new KeyValComboBox<KeyValBoxVo>(colorVector);
		colorComboBox.setMaximumSize(new Dimension(120, 25));
		colorComboBox.setPreferredSize(new Dimension(120, 25));

		// 尺寸集合
		List<SizeInfoModel> sizeList = (List<SizeInfoModel>) InitData.map.get("sizeList");
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
		sizeComboBox = new KeyValComboBox<KeyValBoxVo>(sizeVector);
		sizeComboBox.setMaximumSize(new Dimension(120, 25));
		sizeComboBox.setPreferredSize(new Dimension(120, 25));

		// 查询条件-初始化开始时间标头
		JLabel startTimeLabel = new JLabel(MessageInfoConstant.START_TIME);
		// 查询条件-开始时间
		JXDatePicker startTimeDatepick = new JXDatePicker();
		// 设置 date日期
		startTimeDatepick.setFormats("yyyy-MM-dd");
		startTimeDatepick.setMaximumSize(new Dimension(120, 25));
		startTimeDatepick.setPreferredSize(new Dimension(120, 25));
		// 查询条件-结束时间
		JLabel endTimeLabel = new JLabel(MessageInfoConstant.END_TIME);
		JXDatePicker endTimeDatepick = new JXDatePicker();
		// 设置 date日期
		endTimeDatepick.setFormats("yyyy-MM-dd");
		endTimeDatepick.setMaximumSize(new Dimension(120, 25));
		endTimeDatepick.setPreferredSize(new Dimension(120, 25));

		// 查询条件-初始查询按钮
		JButton searchBtn = new JButton(MessageInfoConstant.SEARCH);
		JButton resetBtn = new JButton(MessageInfoConstant.RESET);

		// 初始化查询条件栏
		Box conditionBox = Box.createHorizontalBox();
		conditionBox.add(purchaseLabel);
		conditionBox.add(purchaseOptions);
		conditionBox.add(goodsLabel);
		conditionBox.add(goodsOptions);
		conditionBox.add(colorNameLabel);
		conditionBox.add(colorComboBox);
		conditionBox.add(sizeNameLabel);
		conditionBox.add(sizeComboBox);
		conditionBox.add(startTimeLabel);
		conditionBox.add(startTimeDatepick);
		conditionBox.add(endTimeLabel);
		conditionBox.add(endTimeDatepick);
		conditionBox.add(searchBtn);
		conditionBox.add(resetBtn);

		// 查询订货单列表
		JTable table = null;
		StockTableModel tableModel = new StockTableModel();

		PageInfo<StockInfoModel> pageInfo = null;

		List<StockInfoModel> orderList = new ArrayList<StockInfoModel>();
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

		// 设置只能选择一行
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// 设置行高
		table.setRowHeight(30);

		// 第一列列宽设置为50
		table.getColumnModel().getColumn(0).setPreferredWidth(50);

		// 设置滚动面板视口大小（超过该大小的行数据，需要拖动滚动条才能看到）
		table.setPreferredScrollableViewportSize(new Dimension(400, 400));

		// 把 表格放到 滚动面板 中（表头将自动添加到滚动面板顶部）
		JScrollPane scrollPane = new JScrollPane(table);

		// 初始化内容栏
		Box contentBox = Box.createHorizontalBox();
		contentBox.add(scrollPane);

		// 初始化整个页面
		Box mainBox = Box.createVerticalBox();

		// 初始化页码栏
		Box bottomBox = ComponentUtils.initBottomBox(pageInfo, STOCK_URL, new OrderQueryConditionModel(), window,
				tableModel, mainBox);

		mainBox.add(conditionBox);
		mainBox.add(contentBox);
		mainBox.add(bottomBox);

		// 添加事件监听
		RepaintContentParamVo<StockInfoModel> paramVo = new RepaintContentParamVo<StockInfoModel>();
		paramVo.setWindow(window);
		paramVo.setPurchaseOptions(purchaseOptions);
		paramVo.setGoodsOptions(goodsOptions);
		paramVo.setColorComboBox(colorComboBox);
		paramVo.setSizeComboBox(sizeComboBox);
		paramVo.setStartDatePicker(startTimeDatepick);
		paramVo.setEndDatePicker(endTimeDatepick);
		paramVo.setSearchBtn(searchBtn);
		paramVo.setResetBtn(resetBtn);
		paramVo.setTableModel(tableModel);
		paramVo.setTable(table);
		paramVo.setMainBox(mainBox);

		addListener(paramVo);

		return mainBox;

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
	private static void addListener(RepaintContentParamVo<StockInfoModel> paramVo) {
		JFrame tabbedPane = paramVo.getWindow();
		JComboBox<String> purchaseOptions = paramVo.getPurchaseOptions();
		JComboBox<String> goodsOptions = paramVo.getGoodsOptions();
		KeyValComboBox<KeyValBoxVo> colorComboBox = paramVo.getColorComboBox();
		KeyValComboBox<KeyValBoxVo> sizeComboBox = paramVo.getSizeComboBox();
		JXDatePicker startDatePicker = paramVo.getStartDatePicker();
		JXDatePicker endDatePicker = paramVo.getEndDatePicker();
		JButton searchBtn = paramVo.getSearchBtn();
		JButton resetBtn = paramVo.getResetBtn();
		StockTableModel tableModel = (StockTableModel) paramVo.getTableModel();
		Box mainBox = paramVo.getMainBox();

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

					Map<String, Object> goodsMap = CallUtils.get(tabbedPane, GOODS_LIST_URL, goodsReq,
							MessageEnum.SEARCH_MSG);

					if (goodsMap != null && !goodsMap.isEmpty()) {
						List<String> goodsList = GsonUtils.fromJson(goodsMap.get("list"),
								new TypeToken<List<String>>() {
						});

						// 列表第一个新增空字符串
						goodsList.add(0, "");
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
				String colorIdStr = colorComboBox.getSelectedKey();
				String sizeIdStr = sizeComboBox.getSelectedKey();
				String startTime = DateUtil.dateFormatY1(startDatePicker.getDate());
				String endTime = DateUtil.dateFormatY1(endDatePicker.getDate());

				OrderQueryConditionModel model = new OrderQueryConditionModel();
				model.setPurchaseOrderName(purchaseOrderName);
				model.setGoodsName(goodsName);
				model.setColorId(StringUtils.isEmpty(colorIdStr) ? null : Long.valueOf(colorIdStr));
				model.setSizeId(StringUtils.isEmpty(sizeIdStr) ? null : Long.valueOf(sizeIdStr));
				model.setStartTime(startTime);
				model.setEndTime(endTime);

				Map<String, Object> salesOrderMap = CallUtils.post(tabbedPane, STOCK_URL, GsonUtils.toJson(model),
						MessageEnum.SEARCH_MSG);

				PageInfo<StockInfoModel> pageInfo = GsonUtils.fromJson(salesOrderMap.get("pageInfo"),
						new TypeToken<PageInfo<StockInfoModel>>() {
				});

				List<StockInfoModel> orderList = new ArrayList<StockInfoModel>();
				if (pageInfo != null) {
					orderList = pageInfo.getList();
				}
				// 数据改变，重新加载数据
				tableModel.setData(orderList);
				tableModel.fireTableDataChanged();

				mainBox.remove(2);
				mainBox.add(ComponentUtils.initBottomBox(pageInfo, STOCK_URL, model, tabbedPane, tableModel, mainBox));
				mainBox.repaint();
			}
		});

		// 重置添加监听
		resetBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				purchaseOptions.setSelectedIndex(0);
				goodsOptions.setSelectedIndex(0);
				colorComboBox.setSelectedIndex(0);
				sizeComboBox.setSelectedIndex(0);
				startDatePicker.setDate(null);
				endDatePicker.setDate(null);
			}
		});
	}

	public static void initPurchaseAndGoodsCondition() {
		InitCondition.initPurchaseAndGoodsCondition(jf, purchaseOptions, goodsOptions);
	}

	public static void initColorCondition() {
		InitCondition.initColorCondition(jf, colorComboBox);
	}

	public static void initSizeCondition() {
		InitCondition.initSizeCondition(jf, sizeComboBox);
	}
}
