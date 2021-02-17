package org.zxd.utils;

import lombok.Value;

/*
 * Project: learn-in-action
 * DateTime: 2020/4/11 13:08
 * @author: zhuxd
 * Version: v1.0
 */
@Value(staticConstructor = "of")
public class ExceptionWrapper extends RuntimeException {
	private Throwable origin;

	public static Throwable origin(Throwable throwable) {
		while (throwable instanceof ExceptionWrapper) {
			origin(((ExceptionWrapper) throwable).origin);
		}
		return throwable;
	}
}
