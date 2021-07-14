package com.atguigu.scw.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取格式化好的当前时间
 */
public class AppDateUtils {

	public static String getFormatTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String string = format.format(new Date());
		return string;
	}

	/**
	 * 根据日志格式返回当前系统时间日期、时间字符串
	 * @param 日志格式   例如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getFormatTime(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String string = format.format(new Date());
		return string;
	}

	public static String getFormatTime(String pattern, Date date) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String string = format.format(date);
		return string;
	}

}
