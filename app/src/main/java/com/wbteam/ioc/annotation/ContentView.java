package com.wbteam.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 	布局的注解
 *  Annotation type used to class
 *  java effective
 * 
 * @autor:码农哥
 * @version:1.0
 * @created:2016-6-1  下午7:09:18
 * @contact:QQ-441293364 TEL-15105695563
 **/
//注解在运行期会被使用
@Retention(RetentionPolicy.RUNTIME)
//注解使用在类上
@Target(ElementType.TYPE)
public @interface ContentView {
	int value();
}
