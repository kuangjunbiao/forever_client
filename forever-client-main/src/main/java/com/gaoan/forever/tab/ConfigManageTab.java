package com.gaoan.forever.tab;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gaoan.forever.constant.MessageInfoConstant;

public class ConfigManageTab {

	private static final Logger logger = LoggerFactory.getLogger(ConfigManageTab.class);

	/**
	 * 展示配置管理tab
	 * 
	 * @param
	 */
	public static JComponent show(JFrame window) {
		// 创建选项卡面板
		JTabbedPane tabbedPane = new JTabbedPane();
		createTab(tabbedPane, window);

		// 添加选项卡选中状态改变的监听器
		tabbedPane.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				logger.info("当前选中的选项卡: {}", tabbedPane.getSelectedIndex());
			}
		});

		// 设置默认选中的选项卡
		tabbedPane.setSelectedIndex(0);

		return tabbedPane;
	}

	/**
	 * 根据返回的资源列表生成TAB
	 * 
	 * @param tabbedPane
	 * @param list
	 */
	private static void createTab(JTabbedPane tabbedPane, JFrame jf) {
		if (tabbedPane == null) {
			return;
		}

		tabbedPane.addTab(MessageInfoConstant.MENU_COLOR_MANAGER, ColorTab.show(jf));
		tabbedPane.addTab(MessageInfoConstant.MENU_SIZE_MANAGER, SizeTab.show(jf));
	}

}
