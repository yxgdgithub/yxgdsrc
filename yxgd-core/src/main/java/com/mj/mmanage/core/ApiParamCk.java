package com.mj.mmanage.core;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.mj.mmanage.core.ApiFieldsCheck.BooleanEnum;
import com.mj.mmanage.core.ApiFieldsCheck.FieldTypeEnum;

/**
 * 校验方法中传的单个参数
 * @author rdpc2622
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiParamCk {
	
	public FieldTypeEnum type() default FieldTypeEnum.STRING;
	/**
	 * 校验字段最大长度
	 * @return
	 */
	public int length() default -1;
	/**
	 * 校验字段是否为空
	 * @return
	 */
	public BooleanEnum isNull() default BooleanEnum.Yes;
	
	/**
	 * 自定义正则表达式
	 * @return
	 */
	public String matches() default "";
	/**
	 * 字段描述
	 * @return
	 */
	public String desc();
}
