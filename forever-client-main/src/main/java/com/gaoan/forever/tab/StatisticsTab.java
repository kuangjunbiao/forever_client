package com.gaoan.forever.tab;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JButton;
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
import com.gaoan.forever.common.InitProperties;
import com.gaoan.forever.config.ServerApiConfig;
import com.gaoan.forever.constant.ForeverConstant;
import com.gaoan.forever.constant.MessageEnum;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.dialog.Dialog;
import com.gaoan.forever.model.KeyValBoxVo;
import com.gaoan.forever.model.RepaintContentParamVo;
import com.gaoan.forever.model.StatisticsInfoModel;
import com.gaoan.forever.model.UserInfoModel;
import com.gaoan.forever.tablemodel.StatisticsInfoTableModel;
import com.gaoan.forever.utils.date.DateUtil;
import com.gaoan.forever.utils.gson.GsonUtils;
import com.gaoan.forever.utils.http.CallUtils;
import com.google.gson.reflect.TypeToken;

public class StatisticsTab {

	private static final Logger logger = LoggerFactory.getLogger(StatisticsTab.class);

	private static final String STATISTICS_INFO_URL = ServerApiConfig.getApiGetStatisticsInfo();
	private static final String EXPORT_SALES_DETAIL_URL = ServerApiConfig.getApiExportSalesInfo();
	private static final String EXPORT_CONSUME_DETAIL_URL = ServerApiConfig.getApiExportConsumeInfo();

	public static JFrame jf;
	public static KeyValComboBox<KeyValBoxVo> userComboBox;

	@SuppressWarnings("unchecked")
	public static JComponent show(JFrame window) {
		logger.info("StatisticsTab show.");
		jf = window;

		// 查询条件-初始化统计类型
		JLabel statisticsTypeLabel = new JLabel(MessageInfoConstant.STATISTICS_TYPE);
		Vector<KeyValBoxVo> typeVector = new Vector<KeyValBoxVo>();
		KeyValBoxVo dateTypeBoxVo = new KeyValBoxVo(String.valueOf(ForeverConstant.STATISTICS_TYPE_DAY),
				MessageInfoConstant.STATISTICS_TYPE_DAY);
		KeyValBoxVo monthTypeBoxVo = new KeyValBoxVo(String.valueOf(ForeverConstant.STATISTICS_TYPE_MONTH),
				MessageInfoConstant.STATISTICS_TYPE_MONTH);
		KeyValBoxVo yearTypeBoxVo = new KeyValBoxVo(String.valueOf(ForeverConstant.STATISTICS_TYPE_YEAR),
				MessageInfoConstant.STATISTICS_TYPE_YEAR);
		typeVector.add(dateTypeBoxVo);
		typeVector.add(monthTypeBoxVo);
		typeVector.add(yearTypeBoxVo);

		KeyValComboBox<KeyValBoxVo> statisticsTypeComboBox = new KeyValComboBox<KeyValBoxVo>(typeVector);
		statisticsTypeComboBox.setMaximumSize(new Dimension(120, 25));
		statisticsTypeComboBox.setPreferredSize(new Dimension(120, 25));

		JLabel statisticsDateLabel = new JLabel(MessageInfoConstant.STATISTICS_DATE);
		// 查询条件-时间
		JXDatePicker statisticsDate = new JXDatePicker();
		// 设置 date日期
		statisticsDate.setFormats("yyyy-MM-dd");
		statisticsDate.setMaximumSize(new Dimension(120, 25));
		statisticsDate.setPreferredSize(new Dimension(120, 25));

		// 封装用户Vector
		Vector<KeyValBoxVo> userVector = new Vector<KeyValBoxVo>();
		userVector.add(new KeyValBoxVo(" ", " "));

		List<UserInfoModel> userList = (List<UserInfoModel>) InitData.map.get("userList");
		if (CollectionUtils.isNotEmpty(userList)) {
			userList.forEach(user -> {
				KeyValBoxVo userKeyval = new KeyValBoxVo(user.getId().toString(), user.getRealName());
				userVector.add(userKeyval);
			});
		}

		// 用户BOX
		userComboBox = new KeyValComboBox<KeyValBoxVo>(userVector);
		JLabel userLabel = new JLabel(MessageInfoConstant.CREATEOR);
		userComboBox.setMaximumSize(new Dimension(120, 30));
		userComboBox.setPreferredSize(new Dimension(120, 30));

		// 查询条件-初始查询按钮
		JButton searchBtn = new JButton(MessageInfoConstant.SEARCH);
		JButton exportSalesBtn = new JButton(MessageInfoConstant.EXPORT_SALES_DETAIL);
		JButton exportConsumeBtn = new JButton(MessageInfoConstant.EXPORT_CONSUME_DETAIL);

		// 初始化查询条件栏
		Box conditionBox = Box.createHorizontalBox();
		conditionBox.add(statisticsTypeLabel);
		conditionBox.add(statisticsTypeComboBox);
		conditionBox.add(statisticsDateLabel);
		conditionBox.add(statisticsDate);
		conditionBox.add(userLabel);
		conditionBox.add(userComboBox);
		conditionBox.add(searchBtn);
		conditionBox.add(exportSalesBtn);
		conditionBox.add(exportConsumeBtn);

		StatisticsInfoTableModel tableModel = new StatisticsInfoTableModel();
		// 初始化tableModel
		JTable table = new JTable(tableModel);

		// 设置表格内容颜色
		table.setForeground(Color.BLACK); // 字体颜色
		table.setFont(new Font(null, Font.PLAIN, 14)); // 字体样式
		table.setSelectionForeground(Color.DARK_GRAY); // 选中后字体颜色
		table.setSelectionBackground(Color.LIGHT_GRAY); // 选中后字体背景
		table.setGridColor(Color.GRAY); // 网格颜色

		// 设置表头
		table.getTableHeader().setFont(new Font(null, Font.BOLD, 14)); // 设置表头名称字体样式
		table.getTableHeader().setForeground(Color.RED); // 设置表头名称字体颜色
		table.getTableHeader().setResizingAllowed(true); // 设置不允许手动改变列宽
		table.getTableHeader().setReorderingAllowed(false); // 设置不允许拖动重新排序各列

		// 设置只能选择一行
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// 设置行高
		table.setRowHeight(30);

		// 第一列列宽设置为50
		table.getColumnModel().getColumn(0).setPreferredWidth(130);
		table.getColumnModel().getColumn(4).setPreferredWidth(10);

		// 设置滚动面板视口大小（超过该大小的行数据，需要拖动滚动条才能看到）
		table.setPreferredScrollableViewportSize(new Dimension(400, 400));

		// 把 表格放到 滚动面板 中（表头将自动添加到滚动面板顶部）
		JScrollPane scrollPane = new JScrollPane(table);
		Box contentBox = Box.createHorizontalBox();
		contentBox.add(scrollPane);

		Box mainBox = Box.createVerticalBox();
		mainBox.add(conditionBox);
		mainBox.add(contentBox);

		RepaintContentParamVo<StatisticsInfoModel> paramVo = new RepaintContentParamVo<StatisticsInfoModel>();
		paramVo.setWindow(window);
		paramVo.setStatisticsComboBox(statisticsTypeComboBox);
		paramVo.setStatisticsDatePicker(statisticsDate);
		paramVo.setUserComboBox(userComboBox);
		paramVo.setSearchBtn(searchBtn);
		paramVo.setExportSalesBtn(exportSalesBtn);
		paramVo.setExportConsumeBtn(exportConsumeBtn);
		paramVo.setTableModel(tableModel);
		paramVo.setTable(table);
		paramVo.setMainBox(mainBox);

		// 添加事件监听
		addListener(paramVo);

		return mainBox;

	}

	/**
	 * 添加监听
	 */
	private static void addListener(RepaintContentParamVo<StatisticsInfoModel> paramVo) {
		JFrame window = paramVo.getWindow();
		KeyValComboBox<KeyValBoxVo> statisticsComboBox = paramVo.getStatisticsComboBox();
		JXDatePicker statisticsDate = paramVo.getStatisticsDatePicker();
		KeyValComboBox<KeyValBoxVo> userComboBox = paramVo.getUserComboBox();
		JButton searchBtn = paramVo.getSearchBtn();
		JButton exportSalesBtn = paramVo.getExportSalesBtn();
		JButton exportConsumeBtn = paramVo.getExportConsumeBtn();

		// 类型选择下拉框
		statisticsComboBox.addItemListener(e -> {
			int type = Integer.parseInt(statisticsComboBox.getSelectedKey());
			// 设置 date日期
			if (type == ForeverConstant.STATISTICS_TYPE_DAY) {
				statisticsDate.setFormats("yyyy-MM-dd");
			} else if (type == ForeverConstant.STATISTICS_TYPE_MONTH) {
				statisticsDate.setFormats("yyyy-MM");
			} else if (type == ForeverConstant.STATISTICS_TYPE_YEAR) {
				statisticsDate.setFormats("yyyy");
			}
		});

		// 查询按钮
		searchBtn.addActionListener(e -> {
			repaintContentBox(paramVo);
		});

		// 导出销售明细按钮
		exportSalesBtn.addActionListener(e -> {
			// 校验必填项
			if (StringUtils.isEmpty(statisticsComboBox.getSelectedKey()) || statisticsDate.getDate() == null) {
				Dialog.showWarningDialog(window, MessageInfoConstant.TITLE_PROMPT,
						MessageInfoConstant.PLEASE_INPUT_DATE);
				return;
			}

			int type = Integer.parseInt(statisticsComboBox.getSelectedKey());
			Date date = statisticsDate.getDate();
			String dateStr = "";
			if (type == ForeverConstant.STATISTICS_TYPE_DAY) {
				dateStr = DateUtil.dateFormatY1(date);
			} else if (type == ForeverConstant.STATISTICS_TYPE_MONTH) {
				dateStr = DateUtil.dateFormat(date, "yyyy-MM");
			} else if (type == ForeverConstant.STATISTICS_TYPE_YEAR) {
				dateStr = DateUtil.dateFormat(date, "yyyy");
			}
			Map<String, String> param = new HashMap<String, String>();
			param.put("type", statisticsComboBox.getSelectedKey());
			param.put("date", dateStr);
			param.put("userId", userComboBox.getSelectedKey());

			StringBuffer filePath = new StringBuffer(InitProperties.readProperties("exportPath"));

			// 文件完整路径
			String fileCompletePath = MessageFormat.format(filePath.append("/{0}_{1}.csv").toString(), "销售明细",
					DateUtil.format(new Date(), "yyyy年MM月dd日HH时mm分ss秒"));

			CallUtils.getForDownload(window,
					EXPORT_SALES_DETAIL_URL, param, MessageEnum.EXPORT_MSG, MessageFormat
							.format(MessageInfoConstant.EXPORT_SUCCESS, InitProperties.readProperties("exportPath")),
					fileCompletePath);
		});

		// 导出支出明细按钮
		exportConsumeBtn.addActionListener(e -> {
			// 校验必填项
			if (StringUtils.isEmpty(statisticsComboBox.getSelectedKey()) || statisticsDate.getDate() == null) {
				Dialog.showWarningDialog(window, MessageInfoConstant.TITLE_PROMPT,
						MessageInfoConstant.PLEASE_INPUT_DATE);
				return;
			}

			int type = Integer.parseInt(statisticsComboBox.getSelectedKey());
			Date date = statisticsDate.getDate();
			String dateStr = "";
			if (type == ForeverConstant.STATISTICS_TYPE_DAY) {
				dateStr = DateUtil.dateFormatY1(date);
			} else if (type == ForeverConstant.STATISTICS_TYPE_MONTH) {
				dateStr = DateUtil.dateFormat(date, "yyyy-MM");
			} else if (type == ForeverConstant.STATISTICS_TYPE_YEAR) {
				dateStr = DateUtil.dateFormat(date, "yyyy");
			}
			Map<String, String> param = new HashMap<String, String>();
			param.put("type", statisticsComboBox.getSelectedKey());
			param.put("date", dateStr);

			StringBuffer filePath = new StringBuffer(InitProperties.readProperties("exportPath"));

			// 文件完整路径
			String fileCompletePath = MessageFormat.format(filePath.append("/{0}_{1}.csv").toString(), "支出明细",
					DateUtil.format(new Date(), "yyyy年MM月dd日HH时mm分ss秒"));

			CallUtils.getForDownload(window,
					EXPORT_CONSUME_DETAIL_URL, param, MessageEnum.EXPORT_MSG, MessageFormat
							.format(MessageInfoConstant.EXPORT_SUCCESS, InitProperties.readProperties("exportPath")),
					fileCompletePath);
		});
	}

	/**
	 * 重绘内容展示区域
	 */
	public static void repaintContentBox(RepaintContentParamVo<StatisticsInfoModel> paramVo) {
		JFrame window = paramVo.getWindow();
		KeyValComboBox<KeyValBoxVo> statisticsComboBox = paramVo.getStatisticsComboBox();
		KeyValComboBox<KeyValBoxVo> userComboBox = paramVo.getUserComboBox();
		JXDatePicker statisticsDatepick = paramVo.getStatisticsDatePicker();
		StatisticsInfoTableModel tableModel = (StatisticsInfoTableModel) paramVo.getTableModel();

		if (StringUtils.isEmpty(statisticsComboBox.getSelectedKey()) || statisticsDatepick.getDate() == null) {
			Dialog.showWarningDialog(window, MessageInfoConstant.TITLE_PROMPT, MessageInfoConstant.PLEASE_INPUT_DATE);
			return;
		}

		int type = Integer.parseInt(statisticsComboBox.getSelectedKey());
		Date date = statisticsDatepick.getDate();
		String dateStr = "";
		if (type == ForeverConstant.STATISTICS_TYPE_DAY) {
			dateStr = DateUtil.dateFormatY1(date);
		} else if (type == ForeverConstant.STATISTICS_TYPE_MONTH) {
			dateStr = DateUtil.dateFormat(date, "yyyy-MM");
		} else if (type == ForeverConstant.STATISTICS_TYPE_YEAR) {
			dateStr = DateUtil.dateFormat(date, "yyyy");
		}

		Map<String, String> param = new HashMap<String, String>();
		param.put("type", statisticsComboBox.getSelectedKey());
		param.put("date", dateStr);
		param.put("userId", userComboBox.getSelectedKey());

		Map<String, Object> statisticsInfoMap = CallUtils.get(window, STATISTICS_INFO_URL, param,
				MessageEnum.SEARCH_MSG);

		StatisticsInfoModel statisticsInfo = GsonUtils.fromJson(statisticsInfoMap.get("info"),
				new TypeToken<StatisticsInfoModel>() {
				});

		List<StatisticsInfoModel> list = new ArrayList<StatisticsInfoModel>();
		if (statisticsInfo != null) {
			list.add(statisticsInfo);
		}

		// 数据改变，重新加载数据
		tableModel.setData(list);
		tableModel.fireTableDataChanged();
	}

	public static void initUserCondition() {
		InitCondition.initUserCondition(jf, userComboBox);
	}

}
