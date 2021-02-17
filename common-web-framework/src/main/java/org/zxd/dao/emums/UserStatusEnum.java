package org.zxd.dao.emums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户状态枚举
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnum {
	/**
	 * 可用
	 */
	OK(1),
	/**
	 * 被锁定
	 */
	LOCKED(2),
	/**
	 * 无效
	 */
	INVALID(3);
	private Integer code;

	/**
	 * Is locked boolean.
	 *
	 * @param status the status
	 * @return the boolean
	 */
	public static boolean isLocked(Integer status) {
		return LOCKED.getCode().equals(status);
	}

	/**
	 * Is invalid boolean.
	 *
	 * @param status the status
	 * @return the boolean
	 */
	public static boolean isInvalid(Integer status) {
		return INVALID.getCode().equals(status);
	}
}
