package com.gaoan.forever.window.color;

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
import com.gaoan.forever.model.ColorInfoModel;
import com.gaoan.forever.model.RepaintContentParamVo;
import com.gaoan.forever.tab.ColorTab;
import com.gaoan.forever.tab.PurchaseOrderTab;
import com.gaoan.forever.tab.SalesOrderTab;
import com.gaoan.forever.tab.StockTab;
import com.gaoan.forever.utils.http.CallUtils;

public class ColorInsertWindow {

	private static final Logger logger = LoggerFactory.getLogger(ColorInsertWindow.class);

	private static final String COLOR_INSERT_URL = ServerApiConfig.getApiInsertColor();

	/**
	 * 展示窗口
	 * 
	 * @param relativeWindow
	 */
	public static void show(JFrame relativeWindow, RepaintContentParamVo<ColorInfoModel> parentParamVo) {
		// 创建一个新窗口
		JFrame insertJFrame = new JFrame(MessageInfoConstant.INSERT);

		insertJFrame.setSize(400, 150);

		// 把新窗口的位置设置到 relativeWindow 窗口的中心
		insertJFrame.setLocationRelativeTo(relativeWindow);

		// 点击窗口关闭按钮, 执行销毁窗口操作（如果设置为 EXIT_ON_CLOSE, 则点击新窗口关闭按钮后, 整个进程将结束）
		insertJFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// 窗口设置为不可改变大小
		insertJFrame.setResizable(false);

		JPanel panel = new JPanel(null);

		// 颜色名BOX
		JLabel colorNameLabel = new JLabel(MessageInfoConstant.COLOR_NAME);
		JTextField colorNameText = new JTextField();
		colorNameLabel.setBounds(80, 20, 80, 35);
		colorNameText.setBounds(160, 25, 140, 25);

		// 按钮BOX
		JButton confirmBtn = new JButton(MessageInfoConstant.COMFIRM);
		JButton cancelBtn = new JButton(MessageInfoConstant.CANCLE);
		confirmBtn.setBounds(110, 70, 70, 30);
		cancelBtn.setBounds(200, 70, 70, 30);

		panel.add(colorNameLabel);
		panel.add(colorNameText);
		panel.add(confirmBtn);
		panel.add(cancelBtn);

		RepaintContentParamVo<ColorInfoModel> paramVo = new RepaintContentParamVo<ColorInfoModel>();
		paramVo.setWindow(insertJFrame);
		paramVo.setColorNameText(colorNameText);
		paramVo.setConfirmButton(confirmBtn);
		paramVo.setCancelButton(cancelBtn);

		addListener(paramVo, parentParamVo);

		insertJFrame.setContentPane(panel);
		insertJFrame.setVisible(true);

	}

	public static void addListener(RepaintContentParamVo<ColorInfoModel> paramVo,
			RepaintContentParamVo<ColorInfoModel> parentParamVo) {
		JFrame insertJFrame = paramVo.getWindow();
		JTextField colorNameText = paramVo.getColorNameText();
		JButton confirmBtn = paramVo.getConfirmButton();
		JButton cancelBtn = paramVo.getCancelButton();

		confirmBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("新增页面,点击确认按钮.");

				String colorName = colorNameText.getText();

				logger.info("colorName = {}", colorName);

				// 请求参数
				Map<String, String> param = new HashMap<String, String>();
				param.put("colorName", colorName);

				Map<String, Object> result = CallUtils.executeGet(insertJFrame, COLOR_INSERT_URL, param,
						MessageEnum.INSERT_MSG, true);
				if (result != null) {
					insertJFrame.dispose();
					ColorTab.repaintContentBox(parentParamVo);
					PurchaseOrderTab.initColorCondition();
					SalesOrderTab.initColorCondition();
					StockTab.initColorCondition();
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
