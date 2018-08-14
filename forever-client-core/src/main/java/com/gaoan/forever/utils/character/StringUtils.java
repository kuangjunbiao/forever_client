package com.gaoan.forever.utils.character;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by one on 2017/4/14.
 */
public class StringUtils {

	public static void main(String[] args) {
		// System.out.println(org.apache.commons.lang3.StringUtils.isNumeric("22"));
	}

	/**
	 * 生成随机字符串
	 * 
	 * @param length
	 *            长度
	 * @param letter
	 *            含字母
	 * @param capital
	 *            字母可大写
	 * @return String
	 */
	public static String getRandomString(int length, boolean letter, boolean capital) {
		String val = "";
		Random random = new Random();

		// 不含字母
		if (!letter) {
			for (int i = 0; i < length; i++) {
				val += String.valueOf(random.nextInt(10));
			}
			return val;
		}

		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "letter" : "num"; // 输出字母还是数字

			if ("letter".equalsIgnoreCase(charOrNum)) { // 字符串
				int choice = 97;
				// 取得大写字母还是小写字母
				if (capital) {
					choice = random.nextInt(2) % 2 == 0 ? 65 : 97;
				}
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
				val += String.valueOf(random.nextInt(10));
			}
		}

		return val;
	}

	/**
	 * 将ErrorStack转化为String.
	 */
	public static String getStackTraceAsString(Throwable e) {
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

	public static boolean isDecimal(String num) {
		if (StringUtils.isEmpty(num)) {
			return false;
		}
		String regex = "^[0-9]+(.[0-9]+)?$";
		boolean bool = num.matches(regex);
		return bool;
	}

	public static boolean isNum(String num) {
		if (StringUtils.isEmpty(num)) {
			return false;
		}
		String regex = "^[0-9]+$";
		boolean bool = num.matches(regex);
		return bool;
	}

	public static boolean isNotEmpty(String str) {
		boolean bool = false;
		if (null != str && !"".equals(str.trim())) {
			bool = true;
		}
		return bool;
	}

	public static boolean isEmpty(String str) {
		boolean bool = false;
		if (null == str || (str != null && "".equals(str.trim()))) {
			bool = true;
		}
		return bool;
	}

	public static boolean isMobileNO(String mobiles) {
		Pattern p = Pattern.compile("^(1)\\d{10}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	public static String listToString(List list, char separator) {
		return org.apache.commons.lang3.StringUtils.join(list.toArray(), separator);
	}

	/**
	 * 做引号和空格的处理
	 * 
	 * @param str
	 * @return
	 */
	public static String processStr(String str) {
		if (StringUtils.isEmpty(str)) {
			return "";
		}

		if (str.startsWith("\"")) {
			str = str.substring(1);
		}
		if (str.endsWith("\"")) {
			str = str.substring(0, str.length() - 1);
		}
		return str.trim();
	}

	public static String trim(String str) {
		return str == null ? null : str.trim();
	}
}
