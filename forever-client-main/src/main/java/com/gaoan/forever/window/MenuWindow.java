package com.gaoan.forever.window;

import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.tab.PurchaseOrderTab;

public class MenuWindow {

    private static final Logger logger = LoggerFactory.getLogger(MenuWindow.class);

    /**
     * 展示菜单窗口
     * 
     * @param
     */
    public static void show(List<Map<String, Object>> list) {
	JFrame jf = new JFrame(MessageInfoConstant.MENU_PAGE);
	jf.setSize(800, 600);
	jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	jf.setLocationRelativeTo(null);
	jf.setResizable(false);

	// 创建选项卡面板
	JTabbedPane tabbedPane = new JTabbedPane();
	createTab(jf, tabbedPane, list);

	// 添加选项卡选中状态改变的监听器
	tabbedPane.addChangeListener(new ChangeListener() {
	    @Override
	    public void stateChanged(ChangeEvent e) {
		logger.info("当前选中的选项卡: {}", tabbedPane.getSelectedIndex());
	    }
	});

	// 设置默认选中的选项卡
	tabbedPane.setSelectedIndex(0);

	jf.setContentPane(tabbedPane);
	jf.setVisible(true);
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
	    if ("进货管理".equals(resourcesName)) {
		tabbedPane.addTab(resourcesName, PurchaseOrderTab.show(jf));
	    }
	}
    }

}
