package org.zxd.distributed.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/*
 * Project: idea-projects
 * DateTime: 2021/2/10 10:24
 * @author: 竺旭东
 * Version: v1.0
 * Desc: TODO
 */
@ConditionalOnProperty(prefix = "common.distributed.lock", name = "enabled", havingValue = "true")
@Configuration
@Import({DistributedAspect.class, RedissonService.class, SpelService.class})
@Slf4j
public class DistributedAspectAutoConfiguration implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("DistributedAspectAutoConfiguration enabled");
	}
}
