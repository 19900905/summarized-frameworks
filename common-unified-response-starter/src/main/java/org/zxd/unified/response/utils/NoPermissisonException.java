package org.zxd.unified.response.utils;

/**
 * Project: self-study-parent
 * DateTime: 17/08/2019 12:46
 *
 * @author: zhuxd
 * Version: v1.0
 * Desc: 无权限异常
 */
public class NoPermissisonException extends RuntimeException {
	private static final long serialVersionUID = 4465236909123663899L;

	public NoPermissisonException(String message) {
		super(message);
	}

	public static final void throwOut(String code) {
		throw new NoPermissisonException(code);
	}
}
