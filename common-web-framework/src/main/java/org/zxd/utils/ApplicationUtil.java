package org.zxd.utils;
/*
 * Project: learn-in-action
 * DateTime: 2020/1/10 11:19
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class ApplicationUtil implements ApplicationContextAware {
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
	}

	public static <T> T beanByTye(Class<T> requireClz) {
		return context.getBean(requireClz);
	}

	public static ApplicationContext getContext() {
		return context;
	}

	public static HttpServletRequest currentRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
}
