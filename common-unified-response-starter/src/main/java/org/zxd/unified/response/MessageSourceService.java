package org.zxd.unified.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/*
 * Project: learn-in-action
 * DateTime: 2020/1/15 20:17
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */
public class MessageSourceService {
	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;

	public String parseMessage(String code) {
		return messageSource.getMessage(code, new Object[0], I18NConfig.FilterHolder.LOCALE.get());
	}
}
