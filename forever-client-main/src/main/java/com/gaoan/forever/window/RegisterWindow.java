package com.gaoan.forever.window;

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
import com.gaoan.forever.constant.MessageEnum;

public class RegisterWindow {

    private static final Logger logger = LoggerFactory.getLogger(RegisterWindow.class);

    /**
     * 展示注册窗口
     * 
     * @param relativeWindow
     */
    public static void show(JFrame relativeWindow) {
	// 创建一个新窗口
	JFrame registerJFrame = new JFrame(MessageInfoConstant.REGISTER_PAGE);

	registerJFrame.setSize(320, 220);

	// 把新窗口的位置设置到 relativeWindow 窗口的中心
	registerJFrame.setLocationRelativeTo(relativeWindow);

	// 点击窗口关闭按钮, 执行销毁窗口操作（如果设置为 EXIT_ON_CLOSE, 则点击新窗口关闭按钮后, 整个进程将结束）
	registerJFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

	// 窗口设置为不可改变大小
	registerJFrame.setResizable(false);

	JPanel panel = new JPanel(null);

	JLabel userNameLabel = new JLabel(MessageInfoConstant.USERNAME);
	userNameLabel.setBounds(30, 10, 50, 50);
	JTextField userNameText = new JTextField();
	userNameText.setBounds(100, 25, 180, 25);
	JLabel passwordLabel = new JLabel(MessageInfoConstant.PASSWORD);
	passwordLabel.setBounds(30, 45, 50, 50);
	JPasswordField passwordText = new JPasswordField();
	passwordText.setBounds(100, 60, 180, 25);
	JLabel realNameLabel = new JLabel(MessageInfoConstant.REAL_NAME);
	realNameLabel.setBounds(30, 80, 80, 50);
	JTextField realNameText = new JTextField();
	realNameText.setBounds(100, 95, 180, 25);

	JButton confirmBtn = new JButton(MessageInfoConstant.COMFIRM);
	confirmBtn.setBounds(90, 130, 60, 30);

	confirmBtn.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		logger.info("注册页面,点击确认按钮.");

		logger.info("userName = {}, password = {}, realName = {}", userNameText.getText(),
			new String(passwordText.getPassword()), realNameText.getText());

		// 请求参数
		Map<String, String> req = new HashMap<String, String>();
		req.put("userName", userNameText.getText());
		req.put("password", new String(passwordText.getPassword()));
		req.put("realName", realNameText.getText());

		CallUtils.executePost(registerJFrame, "http://127.0.0.1:8066/api/usersale/register",
			JSON.toJSONString(req), MessageEnum.REGISTER_MSG, true);
	    }
	});

	JButton cancelBtn = new JButton(MessageInfoConstant.CANCLE);
	cancelBtn.setBounds(180, 130, 60, 30);

	cancelBtn.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		logger.info("注册页面, 点击取消按钮.");
		registerJFrame.dispose();
	    }
	});

	panel.add(userNameLabel);
	panel.add(userNameText);
	panel.add(passwordLabel);
	panel.add(passwordText);
	panel.add(realNameLabel);
	panel.add(realNameText);
	panel.add(confirmBtn);
	panel.add(cancelBtn);

	registerJFrame.setContentPane(panel);
	registerJFrame.setVisible(true);
    }
}
