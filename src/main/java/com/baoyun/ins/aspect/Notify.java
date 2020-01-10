package com.baoyun.ins.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.web.bind.annotation.Mapping;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Mapping
public @interface Notify {

	/**
	 * C--评论
	 * P--点赞 
	 * @Description: TODO 
	 * @Author jiahy
	 * @date Sep 7, 2019 4:02:31 PM
	 * @param @return 
	 * @throws
	 */
	String type() default "";
	
	/**  
	 *  add--A
	 *  update--U
	 *  delete--D
	 * @Description: TODO 
	 * @Author jiahy
	 * @date Sep 7, 2019 4:03:52 PM
	 * @param @return 
	 * @throws  
	*/
	String method() default "";
}
