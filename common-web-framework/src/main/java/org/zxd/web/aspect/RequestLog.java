package org.zxd.web.aspect;

import lombok.Value;

/**
 * 请求日志
 */
@Value(staticConstructor = "of")
public class RequestLog {
	/**
	 * 请求地址
	 */
	private String requestURI;
	/**
	 * 请求方法
	 */
	private String method;
	/**
	 * 客户端地址
	 */
	private String remoteAddr;
	/**
	 * 请求体长度
	 */
	private int contentLength;
	/**
	 * 目标方法执行时间
	 */
	private long executeMillions;
	/**
	 * 目标类名
	 */
	private String className;
	/**
	 * 目标方法名
	 */
	private String methodName;
	/**
	 * 方法导航
	 */
	private String navigate;
	/**
	 * 参数字符串
	 */
	private String args;
}
