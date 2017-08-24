package com.wbteam.onesearch.app.utils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.os.Environment;

import com.wbteam.onesearch.app.utils.io.IOUtils;

/**
 *  @author 码农哥
 *	@date 2016-3-25下午2:24:36
 *	@email 441293364@qq.com
 *	@TODO
 *
 *  ** *** ━━━━━━神兽出没━━━━━━
 *  ** ***       ┏┓　　  ┏┓
 *  ** *** 	   ┏┛┻━━━┛┻┓
 *  ** *** 　  ┃　　　　　　　┃
 *  ** *** 　　┃　　　━　　　┃
 *  ** *** 　　┃　┳┛　┗┳　┃
 *  ** *** 　　┃　　　　　　　┃
 *  ** *** 　　┃　　　┻　　　┃
 *  ** *** 　　┃　　　　　　　┃
 *  ** *** 　　┗━┓　　　┏━┛
 *  ** *** 　　　　┃　　　┃ 神兽保佑,代码永无bug
 *  ** *** 　　　　┃　　　┃
 *  ** *** 　　　　┃　　　┗━━━┓
 *  ** *** 　　　　┃　　　　　　　┣┓
 *  ** *** 　　　　┃　　　　　　　┏┛
 *  ** *** 　　　　┗┓┓┏━┳┓┏┛
 *  ** *** 　　　　  ┃┫┫  ┃┫┫
 *  ** *** 　　　　  ┗┻┛　┗┻┛
 */
public class FileUtils {
	public static String getSDCardPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath();
	}

	public static String getSavePath(String folderName) {
		return getSaveFolder(folderName).getAbsolutePath();
	}

	public static File getSaveFolder(String folderName) {
		File file = new File(getSDCardPath() + File.separator + folderName + File.separator);
		file.mkdirs();
		return file;
	}

	public static File getSaveFile(String folderPath, String fileNmae) {
		File file = new File(getSavePath(folderPath) + File.separator + fileNmae);

		try {
			file.createNewFile();
		} catch (IOException var4) {
			var4.printStackTrace();
		}

		return file;
	}

	public static String inputStream2String(InputStream is) {
		if (null == is) {
			return null;
		} else {
			StringBuilder resultSb = null;

			try {
				BufferedReader ex = new BufferedReader(new InputStreamReader(is));
				resultSb = new StringBuilder();

				String len;
				while (null != (len = ex.readLine())) {
					resultSb.append(len);
				}
			} catch (Exception var7) {
			} finally {
				closeIO(is);
			}

			return null == resultSb ? null : resultSb.toString();
		}
	}

	public static void closeIO(Closeable... closeables) {
		if (null != closeables && closeables.length > 0) {
			Closeable[] var1 = closeables;
			int var2 = closeables.length;

			for (int var3 = 0; var3 < var2; ++var3) {
				Closeable cb = var1[var3];

				try {
					if (null != cb) {
						cb.close();
					}
				} catch (IOException var6) {
					throw new RuntimeException(FileUtils.class.getClass().getName(), var6);
				}
			}

		}
	}

	/**
	 * @param file
	 * @param data
	 * @throws IOException
	 */
	public static void writeStringToFile(File file, String data) throws IOException {
		writeStringToFile(file, data, null, false);
	}

	public static void writeStringToFile(File file, String data, String encoding) throws IOException {
		writeStringToFile(file, data, encoding, false);
	}

	public static void writeStringToFile(File file, String data, String encoding, boolean append) throws IOException {
		OutputStream out = null;
		try {
			out = openOutputStream(file, append);
			IOUtils.write(data, out, encoding);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	public static FileOutputStream openOutputStream(File file, boolean append) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file + "' exists but is a directory");
			}
			if (file.canWrite() == false) {
				throw new IOException("File '" + file + "' cannot be written to");
			}
		} else {
			File parent = file.getParentFile();
			if (parent != null) {
				if (!parent.mkdirs() && !parent.isDirectory()) {
					throw new IOException("Directory '" + parent + "' could not be created");
				}
			}
		}
		return new FileOutputStream(file, append);
	}

}
