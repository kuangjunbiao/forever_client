package com.gaoan.forever.window.user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.gaoan.forever.combobox.KeyValComboBox;
import com.gaoan.forever.config.ServerApiConfig;
import com.gaoan.forever.constant.MessageEnum;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.model.KeyValBoxVo;
import com.gaoan.forever.model.RepaintContentParamVo;
import com.gaoan.forever.model.RoleInfoModel;
import com.gaoan.forever.model.UserInfoModel;
import com.gaoan.forever.tab.SalesOrderTab;
import com.gaoan.forever.tab.StatisticsTab;
import com.gaoan.forever.tab.UserTab;
import com.gaoan.forever.utils.gson.GsonUtils;
import com.gaoan.forever.utils.http.CallUtils;
import com.google.gson.reflect.TypeToken;

public class UserInsertWindow {

	private static final Logger logger = LoggerFactory.getLogger(UserInsertWindow.class);

	private static final String ROLE_LIST_URL = ServerApiConfig.getApiGetRoleList();
	private static final String USER_INSERT_URL = ServerApiConfig.getApiInsertUser();

	/**
	 * 展示窗口
	 * 
	 * @param relativeWindow
	 */
	public static void show(JFrame relativeWindow, RepaintContentParamVo<UserInfoModel> parentParamVo) {
		// 创建一个新窗口
		JFrame updateJFrame = new JFrame(MessageInfoConstant.INSERT);

		updateJFrame.setSize(400, 240);

		// 把新窗口的位置设置到 relativeWindow 窗口的中心
		updateJFrame.setLocationRelativeTo(relativeWindow);

		// 点击窗口关闭按钮, 执行销毁窗口操作（如果设置为 EXIT_ON_CLOSE, 则点击新窗口关闭按钮后, 整个进程将结束）
		updateJFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// 窗口设置为不可改变大小
		updateJFrame.setResizable(false);

		JPanel panel = new JPanel(null);

		// 根据编号查询当前角色信息
		// 用户名BOX
		JLabel userNameLabel = new JLabel(MessageInfoConstant.USERNAME);
		JTextField userNameText = new JTextField();
		userNameLabel.setBounds(80, 20, 80, 35);
		userNameText.setBounds(160, 25, 140, 25);

		// 真实姓名BOX
		JLabel realNameLabel = new JLabel(MessageInfoConstant.REAL_NAME);
		JTextField realNameText = new JTextField();
		realNameLabel.setBounds(80, 60, 80, 35);
		realNameText.setBounds(160, 65, 140, 25);

		// 查询角色列表
		Map<String, Object> roleMap = CallUtils.get(relativeWindow, ROLE_LIST_URL, null, MessageEnum.SEARCH_MSG);

		List<RoleInfoModel> list = new ArrayList<RoleInfoModel>();
		if (roleMap != null && !roleMap.isEmpty()) {
			list = GsonUtils.fromJson(roleMap.get("list"), new TypeToken<List<RoleInfoModel>>() {
			});
		}

		// 封装角色Vector
		Vector<KeyValBoxVo> keyValVector = new Vector<KeyValBoxVo>();
		KeyValBoxVo keyValVo;
		for (RoleInfoModel temp : list) {
			if (temp == null) {
				continue;
			}
			keyValVo = new KeyValBoxVo(String.valueOf(temp.getId()), temp.getRoleName());
			keyValVector.add(keyValVo);
		}

		JLabel roleNameLabel = new JLabel(MessageInfoConstant.ROLE_NAME);
		KeyValComboBox<KeyValBoxVo> roleNameComboBox = new KeyValComboBox<KeyValBoxVo>(keyValVector);
		roleNameComboBox.setSelectedIndex(1);
		roleNameLabel.setBounds(80, 100, 80, 35);
		roleNameComboBox.setBounds(160, 105, 140, 25);

		// 按钮BOX
		JButton confirmBtn = new JButton(MessageInfoConstant.COMFIRM);
		JButton cancelBtn = new JButton(MessageInfoConstant.CANCLE);
		confirmBtn.setBounds(110, 150, 70, 30);
		cancelBtn.setBounds(200, 150, 70, 30);

		panel.add(userNameLabel);
		panel.add(userNameText);
		panel.add(realNameLabel);
		panel.add(realNameText);
		panel.add(roleNameLabel);
		panel.add(roleNameComboBox);
		panel.add(confirmBtn);
		panel.add(cancelBtn);

		RepaintContentParamVo<UserInfoModel> paramVo = new RepaintContentParamVo<UserInfoModel>();
		paramVo.setWindow(updateJFrame);
		paramVo.setUserNameText(userNameText);
		paramVo.setRealNameText(realNameText);
		paramVo.setRoleNameComboBox(roleNameComboBox);
		paramVo.setConfirmButton(confirmBtn);
		paramVo.setCancelButton(cancelBtn);

		addListener(paramVo, parentParamVo);

		updateJFrame.setContentPane(panel);
		updateJFrame.setVisible(true);

	}

	public static void addListener(RepaintContentParamVo<UserInfoModel> paramVo,
			RepaintContentParamVo<UserInfoModel> parentParamVo) {
		JFrame updateJFrame = paramVo.getWindow();
		JTextField userNameText = paramVo.getUserNameText();
		JTextField realNameText = paramVo.getRealNameText();
		KeyValComboBox<KeyValBoxVo> roleNameComboBox = paramVo.getRoleNameComboBox();
		JButton confirmBtn = paramVo.getConfirmButton();
		JButton cancelBtn = paramVo.getCancelButton();

		confirmBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("新增已有页面,点击确认按钮.");

				String userName = userNameText.getText();
				String realName = realNameText.getText();
				String roleId = roleNameComboBox.getSelectedKey();

				logger.info("userName = {}, realName = {},roleId = {}", userName, realName, roleId);

				// 请求参数
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("userName", userName);
				param.put("realName", realName);
				param.put("roleId", Long.valueOf(roleId));

				Map<String, Object> result = CallUtils.executePost(updateJFrame, USER_INSERT_URL, null,
						JSON.toJSONString(param), MessageEnum.INSERT_MSG, true);
				if (result != null) {
					updateJFrame.dispose();
					UserTab.repaintContentBox(parentParamVo);
					SalesOrderTab.initUserCondition();
					StatisticsTab.initUserCondition();
				}
			}
		});

		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("新增用户界面, 点击取消按钮.");
				updateJFrame.dispose();
			}
		});

	}

}
