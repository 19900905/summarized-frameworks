package org.zxd.unified.response;

import com.google.common.base.Throwables;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zxd.unified.response.utils.ApiResponse;
import org.zxd.unified.response.utils.BusinessException;
import org.zxd.unified.response.utils.ErrorCode;
import org.zxd.unified.response.utils.NoPermissisonException;
import org.zxd.utils.ExceptionWrapper;

/*
 * Project: self-study-parent
 * DateTime: 17/08/2019 12:36
 * @Author: zhuxd
 * Version: v1.0
 * Desc: 全局异常处理
 */
@ConditionalOnProperty(prefix = "common.unified.response", name = "enabled", havingValue = "true")
@RestControllerAdvice
@Slf4j
public class GlobalExceptionConfig {

	private static final String MONITOR_PREFIX = "[business_error]";

	@Autowired
	private MessageSourceService messageSourceService;

	/**
	 * JSR 303 校验错误
	 *
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiResponse badRequest(MethodArgumentNotValidException exception) {
		String toast = exception.getBindingResult().getFieldErrors().stream()
				.map(fieldError -> {
					return messageSourceService.parseMessage(fieldError.getDefaultMessage());
				}).collect(Collectors.joining("\r\n"));

		return ApiResponse.badRequest(toast);
	}

	/**
	 * 无权限访问
	 *
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(NoPermissisonException.class)
	public ApiResponse forbidden(NoPermissisonException exception) {
		log.error("无权限 {}", Throwables.getStackTraceAsString(exception));
		return ApiResponse.forbidden(ErrorCode.No_Permissison);
	}

	/**
	 * 业务数据错误
	 *
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(BusinessException.class)
	public ApiResponse businessError(BusinessException exception) {
		monitorException(exception);
		return ApiResponse.dataError(exception.getErrorCode());
	}

	@ExceptionHandler(ExceptionWrapper.class)
	public ApiResponse businessError(ExceptionWrapper exception) {
		Throwable origin = exception.getOrigin();
		if (origin instanceof BusinessException) {
			return businessError((BusinessException) origin);
		} else {
			return no(origin);
		}
	}

	/**
	 * 服务器内部错误
	 *
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(Throwable.class)
	public ApiResponse no(Throwable exception) {
		monitorException(exception);
		return ApiResponse.no(ErrorCode.ERROR);
	}

	private void monitorException(Throwable exception) {
		log.error("{} {}", MONITOR_PREFIX, Throwables.getStackTraceAsString(exception));
	}

}
