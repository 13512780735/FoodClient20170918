package com.wbteam.onesearch.app.utils;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Type;

import android.util.Base64;

import com.alibaba.fastjson.JSON;
import com.wbteam.onesearch.app.AppContext;
import com.wbteam.onesearch.app.utils.io.FileUtil;
import com.wbteam.onesearch.app.utils.io.IOUtils;

/**
 *  @author 码农哥
 *	@date 2016-8-6下午1:35:07
 *	@email 441293364@qq.com
 *	@TODO 缓存信息的存储
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

public class Storage {
	/**
     * 保存对象到缓存
     *
     * @param object
     */
    public static void save(Object object) {
        String subDir = object.getClass().getSimpleName();
        saveObject(subDir, object);
    }

    public static void saveObject(String key, Object obj) {
        try {
            File file = new File(AppContext.getInstance().getCacheDir().getPath()
                    + File.separator + key);

            String json = JSON.toJSONString(obj);

            String base64 = new String(Base64.encode(json.getBytes(), Base64.DEFAULT));

            FileUtil.writeStringToFile(file, base64);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @SuppressWarnings("unchecked")
    public static <T> T getObject(String key, Type type) {
        try {
            FileInputStream fileInputStream = new FileInputStream(AppContext.getInstance().getCacheDir().getPath()
                    + File.separator + key);
            String data = IOUtils.toString(fileInputStream);

            byte[] base64Bytes = Base64.decode(data, Base64.DEFAULT);
            return (T)JSON.parseObject(new String(base64Bytes), type);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 从缓存获取对象
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getObject(Class<T> clazz) {
        String subDir = clazz.getSimpleName();
        return getObject(subDir, clazz);
    }

    /**
     * 清除缓存的对象
     */
    public static void clearObject(String key) {

        FileUtil.delete(new File(AppContext.getInstance().getCacheDir().getPath()
                + File.separator + key));
    }

    /**
     * 清除缓存的对象
     */
    public static void clearObject(Class<?> clazz) {
        String subDir = clazz.getSimpleName();
        clearObject(subDir);
    }
}
