package com.wbteam.onesearch.app.file.cache;


import java.lang.ref.SoftReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;

/**
 * 
 * 内存缓存
 * 
 * @author 码农哥
 */
public class MemoryCache {

	private Map<String, SoftReference<Bitmap>> cache = Collections.synchronizedMap(new HashMap<String, SoftReference<Bitmap>>());// 软引用

	public Bitmap get(String id) {
		if (!cache.containsKey(id))
			return null;
		SoftReference<Bitmap> ref = cache.get(id);
		return ref.get();
	}

	public void put(String id, Bitmap bitmap) {
		cache.put(id, new SoftReference<Bitmap>(bitmap));
	}

	/**
	 * 清除内存信息
	 * 
	 * @param url
	 */
	public void clearMemory(String url) {
		if (cache.containsKey(url)) {
			SoftReference<Bitmap> ref = cache.get(url);
			ref.clear();
			cache.remove(url);
		}
	}

	public void clear() {
		cache.clear();
	}
}
