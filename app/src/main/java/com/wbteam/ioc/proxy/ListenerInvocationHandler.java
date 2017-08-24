package com.wbteam.ioc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

import android.app.Activity;

/**
 *  代理机制
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-6-1  下午8:51:03
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class ListenerInvocationHandler implements InvocationHandler {

	private Map<String, Method> mtdMap;
	private Activity target;

	public ListenerInvocationHandler(Activity target, Map<String, Method> map) {
		this.mtdMap = map;
		this.target = target;

	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String name = method.getName();
		Method mtd = mtdMap.get(name);
		if (mtd != null) {
			return mtd.invoke(target, args);
		}
		return method.invoke(proxy, args);
	}

}
