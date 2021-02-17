package org.zxd.unified.response.utils;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = 4465236909123663899L;

	private ErrorCode errorCode;

	public BusinessException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public static final void throwOut(ErrorCode code) {
		throw new BusinessException(code);
	}

}
