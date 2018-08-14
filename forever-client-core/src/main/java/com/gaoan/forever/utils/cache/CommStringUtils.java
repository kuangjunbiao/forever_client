package com.gaoan.forever.utils.cache;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 通用字符串工具
 * @author longshengtang
 * @since 2017-04-25 9:35
 **/
public class CommStringUtils {
    /**
     * 将ErrorStack转化为String.
     */
    public static String getStackTraceAsString(Throwable e) {
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    /**
     * 将数字转换成以逗号分隔字符串
     */
    public static String getFmt4Num(long num) {
        return getFmt4Num("%,d", num);
    }

    public static String getFmt4Num(String fmt, long num) {
        return "{" + String.format(fmt, num) + "}";
    }
}
