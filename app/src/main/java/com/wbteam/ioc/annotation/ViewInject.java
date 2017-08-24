package com.wbteam.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  视图的注解
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-6-1  下午7:33:43
 * @contact:QQ-441293364 TEL-15105695563
 **/
@Retention(RetentionPolicy.RUNTIME)
//使用在属性上
@Target(ElementType.FIELD)
public @interface ViewInject {
	int value();
}
