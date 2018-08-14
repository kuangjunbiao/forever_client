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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.apache.commons.collections.CollectionUtils;
import org.jdesktop.swingx.JXDatePicker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gaoan.forever.config.ServerApiConfig;
import com.gaoan.forever.constant.MessageEnum;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.dialog.Dialog;
import com.gaoan.forever.model.ConsumeInfoModel;
import com.gaoan.forever.model.RepaintContentParamVo;
import com.gaoan.forever.tablemodel.ConsumeTableModel;
import com.gaoan.forever.utils.component.ComponentUtils;
import com.gaoan.forever.utils.date.DateUtil;
import com.gaoan.forever.utils.gson.GsonUtils;
import com.gaoan.forever.utils.http.CallUtils;
import com.gaoan.forever.window.consume.ConsumeInsertWindow;
import com.gaoan.forever.window.consume.ConsumeUpdateWindow;
import com.github.pagehelper.PageInfo;
import com.google.gson.reflect.TypeToken;

public class ConsumeTab {

	private static final Logger logger = LoggerFactory.getLogger(ConsumeTab.class);

	private static final String GET_CONSUME_PAGE_URL = ServerApiConfig.getApiGetConsumePageInfo();
	private static final String DELETE_CONSUME_INFO = ServerApiConfig.getApiDeleteConsumeInfo();

	public static JFrame jf;

	public static JComponent show(JFrame window) {
		jf = window;

		// 查询条件-时间标头
		JLabel dateLabel = new JLabel(MessageInfoConstant.STATISTICS_DATE);
		// 查询条件-开始时间
		JXDatePicker datepick = new JXDatePicker();
		// 设置 date日期
		datepick.setFormats("yyyy-MM-dd");
		datepick.setMaximumSize(new Dimension(120, 25));
		datepick.setPreferredSize(new Dimension(120, 25));

		// 查询条件-初始查询按钮
		JButton searchBtn = new JButton(MessageInfoConstant.SEARCH);
		JButton resetBtn = new JButton(MessageInfoConstant.RESET);
		JButton insertBtn = new JButton(MessageInfoConstant.INSERT);
		JButton updateBtn = new JButton(MessageInfoConstant.UPDATE);
		JButton deleteBtn = new JButton(MessageInfoConstant.DELETE);

		// 初始化查询条件栏
		Box conditionBox = Box.createHorizontalBox();
		conditionBox.add(dateLabel);
		conditionBox.add(datepick);
		conditionBox.add(searchBtn);
		conditionBox.add(resetBtn);
		conditionBox.add(insertBtn);
		conditionBox.add(updateBtn);
		conditionBox.add(deleteBtn);

		List<ConsumeInfoModel> orderList = new ArrayList<ConsumeInfoModel>();

		ConsumeTableModel tableModel = new ConsumeTableModel();
		// 初始化tableModel
		tableModel.setData(orderList);

		JTable table = null;
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
		table.getColumnModel().getColumn(0).setPreferredWidth(20);

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
		Box bottomBox = ComponentUtils.initBottomBox(null, GET_CONSUME_PAGE_URL, null, window, tableModel, mainBox);

		mainBox.add(conditionBox);
		mainBox.add(contentBox);
		mainBox.add(bottomBox);

		RepaintContentParamVo<ConsumeInfoModel> paramVo = new RepaintContentParamVo<ConsumeInfoModel>();
		paramVo.setWindow(window);
		paramVo.setConsumeDatePicker(datepick);
		paramVo.setSearchBtn(searchBtn);
		paramVo.setResetBtn(resetBtn);
		paramVo.setInsertBtn(insertBtn);
		paramVo.setUpdateBtn(updateBtn);
		paramVo.setDeleteBtn(deleteBtn);
		paramVo.setTableModel(tableModel);
		paramVo.setTable(table);
		paramVo.setMainBox(mainBox);

		// 添加事件监听
		addListener(paramVo);

		return mainBox;

	}

	/**
	 * 添加监听
	 * 
	 * @param paramVo
	 */
	private static void addListener(RepaintContentParamVo<ConsumeInfoModel> paramVo) {
		JFrame window = paramVo.getWindow();
		JXDatePicker consumeDataPicker = paramVo.getConsumeDatePicker();
		JButton searchBtn = paramVo.getSearchBtn();
		JButton resetBtn = paramVo.getResetBtn();
		JButton insertBtn = paramVo.getInsertBtn();
		JButton updateBtn = paramVo.getUpdateBtn();
		JButton deleteBtn = paramVo.getDeleteBtn();
		JTable table = paramVo.getTable();

		// 查询按钮添加监听
		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaintContentBox(paramVo);
			}
		});

		// 重置按钮添加监听
		resetBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				consumeDataPicker.setDate(null);
			}
		});

		// 新增已有按钮添加监听
		insertBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ConsumeInsertWindow.show(window, paramVo);
			}
		});

		// 修改按钮添加监听
		updateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row < 0) {
					Dialog.showWarningDialog(window, MessageInfoConstant.TITLE_PROMPT,
							MessageInfoConstant.PLEASE_CHOOSE_ONE);
				} else {
					List<ConsumeInfoModel> list = ((ConsumeTableModel) table.getModel()).getData();
					ConsumeInfoModel selectModel = null;
					if (CollectionUtils.isNotEmpty(list)) {
						selectModel = list.get(row);
					}
					ConsumeUpdateWindow.show(window, selectModel.getId(), paramVo);
				}
			}
		});

		// 删除按钮添加监听
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
						List<ConsumeInfoModel> list = ((ConsumeTableModel) table.getModel()).getData();
						ConsumeInfoModel selectModel = list.get(row);
						Map<String, String> param = new HashMap<String, String>();
						param.put("id", selectModel.getId().toString());
						Map<String, Object> result = CallUtils.executeGet(window, DELETE_CONSUME_INFO, param,
								MessageEnum.DELETE_MSG, true);
						if (result != null) {
							repaintContentBox(paramVo);
						}
					}
				}
			}
		});
	}

	public static void repaintContentBox(RepaintContentParamVo<ConsumeInfoModel> paramVo) {
		JFrame window = paramVo.getWindow();
		JXDatePicker datePicker = paramVo.getConsumeDatePicker();
		ConsumeTableModel tableModel = (ConsumeTableModel) paramVo.getTableModel();
		Box mainBox = paramVo.getMainBox();

		String date = DateUtil.dateFormatY1(datePicker.getDate());

		Map<String, String> param = new HashMap<String, String>();
		param.put("date", date);

		Map<String, Object> consumeMap = CallUtils.post(window, GET_CONSUME_PAGE_URL, GsonUtils.toJson(param),
				MessageEnum.SEARCH_MSG);

		PageInfo<ConsumeInfoModel> pageInfo = GsonUtils.fromJson(consumeMap.get("pageInfo"),
				new TypeToken<PageInfo<ConsumeInfoModel>>() {
				});

		List<ConsumeInfoModel> consumeList = new ArrayList<ConsumeInfoModel>();
		if (pageInfo != null) {
			consumeList = pageInfo.getList();
		}
		// 数据改变，重新加载数据
		tableModel.setData(consumeList);
		tableModel.fireTableDataChanged();

		mainBox.remove(2);
		mainBox.add(ComponentUtils.initBottomBox(pageInfo, GET_CONSUME_PAGE_URL, param, window, tableModel, mainBox));
		mainBox.repaint();
	}

}
