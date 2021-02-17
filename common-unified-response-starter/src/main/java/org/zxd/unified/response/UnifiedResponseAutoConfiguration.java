package org.zxd.unified.response;
/*
 * Project: idea-projects
 * DateTime: 2021/2/10 11:11
 * @author: 竺旭东
 * Version: v1.0
 * Desc: TODO
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ConditionalOnProperty(prefix = "common.unified.response", name = "enabled", havingValue = "true")
@Import({UnifiedResponseConfig.class, MessageSourceService.class, I18NConfig.class})
@Configuration
@Slf4j
public class UnifiedResponseAutoConfiguration implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("UnifiedResponseAutoConfiguration enabled");
	}
}
