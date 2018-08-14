package com.gaoan.forever.window;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gaoan.forever.config.ServerApiConfig;
import com.gaoan.forever.constant.MessageEnum;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.dialog.Dialog;
import com.gaoan.forever.tab.ConfigManageTab;
import com.gaoan.forever.tab.ConsumeTab;
import com.gaoan.forever.tab.PurchaseOrderTab;
import com.gaoan.forever.tab.SalesManageTab;
import com.gaoan.forever.tab.StatisticsTab;
import com.gaoan.forever.tab.StockTab;
import com.gaoan.forever.tab.SystemManageTab;
import com.gaoan.forever.utils.http.CallUtils;

public class MenuWindow {

	private static final Logger logger = LoggerFactory.getLogger(MenuWindow.class);

	private static final String LOGIN_OUT_URL = ServerApiConfig.getApiLoginout();

	/**
	 * 展示菜单窗口
	 * 
	 * @param
	 */
	public static void show(List<Map<String, Object>> list) {
		JFrame jf = new JFrame(MessageInfoConstant.FOREVER);
		jf.setSize(1200, 700);
		// 重写关闭窗口的事件
		jf.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);

		// 创建选项卡面板
		JTabbedPane tabbedPane = new JTabbedPane();
		createTab(jf, tabbedPane, list);

		// 添加选项卡选中状态改变的监听器
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				logger.info("当前选中的选项卡: {}, {}", tabbedPane.getSelectedIndex(), tabbedPane.getName());
			}
		});

		// 设置默认选中的选项卡
		if (CollectionUtils.isNotEmpty(list)) {
			tabbedPane.setSelectedIndex(0);
		}

		jf.setContentPane(tabbedPane);
		jf.setVisible(true);

		jf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				logger.info("触发windowClosing事件");
				closeFrame(jf);
			}

			public void windowClosed(WindowEvent e) {
				logger.info("触发windowClosed事件");
			}
		});
	}

	/**
	 * 根据返回的资源列表生成TAB
	 * 
	 * @param tabbedPane
	 * @param list
	 */
	private static void createTab(JFrame jf, JTabbedPane tabbedPane, List<Map<String, Object>> list) {
		if (tabbedPane == null || CollectionUtils.isEmpty(list)) {
			return;
		}
		String resourcesName;
		for (Map<String, Object> map : list) {
			if (map == null || map.isEmpty()) {
				continue;
			}
			resourcesName = (String) map.get("resources_name");
			if (MessageInfoConstant.MENU_PURCHASE_ORDER_MANAGER.equals(resourcesName)) {
				tabbedPane.addTab(resourcesName, PurchaseOrderTab.show(jf));
			}
			if (MessageInfoConstant.MENU_SALES_ORDER_MANAGER.equals(resourcesName)) {
				tabbedPane.addTab(resourcesName, SalesManageTab.show(jf));
			}
			if (MessageInfoConstant.MENU_STOCK_MANAGER.equals(resourcesName)) {
				tabbedPane.addTab(resourcesName, StockTab.show(jf));
			}
			if (MessageInfoConstant.MENU_CONSUME_MANAGER.equals(resourcesName)) {
				tabbedPane.addTab(resourcesName, ConsumeTab.show(jf));
			}
			if (MessageInfoConstant.MENU_CONFIG_MANAGER.equals(resourcesName)) {
				tabbedPane.addTab(resourcesName, ConfigManageTab.show(jf));
			}
			if (MessageInfoConstant.MENU_STATISTICS_MANAGER.equals(resourcesName)) {
				tabbedPane.addTab(resourcesName, StatisticsTab.show(jf));
			}
			if (MessageInfoConstant.MENU_SYSTEM_MANAGER.equals(resourcesName)) {
				tabbedPane.addTab(resourcesName, SystemManageTab.show(jf));
			}
		}
	}

	// 关闭窗体
	private static void closeFrame(JFrame jf) {
		logger.info("调用窗体关闭功能");
		int result = Dialog.showConfirmDialog(jf, MessageInfoConstant.CONFIRM_EXIT, MessageInfoConstant.EXIT_TITLE);
		if (result == JOptionPane.YES_OPTION) {
			Map<String, Object> map = CallUtils.get(jf, LOGIN_OUT_URL, null, MessageEnum.LOGIN_OUT_MSG);
			if (map != null) {
				jf.dispose();
			}
		}
	}
}
