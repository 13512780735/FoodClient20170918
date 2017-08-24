package com.wbteam.onesearch.app.utils;


import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 *  
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016年8月11日  下午3:42:18
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class EncoderHandler {
	private static String secret = "tang_wenshi_lkasjgwlfs";
	
	private static final String ALGORITHM = "MD5";

	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * encode string
	 *
	 * @param algorithm
	 * @param str
	 * @return String
	 */
	public static String encode(String algorithm, String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	
	/**
	 * encode By MD5
	 *
	 * @param str
	 * @return String
	 */
	public static String encodeByMD5(String str) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
			messageDigest.update(str.getBytes());
			return getFormattedText(messageDigest.digest());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * Takes the raw bytes from the digest and formats them correct.
	 *
	 * @param bytes
	 *            the raw bytes from the digest.
	 * @return the formatted bytes.
	 */
	private static String getFormattedText(byte[] bytes) {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		// 把密文转换成十六进制的字符串形式
		for (int j = 0; j<len; j++) { 			
			buf.append(HEX_DIGITS[(bytes[j] >>4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] &0x0f]);
		}
		return buf.toString();
	}
	
	/**
	 * 添加参数的封装方法
	 */
	private static StringBuffer getBeforeSign(TreeMap<String, String> params,String orgin) {
		if (params == null)
			return null;
		Map<String, String> treeMap = new TreeMap<String, String>();
		treeMap.putAll(params);
		StringBuffer buffer = new StringBuffer();
		Iterator<String> iter = treeMap.keySet().iterator();
		while (iter.hasNext()) {
			String name = (String) iter.next();
			String keyValue = params.get(name);
			if (name != null && name != "" && keyValue != null
					&& keyValue != "") {
				if (buffer.length() == 0) {
					buffer.append(name).append("=").append(keyValue);
				}else{
					buffer.append("&"+name).append("=").append(keyValue);
				}
			}
		}
		buffer.append(orgin);
		return buffer;
	}
	
	/**
	 * 创建签名
	 * 
	 * @param params
	 * @return
	 */
	public static String createSignString(TreeMap<String, String> params){
		String signBefore = getBeforeSign(params, secret).toString();
		Logger.e("TAG", "签名前："+signBefore);
		String md5Str = EncoderHandler.encodeByMD5(signBefore);
		Logger.e("TAG", "Md5："+md5Str);
		return EncoderHandler.encode("SHA1",md5Str);
	}
	
	/**
	 * 获取参数Json组
	 * 
	 * @param params
	 * @return
	 */
	public static String getDataParams(TreeMap<String, String> params){
		if (params == null)
			return null;
		Map<String, String> treeMap = new TreeMap<String, String>();
		treeMap.putAll(params);
		StringBuilder paramJson = new StringBuilder();
		Iterator<String> iter = treeMap.keySet().iterator();
		while (iter.hasNext()) {
			String name = (String) iter.next();
			String keyValue = params.get(name);
			if (name != null && name != "" && keyValue != null
					&& keyValue != "") {
				if (paramJson.length() == 0) {
					paramJson.append("\"").append(name).append("\":\"")
							.append(keyValue).append("\"");
				} else {
					paramJson.append(",\"").append(name).append("\":\"")
							.append(keyValue).append("\"");
				}
			}
		}
		Logger.e("TAG", "拼接JSON串====>:{" + paramJson + "}");
		return "{" + paramJson + "}";
	}

//	public static void main(String[] args) {
//		TreeMap<String, String> params = new TreeMap<String, String>();
//		params.put("password", "123456");
//		params.put("username", "test");
//		params.put("a", "1111");
//		//tang_KUGUAN_ljijgekpassword123456usernametest
//		String result = EncoderHandler.encodeByMD5("password=123456&username=testtang_KUGUAN_ljijgek");
//		System.out.println(EncoderHandler.encode("SHA1",result));
//	}
}
