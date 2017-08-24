package com.wbteam.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  事件的注解
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-6-1  下午7:53:54
 * @contact:QQ-441293364 TEL-15105695563
 **/
@Retention(RetentionPolicy.RUNTIME)
//该注解在另外一个注解上使用
@Target(ElementType.ANNOTATION_TYPE)
public @interface EventBase {
	
	/**
	 * 设置事件监听的方法
	 * @return
	 */
	String listenerSetter();
	
	/**
	 * 事件监听的类型
	 * @return
	 */
	Class<?> listenerType();
	
	/**
	 * 事件被触发之后，执行的回调方法的名称
	 * @return
	 */
	String callbackMethod();
}
