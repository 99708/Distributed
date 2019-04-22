package org.xyq.cms.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 只要在controller上增加了这个方法的类，都需要权限控制
 * @author Dell
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthClass {
	/**
	 * 如果value为admin就表示这个类只能超级管理员访问
	 * 如果value为login就表示这个类中的方法，某些角色可以访问
	 * @return
	 */
	public String value() default "admin";
}
