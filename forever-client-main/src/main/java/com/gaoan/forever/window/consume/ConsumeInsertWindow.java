package com.gaoan.forever.window.consume;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import org.jdesktop.swingx.JXDatePicker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.gaoan.forever.config.ServerApiConfig;
import com.gaoan.forever.constant.MessageEnum;
import com.gaoan.forever.constant.MessageInfoConstant;
import com.gaoan.forever.dialog.Dialog;
import com.gaoan.forever.model.ConsumeInfoModel;
import com.gaoan.forever.model.RepaintContentParamVo;
import com.gaoan.forever.tab.ConsumeTab;
import com.gaoan.forever.utils.character.StringUtils;
import com.gaoan.forever.utils.date.DateUtil;
import com.gaoan.forever.utils.http.CallUtils;

public class ConsumeInsertWindow {

	private static final Logger logger = LoggerFactory.getLogger(ConsumeInsertWindow.class);

	private static final String INSERT_CONSUME_URL = ServerApiConfig.getApiInsertConsumeInfo();

	/**
	 * 展示窗口
	 * 
	 * @param relativeWindow
	 */
	public static void show(JFrame relativeWindow, RepaintContentParamVo<ConsumeInfoModel> parentParamVo) {
		// 创建一个新窗口
		JFrame insertJFrame = new JFrame(MessageInfoConstant.INSERT);

		insertJFrame.setSize(400, 240);

		// 把新窗口的位置设置到 relativeWindow 窗口的中心
		insertJFrame.setLocationRelativeTo(relativeWindow);

		// 点击窗口关闭按钮, 执行销毁窗口操作（如果设置为 EXIT_ON_CLOSE, 则点击新窗口关闭按钮后, 整个进程将结束）
		insertJFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// 窗口设置为不可改变大小
		insertJFrame.setResizable(false);

		JPanel panel = new JPanel(null);

		// 消费日期BOX
		JLabel dateLabel = new JLabel(MessageInfoConstant.CONSUME_DATE);
		JXDatePicker datePicker = new JXDatePicker();
		datePicker.setFormats("yyyy-MM-dd");
		dateLabel.setBounds(80, 20, 80, 35);
		datePicker.setBounds(160, 25, 140, 25);

		// 消费金额BOX
		JLabel amountLabel = new JLabel(MessageInfoConstant.CONSUME_AMOUNT);
		JTextField amountField = new JTextField();
		amountLabel.setBounds(80, 70, 80, 28);
		amountField.setBounds(160, 70, 140, 28);

		// 备注BOX
		JLabel remarkLabel = new JLabel(MessageInfoConstant.REMARK);
		JTextArea remarkArea = new JTextArea();
		remarkLabel.setBounds(80, 110, 80, 50);
		remarkArea.setBounds(160, 110, 140, 50);

		// 按钮BOX
		JButton confirmBtn = new JButton(MessageInfoConstant.COMFIRM);
		JButton cancelBtn = new JButton(MessageInfoConstant.CANCLE);
		confirmBtn.setBounds(110, 170, 70, 30);
		cancelBtn.setBounds(200, 170, 70, 30);

		panel.add(dateLabel);
		panel.add(datePicker);
		panel.add(amountLabel);
		panel.add(amountField);
		panel.add(remarkLabel);
		panel.add(remarkArea);
		panel.add(confirmBtn);
		panel.add(cancelBtn);

		RepaintContentParamVo<ConsumeInfoModel> paramVo = new RepaintContentParamVo<ConsumeInfoModel>();
		paramVo.setWindow(insertJFrame);
		paramVo.setConsumeDatePicker(datePicker);
		paramVo.setConsumeAmountText(amountField);
		paramVo.setRemarkArea(remarkArea);
		paramVo.setConfirmButton(confirmBtn);
		paramVo.setCancelButton(cancelBtn);

		addListener(paramVo, parentParamVo);

		insertJFrame.setContentPane(panel);
		insertJFrame.setVisible(true);
	}

	public static void addListener(RepaintContentParamVo<ConsumeInfoModel> paramVo,
			RepaintContentParamVo<ConsumeInfoModel> parentParamVo) {

		JXDatePicker consumeDatePicker = paramVo.getConsumeDatePicker();
		JTextField consumeAmountText = paramVo.getConsumeAmountText();
		JTextArea remarkArea = paramVo.getRemarkArea();

		JFrame insertJFrame = paramVo.getWindow();
		JButton confirmBtn = paramVo.getConfirmButton();
		JButton cancelBtn = paramVo.getCancelButton();

		confirmBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("新增页面,点击确认按钮.");

				String date = DateUtil.dateFormatY1(consumeDatePicker.getDate());
				String amount = consumeAmountText.getText();
				String remark = remarkArea.getText();

				if (StringUtils.isEmpty(date) || !StringUtils.isDecimal(amount)) {
					Dialog.showWarningDialog(insertJFrame, MessageInfoConstant.TITLE_PROMPT,
							MessageInfoConstant.PLEASE_CHECK_PARAM);
					return;
				}

				logger.info("date = {}, amount = {}, remark = {}", date, amount, remark);

				// 请求参数
				Map<String, String> param = new HashMap<String, String>();
				param.put("date", date);
				param.put("amount", new BigDecimal(amount).setScale(2, BigDecimal.ROUND_UP).toPlainString());
				param.put("remark", remark);

				Map<String, Object> result = CallUtils.executePost(insertJFrame, INSERT_CONSUME_URL, null,
						JSON.toJSONString(param), MessageEnum.INSERT_MSG, true);
				if (result != null) {
					insertJFrame.dispose();
					ConsumeTab.repaintContentBox(parentParamVo);
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
