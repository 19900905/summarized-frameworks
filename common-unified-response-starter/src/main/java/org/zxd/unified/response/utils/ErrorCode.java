package org.zxd.unified.response.utils;

import lombok.Getter;
import lombok.Value;

/*
 * Project: self-study-parent
 * DateTime: 10/08/2019 18:32
 * @Author: zhuxd
 * Version: v1.0
 * Desc: 系统错误码
 */
@Getter
@Value(staticConstructor = "of")
public class ErrorCode {
	/**
	 * 操作成功
	 */
	public static ErrorCode SUCCESS = new ErrorCode("success");

	/**
	 * 操作失败
	 */
	public static ErrorCode ERROR = new ErrorCode("failed");

	/**
	 * 被限流
	 */
	public static ErrorCode RATE_LIMITED = new ErrorCode("rateLimited");

	/**
	 * 无权限
	 */
	public static ErrorCode No_Permissison = new ErrorCode("noPermissison");

	/**
	 * 请求方法或参数错误
	 */
	public static ErrorCode REQUEST_ERROR = new ErrorCode("request.error");

	private String code;
}
