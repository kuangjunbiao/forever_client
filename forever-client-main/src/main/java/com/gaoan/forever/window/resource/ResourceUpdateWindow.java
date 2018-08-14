package com.gaoan.forever.window.resource;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
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

import com.gaoan.forever.combobox.KeyValComboBox;
import com.gaoan.forever.config.ServerApiConfig;
import com.gaoan.forever.constant.ForeverConstant;
import com.gaoan.forever.constant.MessageEnum;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.model.KeyValBoxVo;
import com.gaoan.forever.model.RepaintContentParamVo;
import com.gaoan.forever.model.ResourceInfoModel;
import com.gaoan.forever.tab.ResourceTab;
import com.gaoan.forever.utils.gson.JsonUtils;
import com.gaoan.forever.utils.http.CallUtils;
import com.gaoan.forever.window.purchase.PurchaseInsertUnHaveWindow;

public class ResourceUpdateWindow {

	private static final Logger logger = LoggerFactory.getLogger(PurchaseInsertUnHaveWindow.class);

	private static final String RESOURCE_DETAIL_URL = ServerApiConfig.getApiGetResourceDetail();

	private static final String RESOURCE_UPDATE_URL = ServerApiConfig.getApiUpdateResources();

	/**
	 * 展示窗口
	 * 
	 * @param relativeWindow
	 */
	@SuppressWarnings("unchecked")
	public static void show(JFrame relativeWindow, Long resourceId,
			RepaintContentParamVo<ResourceInfoModel> parentParamVo) {
		// 创建一个新窗口
		JFrame updateJFrame = new JFrame(MessageInfoConstant.UPDATE);

		updateJFrame.setSize(400, 190);

		// 把新窗口的位置设置到 relativeWindow 窗口的中心
		updateJFrame.setLocationRelativeTo(relativeWindow);

		// 点击窗口关闭按钮, 执行销毁窗口操作（如果设置为 EXIT_ON_CLOSE, 则点击新窗口关闭按钮后, 整个进程将结束）
		updateJFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// 窗口设置为不可改变大小
		updateJFrame.setResizable(false);

		JPanel panel = new JPanel(null);

		// 根据编号查询当前资源信息
		Map<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("resourceId", resourceId.toString());
		Map<String, Object> resourceDetail = CallUtils.get(relativeWindow, RESOURCE_DETAIL_URL, queryParam,
				MessageEnum.SEARCH_MSG);
		Map<String, Object> orderDetailMap = (Map<String, Object>) resourceDetail.get("info");
		ResourceInfoModel model = JsonUtils.fromJson(orderDetailMap, ResourceInfoModel.class);

		// 隐藏ID
		JTextField idText = new JTextField(model.getId().toString());
		idText.setVisible(false);

		// 资源名BOX
		JLabel resourceNameLabel = new JLabel(MessageInfoConstant.RESOURCE);
		JTextField resourceNameText = new JTextField(model.getResourcesName());
		resourceNameLabel.setBounds(80, 20, 80, 35);
		resourceNameText.setBounds(160, 25, 140, 25);

		// 封装类型Vector
		Vector<KeyValBoxVo> typeVector = new Vector<KeyValBoxVo>();
		KeyValBoxVo menuTypeVo = new KeyValBoxVo(ForeverConstant.RESOURCE_TYPE_MENU,
				MessageInfoConstant.RESOURCE_TYPE_MENU);
		KeyValBoxVo operatorTypeVo = new KeyValBoxVo(ForeverConstant.RESOURCE_TYPE_OPERATE,
				MessageInfoConstant.RESOURCE_TYPE_OPERATOR);
		typeVector.add(menuTypeVo);
		typeVector.add(operatorTypeVo);

		// 类型BOX
		JLabel typeLabel = new JLabel(MessageInfoConstant.TYPE);
		KeyValComboBox<KeyValBoxVo> typeComboBox = new KeyValComboBox<KeyValBoxVo>(typeVector);
		// 选中当前数据
		typeComboBox.setSelectedKey(model.getType());
		typeLabel.setBounds(80, 60, 80, 35);
		typeComboBox.setBounds(160, 65, 140, 25);

		// 按钮BOX
		JButton confirmBtn = new JButton(MessageInfoConstant.COMFIRM);
		JButton cancelBtn = new JButton(MessageInfoConstant.CANCLE);
		confirmBtn.setBounds(110, 110, 70, 30);
		cancelBtn.setBounds(200, 110, 70, 30);

		panel.add(resourceNameLabel);
		panel.add(resourceNameText);
		panel.add(typeLabel);
		panel.add(typeComboBox);
		panel.add(confirmBtn);
		panel.add(cancelBtn);

		RepaintContentParamVo<ResourceInfoModel> paramVo = new RepaintContentParamVo<ResourceInfoModel>();
		paramVo.setWindow(updateJFrame);
		paramVo.setIdText(idText);
		paramVo.setResourceNameText(resourceNameText);
		paramVo.setTypeComboBox(typeComboBox);
		paramVo.setConfirmButton(confirmBtn);
		paramVo.setCancelButton(cancelBtn);

		addListener(paramVo, parentParamVo);

		updateJFrame.setContentPane(panel);
		updateJFrame.setVisible(true);

	}

	public static void addListener(RepaintContentParamVo<ResourceInfoModel> paramVo,
			RepaintContentParamVo<ResourceInfoModel> parentParamVo) {
		JFrame updateJFrame = paramVo.getWindow();
		JTextField idText = paramVo.getIdText();
		JTextField resourceText = paramVo.getResourceNameText();
		KeyValComboBox<KeyValBoxVo> typeComboBox = paramVo.getTypeComboBox();
		JButton confirmBtn = paramVo.getConfirmButton();
		JButton cancelBtn = paramVo.getCancelButton();

		confirmBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("新增已有页面,点击确认按钮.");

				Long id = Long.valueOf(idText.getText());
				String resourceName = resourceText.getText();
				String type = typeComboBox.getSelectedKey();

				logger.info("id = {}, resourceName = {}, type = {}", id, resourceName, type);

				// 请求参数
				Map<String, String> param = new HashMap<String, String>();
				param.put("resourceId", id.toString());
				param.put("resourceName", resourceName);
				param.put("type", type);

				Map<String, Object> result = CallUtils.executeGet(updateJFrame, RESOURCE_UPDATE_URL, param,
						MessageEnum.UPDATE_MSG, true);
				if (result != null) {
					updateJFrame.dispose();
					ResourceTab.repaintContentBox(parentParamVo);
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
