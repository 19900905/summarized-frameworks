package org.zxd.dao.emums;
/*
 * Project: learn-in-action
 * DateTime: 2020/1/13 15:34
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否枚举
 */
@Getter
@AllArgsConstructor
public enum YesNoEnum {
	/**
	 * 是
	 */
	YES(1),
	/**
	 * 否
	 */
	NO(0);
	private Integer code;
}
