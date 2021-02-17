package org.zxd.session;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 开启分布式会话，并设置会话超时时间为 30 天
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600 * 24 * 30)
public class RedisSessionConfig {
}
