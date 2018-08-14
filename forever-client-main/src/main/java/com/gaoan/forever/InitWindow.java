package com.gaoan.forever;

import javax.swing.UIManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gaoan.forever.window.user.LoginWindow;

public class InitWindow {

	private static final Logger logger = LoggerFactory.getLogger(InitWindow.class);

	public static void main(String[] args) {
		logger.info("展示登录窗口.");
		try {
			// UIManager.setLookAndFeel(com.sun.java.swing.plaf.windows.WindowsLookAndFeel.class.getName());
			// 设置swing自带皮肤
			UIManager.setLookAndFeel(javax.swing.plaf.nimbus.NimbusLookAndFeel.class.getName());
			// 展示登陆窗口
			LoginWindow.show();
		} catch (Exception e) {
			logger.error("initWindow error.", e);
		}
	}

}
