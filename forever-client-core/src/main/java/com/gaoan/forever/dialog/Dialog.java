package com.gaoan.forever.dialog;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.gaoan.forever.constant.MessageInfoConstant;

public class Dialog {

    /**
     * 提示对话框
     * 
     * @param jf
     * @param message
     */
    public static void showDialog(JFrame jf, String message) {
	showInfoDialog(jf, MessageInfoConstant.TITLE_PROMPT, message);
    }

    public static void showInfoDialog(JFrame jf, String title, String message) {
	JOptionPane.showMessageDialog(jf, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showWarningDialog(JFrame jf, String title, String message) {
	// 消息对话框无返回, 仅做通知作用
	JOptionPane.showMessageDialog(jf, message, title, JOptionPane.WARNING_MESSAGE);
    }

    public static void shwoConfirmDialog(JFrame jf, String title, String message) {
	/*
	 * 返回用户点击的选项, 值为下面三者之一: 是: JOptionPane.YES_OPTION 否:
	 * JOptionPane.NO_OPTION 取消: JOptionPane.CANCEL_OPTION 关闭:
	 * JOptionPane.CLOSED_OPTION
	 */
	int result = JOptionPane.showConfirmDialog(jf, message, title, JOptionPane.YES_NO_CANCEL_OPTION);
	System.out.println("选择结果: " + result);
    }

    public static void showInputDialog(JFrame jf, String message) {
	// 显示输入对话框, 返回输入的内容
	String inputContent = JOptionPane.showInputDialog(jf, message, "默认内容");
	System.out.println("输入的内容: " + inputContent);
    }

    public static void showSelectDialog(JFrame jf, String title, String message, List<Object> list) {
	Object[] selectionValues = new Object[] { "香蕉", "雪梨", "苹果" };
	// Object[] selectionValues = list.toArray();

	// 显示输入对话框, 返回选择的内容, 点击取消或关闭, 则返回null
	Object inputContent = JOptionPane.showInputDialog(jf, message, title, JOptionPane.PLAIN_MESSAGE, null,
		selectionValues, selectionValues[0]);
	System.out.println("输入的内容: " + inputContent);
    }

    public static void showOptionDialog(JFrame jf, String title, String message, List<Object> list) {
	// 选项按钮
	Object[] options = new Object[] { "香蕉", "雪梨", "苹果" };
	// Object[] options = list.toArray();

	// 显示选项对话框, 返回选择的选项索引, 点击关闭按钮返回-1
	int optionSelected = JOptionPane.showOptionDialog(jf, message, title, JOptionPane.YES_NO_CANCEL_OPTION,
		JOptionPane.ERROR_MESSAGE, null, options, options[0]);
	if (optionSelected >= 0) {
	    System.out.println("点击的按钮: " + options[optionSelected]);
	}
    }

}
