package com.gaoan.forever.window.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.utils.http.CallUtils;
import com.gaoan.forever.config.ServerApiConfig;
import com.gaoan.forever.constant.MessageEnum;

public class ForgetPasswordWindow {

	private static final Logger logger = LoggerFactory.getLogger(ForgetPasswordWindow.class);

	private static final String FORGET_PASSWORD_URL = ServerApiConfig.getApiForgetPass();

	/**
	 * 展示注册窗口
	 * 
	 * @param relativeWindow
	 */
	public static void show(JFrame relativeWindow) {
		// 创建一个新窗口
		JFrame forgetPasswordJFrame = new JFrame(MessageInfoConstant.FORGET_PASSWORD_PAGE);

		forgetPasswordJFrame.setSize(320, 260);

		// 把新窗口的位置设置到 relativeWindow 窗口的中心
		forgetPasswordJFrame.setLocationRelativeTo(relativeWindow);

		// 点击窗口关闭按钮, 执行销毁窗口操作（如果设置为 EXIT_ON_CLOSE, 则点击新窗口关闭按钮后, 整个进程将结束）
		forgetPasswordJFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// 窗口设置为不可改变大小
		forgetPasswordJFrame.setResizable(false);

		JPanel panel = new JPanel(null);

		JLabel userNameLabel = new JLabel(MessageInfoConstant.USERNAME);
		userNameLabel.setBounds(30, 10, 50, 50);
		JTextField userNameText = new JTextField();
		userNameText.setBounds(100, 25, 180, 25);
		JLabel realNameLabel = new JLabel(MessageInfoConstant.REAL_NAME);
		realNameLabel.setBounds(30, 45, 80, 50);
		JTextField realNameText = new JTextField();
		realNameText.setBounds(100, 60, 180, 25);
		JLabel newPasswordLabel = new JLabel(MessageInfoConstant.NEW_PASSWORD);
		newPasswordLabel.setBounds(30, 80, 50, 50);
		JPasswordField newPasswordText = new JPasswordField();
		newPasswordText.setBounds(100, 95, 180, 25);
		JLabel confirmPasswordLabel = new JLabel(MessageInfoConstant.CONFIRM_NEW_PASSWORD);
		confirmPasswordLabel.setBounds(30, 115, 80, 50);
		JPasswordField confirmPasswordText = new JPasswordField();
		confirmPasswordText.setBounds(100, 130, 180, 25);

		JButton confirmBtn = new JButton(MessageInfoConstant.COMFIRM);
		confirmBtn.setBounds(90, 170, 60, 30);

		confirmBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("忘记密码页面,点击确认按钮.");

				logger.info("userName = {}, realName = {}, newPassword = {}, confirmNewPassword = {}",
						userNameText.getText(), realNameText.getText(), new String(newPasswordText.getPassword()),
						new String(confirmPasswordText.getPassword()));

				// 请求参数
				Map<String, String> req = new HashMap<String, String>();
				req.put("userName", userNameText.getText());
				req.put("realName", realNameText.getText());
				req.put("newPassword", new String(newPasswordText.getPassword()));
				req.put("confirmNewPassword", new String(confirmPasswordText.getPassword()));

				Map<String, Object> map = CallUtils.executePost(forgetPasswordJFrame, FORGET_PASSWORD_URL, null,
						JSON.toJSONString(req), MessageEnum.SET_NEW_PASSWORD_MSG, true);
				if (map != null) {
					forgetPasswordJFrame.dispose();
				}
			}
		});

		JButton cancelBtn = new JButton(MessageInfoConstant.CANCLE);
		cancelBtn.setBounds(180, 170, 60, 30);

		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("注册页面, 点击取消按钮.");
				forgetPasswordJFrame.dispose();
			}
		});

		panel.add(userNameLabel);
		panel.add(userNameText);
		panel.add(realNameLabel);
		panel.add(realNameText);
		panel.add(newPasswordLabel);
		panel.add(newPasswordText);
		panel.add(confirmPasswordLabel);
		panel.add(confirmPasswordText);
		panel.add(confirmBtn);
		panel.add(cancelBtn);

		forgetPasswordJFrame.setContentPane(panel);
		forgetPasswordJFrame.setVisible(true);
	}
}
