package com.mj.mmanage.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 登陆校验
 * @author 郭保利
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiLoginAuth {

	enum LoginAuth{YES,NO};
	
	public LoginAuth value() default LoginAuth.NO;
	
}
