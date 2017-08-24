package com.wbteam.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import android.view.View;

/**
 *  事件的注解
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-6-1  下午7:53:10
 * @contact:QQ-441293364 TEL-15105695563
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
//注入事件三要素
@EventBase(listenerSetter = "setOnClickListener",listenerType = View.OnClickListener.class, callbackMethod = "onClick")
public @interface OnClick {

	int[] value();

}
