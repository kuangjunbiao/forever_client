package com.gaoan.forever.window;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.gaoan.forever.constant.MessageEnum;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.utils.http.CallUtils;

public class LoginWindow {

    private static final Logger logger = LoggerFactory.getLogger(LoginWindow.class);

    /**
     * 展示登录窗口
     */
    public static void show() {
	JFrame jf = new JFrame(MessageInfoConstant.LOGIN_PAGE);
	jf.setSize(280, 180);
	jf.setLocationRelativeTo(null);
	jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	jf.setResizable(false);

	// 绝对布局
	JPanel panel = new JPanel(null);

	// 用户名
	JLabel userNameLabel = new JLabel(MessageInfoConstant.USERNAME);
	userNameLabel.setBounds(30, 10, 50, 50);
	JTextField userNameText = new JTextField();
	userNameText.setBounds(80, 25, 150, 25);
	// 密码
	JLabel passwordLabel = new JLabel(MessageInfoConstant.PASSWORD);
	passwordLabel.setBounds(30, 45, 50, 50);
	JPasswordField passwordText = new JPasswordField();
	passwordText.setBounds(80, 60, 150, 25);

	// 登录按钮
	JButton loginBtn = new JButton(MessageInfoConstant.LOGIN);
	loginBtn.setBounds(80, 100, 60, 30);

	loginBtn.addActionListener(new ActionListener() {
	    @SuppressWarnings("unchecked")
	    public void actionPerformed(ActionEvent e) {
		logger.info("userName = {}, password = {}", userNameText.getText(),
			new String(passwordText.getPassword()));
		logger.info("点击登录按钮");

		Map<String, String> req = new HashMap<String, String>();
		req.put("userName", userNameText.getText());
		req.put("password", new String(passwordText.getPassword()));

		Object obj = CallUtils.post(jf, "http://127.0.0.1:8066/api/usersale/login", JSON.toJSONString(req),
			MessageEnum.LOGIN_MSG);

		if (obj != null) {
		    Map<String, Object> map = (Map<String, Object>) obj;
		    if (map != null && !map.isEmpty()) {
			// String sessionId = (String) map.get("sessionId");
			List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("list");
			if (CollectionUtils.isNotEmpty(list)) {
			    jf.setVisible(false);
			    MenuWindow.show(list);
			}
		    }
		}
	    }
	});

	// 注册按钮
	JButton registerBtn = new JButton(MessageInfoConstant.REGISTER);
	registerBtn.setBounds(150, 100, 60, 30);
	registerBtn.addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		logger.info("点击注册按钮");
		RegisterWindow.show(jf);
	    }
	});
	panel.add(userNameLabel);
	panel.add(userNameText);
	panel.add(passwordLabel);
	panel.add(passwordText);
	panel.add(loginBtn);
	panel.add(registerBtn);

	jf.setContentPane(panel);
	// 显示窗口，前面创建的信息都在内存中,把内存中的窗口显示在屏幕上
	jf.setVisible(true);
    }
}
