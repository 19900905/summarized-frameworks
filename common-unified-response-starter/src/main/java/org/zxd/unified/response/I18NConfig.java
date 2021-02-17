package org.zxd.unified.response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;

/*
 * Project: self-study-parent
 * DateTime: 18/08/2019 19:16
 * @author: zhuxd
 * Version: v1.0
 * Desc: 国际化配置
 */
public class I18NConfig {

	/**
	 * 国际化资源
	 *
	 * @return
	 */
	@Bean
	@Primary
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		// 属性文件的基本名称，对应于 resources/messages 文件夹下的 message*.properties 等文件
		messageSource.setBasename("classpath:messages/message");
		// 每 60 秒刷新一次
		messageSource.setCacheSeconds(60);
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

	/**
	 * 注册 Web 请求过滤器
	 *
	 * @return
	 */
	@Bean
	public FilterRegistrationBean webFilter() {
		final FilterRegistrationBean bean = new FilterRegistrationBean();
		bean.setFilter(new WebFilter());
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		bean.setName(WebFilter.NAME);
		return bean;
	}

	/**
	 * Web 端请求过滤器
	 */
	static class WebFilter implements Filter {

		public static String NAME = "LocateFilter";

		@Override
		public void init(FilterConfig filterConfig) throws ServletException {
		}

		@Override
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
			HttpServletRequest req = (HttpServletRequest) request;

			// 写入当前请求的 Locale
			FilterHolder.LOCALE.set(request.getLocale());

			try {
				chain.doFilter(request, response);
			} finally {
				FilterHolder.cleanAll();
			}
		}

		@Override
		public void destroy() {

		}
	}

	/**
	 * ThreadLocal 暂存器
	 */
	public enum FilterHolder {
		LOCALE;
		private static ThreadLocal<Map<FilterHolder, Object>> holders =
				ThreadLocal.withInitial(ConcurrentHashMap::new);

		public <T> void set(T t) {
			holders.get().put(this, t);
		}

		public <T> T get() {
			Object o = holders.get().get(this);
			if (o != null) {
				return (T) o;
			}

			return null;
		}

		public static void cleanAll() {
			holders.remove();
		}
	}
}
