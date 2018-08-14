package com.gaoan.forever.window.role;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gaoan.forever.config.ServerApiConfig;
import com.gaoan.forever.constant.MessageEnum;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.model.RepaintContentParamVo;
import com.gaoan.forever.model.ResourceInfoModel;
import com.gaoan.forever.model.RoleInfoModel;
import com.gaoan.forever.tab.RoleTab;
import com.gaoan.forever.utils.gson.GsonUtils;
import com.gaoan.forever.utils.http.CallUtils;
import com.gaoan.forever.window.purchase.PurchaseInsertUnHaveWindow;
import com.google.gson.reflect.TypeToken;

public class RoleUpdateWindow {

	private static final Logger logger = LoggerFactory.getLogger(PurchaseInsertUnHaveWindow.class);

	private static final String ROLE_DETAIL_URL = ServerApiConfig.getApiGetRoleDetail();

	private static final String ROLE_UPDATE_URL = ServerApiConfig.getApiUpdateRole();

	private static final String RESOURCE_LIST_URL = ServerApiConfig.getApiGetResourceList();

	/**
	 * 展示窗口
	 * 
	 * @param relativeWindow
	 */
	public static void show(JFrame relativeWindow, Long roleId, RepaintContentParamVo<RoleInfoModel> parentParamVo) {
		// 创建一个新窗口
		JFrame updateJFrame = new JFrame(MessageInfoConstant.UPDATE);

		updateJFrame.setSize(400, 300);

		// 把新窗口的位置设置到 relativeWindow 窗口的中心
		updateJFrame.setLocationRelativeTo(relativeWindow);

		// 点击窗口关闭按钮, 执行销毁窗口操作（如果设置为 EXIT_ON_CLOSE, 则点击新窗口关闭按钮后, 整个进程将结束）
		updateJFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// 窗口设置为不可改变大小
		updateJFrame.setResizable(false);

		JPanel panel = new JPanel(null);

		// 根据编号查询当前资源信息
		Map<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("roleId", roleId.toString());
		Map<String, Object> dataMap = CallUtils.get(relativeWindow, ROLE_DETAIL_URL, queryParam,
				MessageEnum.SEARCH_MSG);
		RoleInfoModel model = GsonUtils.fromJson(dataMap.get("info"), RoleInfoModel.class);
		List<Long> permissionList = GsonUtils.fromJson(dataMap.get("permissionList"), new TypeToken<List<Long>>() {
		});

		// 隐藏ID
		JTextField idText = new JTextField(model.getId().toString());
		idText.setVisible(false);

		// 角色BOX
		JLabel roleNameLabel = new JLabel(MessageInfoConstant.ROLE_NAME);
		JTextField roleNameText = new JTextField(model.getRoleName());
		roleNameLabel.setBounds(80, 20, 80, 35);
		roleNameText.setBounds(160, 25, 140, 25);

		// 查询所有资源
		Map<String, Object> map = CallUtils.get(updateJFrame, RESOURCE_LIST_URL, null, MessageEnum.SEARCH_MSG);
		List<ResourceInfoModel> resourceList = GsonUtils.fromJson(map.get("list"),
				new TypeToken<List<ResourceInfoModel>>() {
				});

		// 资源List组装成Map
		Map<String, Long> resourceMap = new HashMap<String, Long>();
		if (CollectionUtils.isNotEmpty(resourceList)) {
			for (ResourceInfoModel temp : resourceList) {
				resourceMap.put(temp.getResourcesName(), temp.getId());
			}
		}

		JLabel resourceNameLabel = new JLabel(MessageInfoConstant.RESOURCE_LIST);
		resourceNameLabel.setBounds(80, 60, 80, 35);

		// 最后选择的资源
		List<Long> checkedResourceIdList = permissionList;

		if (CollectionUtils.isNotEmpty(resourceList)) {
			boolean checkFlag = false;
			JCheckBox checkBox = null;
			ResourceInfoModel temp;
			for (int i = 0; i < resourceList.size(); i++) {
				temp = resourceList.get(i);
				if (CollectionUtils.isNotEmpty(permissionList)) {
					if (permissionList.contains(temp.getId())) {
						checkFlag = true;
					}
				}
				// 初始化资源的选中情况
				checkBox = new JCheckBox(temp.getResourcesName(), checkFlag);
				checkBox.setBounds(160, 65 + (i * 20), 140, 25);
				panel.add(checkBox);
				// 多选框新增监听
				checkBox.addItemListener(new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						JCheckBox cb = (JCheckBox) e.getItem();
						int changeStatus = e.getStateChange();
						Long resourceId = resourceMap.get(cb.getText());
						if (changeStatus == ItemEvent.SELECTED && !checkedResourceIdList.contains(resourceId)) {
							checkedResourceIdList.add(resourceId);
						} else if (changeStatus == ItemEvent.DESELECTED && checkedResourceIdList.contains(resourceId)) {
							checkedResourceIdList.remove(resourceId);
						}
					}
				});
				checkFlag = false;
			}
		}

		// 按钮BOX
		JButton confirmBtn = new JButton(MessageInfoConstant.COMFIRM);
		JButton cancelBtn = new JButton(MessageInfoConstant.CANCLE);
		confirmBtn.setBounds(110, 220, 70, 30);
		cancelBtn.setBounds(200, 220, 70, 30);

		panel.add(roleNameLabel);
		panel.add(roleNameText);
		panel.add(resourceNameLabel);
		panel.add(confirmBtn);
		panel.add(cancelBtn);

		RepaintContentParamVo<RoleInfoModel> paramVo = new RepaintContentParamVo<RoleInfoModel>();
		paramVo.setWindow(updateJFrame);
		paramVo.setIdText(idText);
		paramVo.setRoleNameText(roleNameText);
		paramVo.setCheckedResourceIdList(checkedResourceIdList);
		paramVo.setConfirmButton(confirmBtn);
		paramVo.setCancelButton(cancelBtn);

		addListener(paramVo, parentParamVo);

		updateJFrame.setContentPane(panel);
		updateJFrame.setVisible(true);

	}

	public static void addListener(RepaintContentParamVo<RoleInfoModel> paramVo,
			RepaintContentParamVo<RoleInfoModel> parentParamVo) {
		JFrame updateJFrame = paramVo.getWindow();
		JTextField idText = paramVo.getIdText();
		JTextField roleNameText = paramVo.getRoleNameText();
		List<Long> checkedResourceIdList = paramVo.getCheckedResourceIdList();
		JButton confirmBtn = paramVo.getConfirmButton();
		JButton cancelBtn = paramVo.getCancelButton();

		confirmBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("新增已有页面,点击确认按钮.");

				Long id = Long.valueOf(idText.getText());
				String roleName = roleNameText.getText();

				logger.info("id = {}, roleName = {}", id, roleName);

				// 请求参数
				Map<String, String> param = new HashMap<String, String>();
				param.put("roleId", id.toString());
				param.put("roleName", roleName);

				Map<String, Object> result = CallUtils.executePost(updateJFrame, ROLE_UPDATE_URL, param,
						GsonUtils.toJson(checkedResourceIdList), MessageEnum.UPDATE_MSG, true);
				if (result != null) {
					updateJFrame.dispose();
					RoleTab.repaintContentBox(parentParamVo);
				}
			}
		});

		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("修改界面, 点击取消按钮.");
				updateJFrame.dispose();
			}
		});

	}

}
