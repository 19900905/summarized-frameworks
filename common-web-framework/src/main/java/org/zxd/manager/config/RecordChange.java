package org.zxd.manager.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * Project: learn-in-action
 * DateTime: 2020/3/31 19:12
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface RecordChange {
	/**
	 * 变更模型
	 */
	String type();

	/**
	 * 目标对象唯一标识
	 */
	String key();

	/**
	 * 变更的数据
	 */
	String changeData();
}
