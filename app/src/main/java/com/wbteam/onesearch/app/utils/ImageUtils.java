package com.wbteam.onesearch.app.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *  图片工具集
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-9-12  上午10:59:16
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class ImageUtils {
	/**
	 * 复制图片流
	 * 
	 * @param is
	 * @param os
	 */
	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		byte[] buffer = new byte[buffer_size];
		int len = 0;
		try {
			while ((len = is.read(buffer, 0, buffer_size)) != -1) {
				os.write(buffer, 0, len);
			}
			is.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
