package org.zxd.business.common;
/*
 * Project: learn-in-action
 * DateTime: 2020/1/14 17:09
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */

import org.zxd.unified.response.utils.ErrorCode;

public interface UserErrors {
	String NULL_USERNAME_CODE = "user.1001";
	String NULL_PASSWORD_CODE = "user.1002";
	String NULL_CONFIRM_CODE = "user.1003";
	String USERNAME_INVALID_CODE = "user.1008";

	/**
	 * 用户名为空
	 */
	ErrorCode NULL_USERNAME = ErrorCode.of(NULL_USERNAME_CODE);

	/**
	 * 密码为空
	 */
	ErrorCode NULL_PASSWORD = ErrorCode.of(NULL_PASSWORD_CODE);

	/**
	 * 确认密码为空
	 */
	ErrorCode NULL_CONFIRM = ErrorCode.of(NULL_CONFIRM_CODE);

	/**
	 * 密码和确认密码不一致
	 */
	ErrorCode NOT_EQUALS_PASSWORD = ErrorCode.of("user.1004");

	/**
	 * 密码太过简单
	 */
	ErrorCode SIMPLE_PASSWORD = ErrorCode.of("user.1005");

	/**
	 * 用户名已经存在
	 */
	ErrorCode USERNAME_EXISTED = ErrorCode.of("user.1006");

	/**
	 * 用户ID非法
	 */
	ErrorCode USERID_INVALID = ErrorCode.of("user.1007");

	/**
	 * 用户名格式错误，只支持手机号
	 */
	ErrorCode USERNAME_INVALID = ErrorCode.of(USERNAME_INVALID_CODE);

	/**
	 * 此用户被锁定
	 */
	ErrorCode USER_LOCKED = ErrorCode.of("user.1009");

	/**
	 * 此用户无效
	 */
	ErrorCode USER_INVALID = ErrorCode.of("user.1010");

	/**
	 * 用户名不存在
	 */
	ErrorCode USERNAME_NOT_EXISTED = ErrorCode.of("user.1011");
}
