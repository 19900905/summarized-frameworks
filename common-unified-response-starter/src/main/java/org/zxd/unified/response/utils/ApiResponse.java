package org.zxd.unified.response.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/*
 * Project: self-study-parent
 * DateTime: 17/08/2019 12:23
 * @Author: zhuxd
 * Version: v1.0
 * Desc: 统一返回结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
	/**
	 * 状态码
	 */
	private int status;

	/**
	 * 错误编码，用于国际化
	 */
	private String code;

	/**
	 * 错误消息，用于 toast 提示
	 */
	private String message;

	/**
	 * 成功时的响应体
	 */
	private T body;

	/**
	 * 通用成功
	 *
	 * @param body
	 * @param <T>
	 * @return
	 */
	public static final <T> ApiResponse yes(T body) {
		return successResponse(body);
	}

	/**
	 * 通用失败
	 *
	 * @param code
	 * @param <T>
	 * @return
	 */
	public static final <T> ApiResponse no(ErrorCode code) {
		return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ErrorCode.ERROR);
	}

	/**
	 * 请求参数校验失败
	 *
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T> ApiResponse badRequest(T data) {
		return buildResponse(HttpStatus.BAD_REQUEST.value(), ErrorCode.REQUEST_ERROR, data);
	}

	/**
	 * 通用无权限
	 *
	 * @param code
	 * @param <T>
	 * @return
	 */
	public static final <T> ApiResponse forbidden(ErrorCode code) {
		return errorResponse(HttpStatus.FORBIDDEN.value(), code);
	}

	/**
	 * 请求数据异常
	 *
	 * @param code
	 * @param <T>
	 * @return
	 */
	public static final <T> ApiResponse dataError(ErrorCode code) {
		return errorResponse(HttpStatus.PRECONDITION_FAILED.value(), code);
	}

	private static <T> ApiResponse successResponse(T data) {
		return buildResponse(HttpStatus.OK.value(), ErrorCode.SUCCESS, data);
	}

	private static ApiResponse errorResponse(int status, ErrorCode code) {
		return buildResponse(status, code, null);
	}

	private static <T> ApiResponse buildResponse(int status, ErrorCode code, T data) {
		return ApiResponse.builder()
				.status(status)
				.code(code.getCode())
				.body(data)
				.build();
	}

}
