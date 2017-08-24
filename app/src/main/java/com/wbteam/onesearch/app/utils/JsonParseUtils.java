package com.wbteam.onesearch.app.utils;


import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Json解析类 
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-5-27  上午10:43:31
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class JsonParseUtils {
	
	private static Gson gson;
	
	private static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder().create();
        }
        return gson;
    }


    public static <T> T fromJson(String jsonData, Class<T> clazz) {
        return fromJson(jsonData, (Type) clazz);
    }

    public static <T> T fromJson(String jsonData, Type type) {
        try {

            T object = getGson().fromJson(jsonData, type);
            return object;
        } catch (Exception ex) {
            if (Logger.isDebug()){
            	Logger.d(ex.getMessage()+"");
            }
            return null;
        }
    }

    public static String toJson(Object object) {
        return getGson().toJson(object);
    }
	
}
