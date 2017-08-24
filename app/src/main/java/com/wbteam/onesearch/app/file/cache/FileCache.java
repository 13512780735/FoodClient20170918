package com.wbteam.onesearch.app.file.cache;


import java.io.File;

import android.content.Context;

import com.wbteam.onesearch.app.utils.PathUtils;

/**
 * 文件的缓存
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2014-7-25上午9:20:42
 * 
 */
public class FileCache {

	private File cacheDir;

	public FileCache(Context context) {
		cacheDir = new File(PathUtils.getCachePath());
		if (!cacheDir.exists())
			cacheDir.mkdirs();
	}

	public File getFile(String url) {
		String filename = String.valueOf(url.hashCode());
		File f = new File(cacheDir, filename);
		return f;

	}

	public void clear() {
		File[] files = cacheDir.listFiles();
		if (files == null)
			return;
		for (File f : files)
			f.delete();
	}

}
