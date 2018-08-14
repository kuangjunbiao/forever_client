package com.gaoan.forever.window.size;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gaoan.forever.config.ServerApiConfig;
import com.gaoan.forever.constant.MessageEnum;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.model.RepaintContentParamVo;
import com.gaoan.forever.model.SizeInfoModel;
import com.gaoan.forever.tab.PurchaseOrderTab;
import com.gaoan.forever.tab.SalesOrderTab;
import com.gaoan.forever.tab.SizeTab;
import com.gaoan.forever.tab.StockTab;
import com.gaoan.forever.utils.http.CallUtils;

public class SizeInsertWindow {

	private static final Logger logger = LoggerFactory.getLogger(SizeInsertWindow.class);

	private static final String SIZE_INSERT_URL = ServerApiConfig.getApiInsertSize();

	/**
	 * 展示窗口
	 * 
	 * @param relativeWindow
	 */
	public static void show(JFrame relativeWindow, RepaintContentParamVo<SizeInfoModel> parentParamVo) {
		// 创建一个新窗口
		JFrame insertJFrame = new JFrame(MessageInfoConstant.INSERT);

		insertJFrame.setSize(400, 190);

		// 把新窗口的位置设置到 relativeWindow 窗口的中心
		insertJFrame.setLocationRelativeTo(relativeWindow);

		// 点击窗口关闭按钮, 执行销毁窗口操作（如果设置为 EXIT_ON_CLOSE, 则点击新窗口关闭按钮后, 整个进程将结束）
		insertJFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// 窗口设置为不可改变大小
		insertJFrame.setResizable(false);

		JPanel panel = new JPanel(null);

		// 尺寸名BOX
		JLabel sizeNameLabel = new JLabel(MessageInfoConstant.SIZE_NAME);
		JTextField sizeNameText = new JTextField();
		sizeNameLabel.setBounds(80, 20, 80, 35);
		sizeNameText.setBounds(160, 25, 140, 25);

		// 尺寸简称BOX
		JLabel sizeCodeLabel = new JLabel(MessageInfoConstant.SIZE_CODE);
		JTextField sizeCodeText = new JTextField();
		sizeCodeLabel.setBounds(80, 60, 80, 35);
		sizeCodeText.setBounds(160, 65, 140, 25);

		// 按钮BOX
		JButton confirmBtn = new JButton(MessageInfoConstant.COMFIRM);
		JButton cancelBtn = new JButton(MessageInfoConstant.CANCLE);
		confirmBtn.setBounds(110, 110, 70, 30);
		cancelBtn.setBounds(200, 110, 70, 30);

		panel.add(sizeNameLabel);
		panel.add(sizeNameText);
		panel.add(sizeCodeLabel);
		panel.add(sizeCodeText);
		panel.add(confirmBtn);
		panel.add(cancelBtn);

		RepaintContentParamVo<SizeInfoModel> paramVo = new RepaintContentParamVo<SizeInfoModel>();
		paramVo.setWindow(insertJFrame);
		paramVo.setSizeNameText(sizeNameText);
		paramVo.setSizeCodeText(sizeCodeText);
		paramVo.setConfirmButton(confirmBtn);
		paramVo.setCancelButton(cancelBtn);

		addListener(paramVo, parentParamVo);

		insertJFrame.setContentPane(panel);
		insertJFrame.setVisible(true);

	}

	public static void addListener(RepaintContentParamVo<SizeInfoModel> paramVo,
			RepaintContentParamVo<SizeInfoModel> parentParamVo) {
		JFrame insertJFrame = paramVo.getWindow();
		JTextField sizeNameText = paramVo.getSizeNameText();
		JTextField sizeCodeText = paramVo.getSizeCodeText();
		JButton confirmBtn = paramVo.getConfirmButton();
		JButton cancelBtn = paramVo.getCancelButton();

		confirmBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("新增页面,点击确认按钮.");

				String sizeName = sizeNameText.getText();
				String sizeCode = sizeCodeText.getText();

				logger.info("sizeName = {}, sizeCode = {}", sizeName, sizeCode);

				// 请求参数
				Map<String, String> param = new HashMap<String, String>();
				param.put("sizeName", sizeName);
				param.put("sizeCode", sizeCode);

				Map<String, Object> result = CallUtils.executeGet(insertJFrame, SIZE_INSERT_URL, param,
						MessageEnum.INSERT_MSG, true);
				if (result != null) {
					insertJFrame.dispose();
					SizeTab.repaintContentBox(parentParamVo);
					PurchaseOrderTab.initSizeCondition();
					SalesOrderTab.initSizeCondition();
					StockTab.initSizeCondition();
				}
			}
		});

		cancelBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("新增界面, 点击取消按钮.");
				insertJFrame.dispose();
			}
		});

	}

}
