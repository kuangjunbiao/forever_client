package com.gaoan.forever.tab;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gaoan.forever.config.ServerApiConfig;
import com.gaoan.forever.constant.MessageEnum;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.dialog.Dialog;
import com.gaoan.forever.model.RepaintContentParamVo;
import com.gaoan.forever.model.SizeInfoModel;
import com.gaoan.forever.tablemodel.SizeTableModel;
import com.gaoan.forever.utils.component.ComponentUtils;
import com.gaoan.forever.utils.gson.GsonUtils;
import com.gaoan.forever.utils.http.CallUtils;
import com.gaoan.forever.window.size.SizeInsertWindow;
import com.github.pagehelper.PageInfo;
import com.google.gson.reflect.TypeToken;

public class SizeTab {

	private static final Logger logger = LoggerFactory.getLogger(SizeTab.class);

	private static final String SIZE_PAGE_URL = ServerApiConfig.getApiGetSizePage();

	private static final String DELETE_SIZE_URL = ServerApiConfig.getApiDeleteSize();

	public static JComponent show(JFrame window) {
		// 查询条件-初始查询按钮
		JButton searchBtn = new JButton(MessageInfoConstant.SEARCH);
		JButton insertBtn = new JButton(MessageInfoConstant.INSERT);
		JButton deleteBtn = new JButton(MessageInfoConstant.DELETE);

		// 初始化查询条件栏
		Box conditionBox = Box.createHorizontalBox();
		conditionBox.add(searchBtn);
		conditionBox.add(insertBtn);
		conditionBox.add(deleteBtn);

		// 查询颜色列表
		JTable table = null;
		SizeTableModel tableModel = new SizeTableModel();

		PageInfo<SizeInfoModel> pageInfo = null;

		List<SizeInfoModel> colorList = new ArrayList<SizeInfoModel>();
		// 初始化tableModel
		tableModel.setData(colorList);
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
		Box bottomBox = ComponentUtils.initBottomBox(pageInfo, SIZE_PAGE_URL, null, window, tableModel, mainBox);

		mainBox.add(conditionBox);
		mainBox.add(contentBox);
		mainBox.add(bottomBox);

		RepaintContentParamVo<SizeInfoModel> paramVo = new RepaintContentParamVo<SizeInfoModel>();
		paramVo.setWindow(window);
		paramVo.setSearchBtn(searchBtn);
		paramVo.setInsertBtn(insertBtn);
		paramVo.setDeleteBtn(deleteBtn);
		paramVo.setTableModel(tableModel);
		paramVo.setTable(table);
		paramVo.setMainBox(mainBox);

		addListener(paramVo);

		return mainBox;
	}

	/**
	 * 添加监听
	 * 
	 * @param window
	 * @param searchBtn
	 * @param tableModel
	 */
	private static void addListener(RepaintContentParamVo<SizeInfoModel> paramVo) {
		JFrame window = paramVo.getWindow();
		JButton searchBtn = paramVo.getSearchBtn();
		JButton insertBtn = paramVo.getInsertBtn();
		JButton deleteBtn = paramVo.getDeleteBtn();
		JTable table = paramVo.getTable();

		// 查询按钮添加监听
		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaintContentBox(paramVo);
			}
		});

		// 新增按钮添加监听
		insertBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SizeInsertWindow.show(window, paramVo);
			}
		});

		// 修改按钮添加监听
		deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row < 0) {
					Dialog.showWarningDialog(window, MessageInfoConstant.TITLE_PROMPT,
							MessageInfoConstant.PLEASE_CHOOSE_ONE);
				} else {
					int chooseResult = Dialog.showConfirmDialog(window, MessageInfoConstant.TITLE_PROMPT,
							MessageInfoConstant.COMFIRM_DELETE);
					if (chooseResult == JOptionPane.YES_OPTION) {
						List<SizeInfoModel> list = ((SizeTableModel) table.getModel()).getData();
						SizeInfoModel selectModel = list.get(row);
						Map<String, String> param = new HashMap<String, String>();
						param.put("sizeId", selectModel.getId().toString());
						Map<String, Object> result = CallUtils.executeGet(window, DELETE_SIZE_URL, param,
								MessageEnum.DELETE_MSG, true);
						if (result != null) {
							repaintContentBox(paramVo);
							PurchaseOrderTab.initSizeCondition();
							SalesOrderTab.initSizeCondition();
							StockTab.initSizeCondition();
						}
					}
				}
			}
		});
	}

	public static void repaintContentBox(RepaintContentParamVo<SizeInfoModel> paramVo) {
		JFrame window = paramVo.getWindow();
		Box mainBox = paramVo.getMainBox();
		SizeTableModel tableModel = (SizeTableModel) paramVo.getTableModel();

		Map<String, Object> colorMap = CallUtils.post(window, SIZE_PAGE_URL, null, MessageEnum.SEARCH_MSG);

		PageInfo<SizeInfoModel> pageInfo = GsonUtils.fromJson(colorMap.get("pageInfo"),
				new TypeToken<PageInfo<SizeInfoModel>>() {
				});

		List<SizeInfoModel> colorList = new ArrayList<SizeInfoModel>();
		if (pageInfo != null) {
			colorList = pageInfo.getList();
		}
		// 数据改变，重新加载数据
		tableModel.setData(colorList);
		tableModel.fireTableDataChanged();

		mainBox.remove(2);
		mainBox.add(ComponentUtils.initBottomBox(pageInfo, SIZE_PAGE_URL, null, window, tableModel, mainBox));
		mainBox.repaint();
	}
}
