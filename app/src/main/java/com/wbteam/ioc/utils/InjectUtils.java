package com.wbteam.ioc.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.view.View;

import com.wbteam.ioc.annotation.ContentView;
import com.wbteam.ioc.annotation.EventBase;
import com.wbteam.ioc.annotation.ViewInject;
import com.wbteam.ioc.proxy.ListenerInvocationHandler;

/**
 *  注入式工具集
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-6-1  下午7:25:19
 * @contact:QQ-441293364 TEL-15105695563
 **/
public class InjectUtils {

	/**
	 * 注入
	 * 
	 * @param activity
	 */
	public static void inject(Activity activity) {
		// 注入布局
		injectLayouts(activity);
	
		// 注入视图
		injectView(activity);
		
		// 注入事件
		injectEvents(activity);
	}

	/**
	 * 注入布局
	 * 
	 * @param activity
	 */
	private static void injectLayouts(Activity activity) {
		Class<?> clazz = activity.getClass();
		ContentView contentView = clazz.getAnnotation(ContentView.class);
		int layoutId = contentView.value();// 获取布局Id
		activity.setContentView(layoutId);
	}

	/**
	 * 注入视图
	 * 
	 * @param activity
	 */
	private static void injectView(Activity activity) {
		Class<?> clazz = activity.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			ViewInject viewInject = field.getAnnotation(ViewInject.class);
			if (viewInject != null) {
				// 获取注解的值
				int id = viewInject.value();
				// 设置给属性
				View view = activity.findViewById(id);
				field.setAccessible(true);
				try {
					field.set(activity, view);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 注入事件
	 * 
	 * @param activity
	 */
	private static void injectEvents(Activity activity) {
		Class<?> clazz = activity.getClass();
		Method[] methods = clazz.getDeclaredMethods();
		// 获取带有事件注解的方法
		for (Method method : methods) {
			Annotation[] annotations=method.getAnnotations();
			for (Annotation annotation : annotations) {
				 Class<?> annotationType = annotation.annotationType();
				 EventBase eventBase = annotationType.getAnnotation(EventBase.class);
				 if(eventBase == null){
					 continue;
				 }
				 //获取事件的三要素
				 String listenerSetter = eventBase.listenerSetter();
				 Class<?> listenerType = eventBase.listenerType();
				 String callbackMethod = eventBase.callbackMethod();

				 
				Map<String, Method> mtdMap = new HashMap<String, Method>();
				mtdMap.put(callbackMethod, method);
				// 代理
				ListenerInvocationHandler handler = new ListenerInvocationHandler(activity, mtdMap);
				 
				 //获取需要注入事件的控件对象
				try {
					Method valueMtd = annotationType.getDeclaredMethod("value");
					int[] viewIds = (int[]) valueMtd.invoke(annotation);
					for (int viewId : viewIds) {
						View view = activity.findViewById(viewId);
						if(view == null){
							continue;
						}
						//执行listenerSetter
						Method setListenerMtd = view.getClass().getMethod(listenerSetter, listenerType);
						Object proxy = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class<?>[]{listenerType}, handler);
						setListenerMtd.invoke(view, proxy);
					}
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
