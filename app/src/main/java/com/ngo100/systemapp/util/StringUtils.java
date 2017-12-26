package com.ngo100.systemapp.util;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;

import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public class StringUtils {

	/**
	 * 正则表达式：验证密码
	 */
	public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{8,16}$";
	/**
	 * 正则表达式：验证密码(6-20位字母数字组合)
	 */
	public static final String REGEX_PASSWORD6_20 = "^[a-zA-Z0-9]{6,20}$";
	/**
	 * 正则表达式：验证手机号
	 */
	public static final String REGEX_MOBILE = "^1(3|5|7|8)/d{9}$";

	/**
	 * 正则表达式：验证身份证
	 */
	public static final String REGEX_ID_CARD = "^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$";
	/**
	 * 正则表达式：验证URL
	 */
//	public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
	/**
	 * 判断字符串是否为空 空返回false，反之返回true
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNoEmpty(String s) {
		if ("".equals(s) || "[]".equals(s) || "null".equals(s)
				|| "NULL".equals(s) || null == s) {
			return false;
		}
		return true;
	}

	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobiles) {
		if (!isNoEmpty(mobiles)) {
			return false;
		}
		Pattern p = null;
		Matcher m = null;
		boolean b = false;
		p = Pattern.compile("^1(3|4|5|7|8)\\d{9}$"); // 验证手机号
		m = p.matcher(mobiles);
		b = m.matches();
		return b;
	}

	/**
	 * 使用正则表达式检查邮箱地址格式
	 * 
	 * @param email
	 * @return
	 */
	public static boolean checkEmailAddress(String email) {
		boolean isValid = false;
		String str = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);
		if (m.matches()) {
			isValid = true;
		}

		return isValid;
	}

	/**
	 * uri编码
	 * 
	 * @param paramString
	 * @return
	 */
	public static String toURLEncoded(String paramString) {
		if (paramString == null || paramString.equals("")) {
			return "";
		}

		try {
			String str = new String(paramString.getBytes(), "UTF-8");
			str = URLEncoder.encode(str, "UTF-8");
			return str;
		} catch (Exception localException) {
		}

		return "";
	}

	/**
	 * 校验身份证
	 * 
	 * @param idCard
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isIDCard(String idCard) {
		return Pattern.matches(REGEX_ID_CARD, idCard);
	}

	/**
	 * 截取url获取code
	 * 
	 * @param url
	 * @return
	 */
	public static String subStrCode(String url) {
		String code = "";
		String arg = url.substring(url.indexOf("?") + 1, url.length());
		String[] strs = arg.split("&");
		HashMap<String, String> map = new HashMap<String, String>();
		for (int x = 0; x < strs.length; x++) {
			String[] strs2 = strs[x].split("=");
			if (strs2.length == 2) {
				map.put(strs2[0], strs2[1]);
			}
		}

		for (String key : map.keySet()) {
			if (TextUtils.equals("code", key)) {
				code = map.get(key);
				break;
			}
		}
		return code;
	}

	/**
	 * 校验URL
	 * 
	 * @param url
	 * @return 校验通过返回true，否则返回false
	 */
//	public static boolean isUrl(String url) {
//		return Pattern.matches(REGEX_URL, url);
//	}

	/**
	 * 校验密码
	 * 
	 * @param password
	 *            8-16位字母数字组合
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isPassword(String password) {
		return Pattern.matches(REGEX_PASSWORD, password);
	}

	/**
	 * 校验密码
	 * 
	 * @param password
	 *            6-20位字母数字组合
	 * @return校验通过返回true，否则返回false
	 */
	public static boolean isPassword_6_20(String password) {
		return Pattern.matches(REGEX_PASSWORD6_20, password);
	}

	/**
	 * null返回空字符串
	 * 
	 * @param s
	 * @return
	 */
	public static String getNoNullString(String s) {
		if (isNoEmpty(s)) {
			return s;
		} else {
			return "";
		}

	}

	/**
	 * 设置部分字体颜色
	 * 
	 * @param str
	 * @param color
	 * @param fstart
	 * @param fend
	 * @return
	 */
	public static SpannableStringBuilder setTextViewSpanColor(String str,
                                                              int color, int fstart, int fend) {
		SpannableStringBuilder style = new SpannableStringBuilder(str);
		style.setSpan(new ForegroundColorSpan(color), fstart, fend,
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		return style;
	}

	/**
	 * 设置部分字体颜色
	 * 
	 * @param str
	 * @param color
	 * @param p
	 * @return
	 */
	public static SpannableStringBuilder setTextViewSpanColor(String str,
                                                              int color, Pattern p) {
		SpannableStringBuilder s = new SpannableStringBuilder(str);
		Matcher m = p.matcher(s);
		while (m.find()) {
			int start = m.start();
			int end = m.end();
			s.setSpan(new ForegroundColorSpan(color), start, end,
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		return s;
	}

	/**
	 * 判断字符串是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 判断是否为电话号码
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public static boolean isPhoneNumberValid(String phoneNumber) {

		boolean isValid = false;
		/*
		 * 可接受的电话格式有: * ^\\(? : 可以使用 "(" 作为开头 * (\\d{3}): 紧接着三个数字 * \\)? :
		 * 可以使用")"接续 * [- ]? : 在上述格式后可以使用具选择性的 "-". * (\\d{3}) : 再紧接着三个数字 * [-
		 * ]? : 可以使用具选择性的 "-" 接续. * (\\d{4})$: 以四个数字结束. * 可以比对下列数字格式: *
		 * (123)456-7890, 123-456-7890, 1234567890, (123)-456-7890
		 */
		String expression = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
		String expression2 = "^\\(?(\\d{2})\\)?[- ]?(\\d{4})[- ]?(\\d{4})$";
		CharSequence inputStr = phoneNumber;
		/* 建立Pattern */
		Pattern pattern = Pattern.compile(expression);
		/* 将Pattern 以参数传入Matcher作Regular expression */
		Matcher matcher = pattern.matcher(inputStr);
		/* 建立Pattern2 */
		Pattern pattern2 = Pattern.compile(expression2);
		/* 将Pattern2 以参数传入Matcher2作Regular expression */
		Matcher matcher2 = pattern2.matcher(inputStr);
		if (matcher.matches() || matcher2.matches()) {
			isValid = true;
		}
		return isValid;
	}

	/**
	 * 设置数字显示为英文状态下带“,”，如（1,254.00）
	 * 
	 * @param str
	 * @return
	 */
	public static String getEnglishNumString(String str) {
		DecimalFormat df = new DecimalFormat("###,###.00");
		return df.format(Double.parseDouble(str));
	}
/**
 * 设置两位数
 * */
	public static String getTwoPointNmuber(String str) {
		double num= Double.parseDouble(str);
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(num);

	}
	
	
	
	/**
	 * 清除<!-- --> 内容 项目需要 
	 * */
	public static String replaceContext(String str){
		if (str==null) {
			return "";
		}
		int a=str.indexOf("<");
		int b=str.indexOf(">");
		if (a!=-1&&b!=-1) {
			return str.replace(str.substring(a, b + 1),"");
		}
		return str;
	}
	
	/**
	 * 去掉小数点后多余的0
	 * @param b
	 * @return
	 */
	public static String removeTailZero(double b) {
		 String s = b+"";
		 int i, len = s.length();
		 for (i = 0; i < len; i++)
		  if (s.charAt(len - 1 - i) != '0')
		   break;
		 if (s.charAt(len - i - 1) == '.')
		  return s.substring(0, len - i - 1);
		 return s.substring(0, len - i);
		}
	
}
