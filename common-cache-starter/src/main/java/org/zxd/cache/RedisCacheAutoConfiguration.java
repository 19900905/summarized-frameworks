package org.zxd.cache;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.validation.annotation.Validated;
import org.zxd.distributed.lock.RedissonService;

/*
 * Project: learn-in-action
 * DateTime: 2020/4/6 12:53
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */

@ConditionalOnProperty(prefix = "common.distributed.cache", name = "enabled", havingValue = "true")
@EnableCaching
@Configuration
@EnableConfigurationProperties(CacheConfigs.class)
@Validated
@Slf4j
public class RedisCacheAutoConfiguration implements ApplicationRunner {

	/**
	 * Jackson 序列化器
	 */
	@Bean
	@Primary
	public Jackson2JsonRedisSerializer jackson2JsonRedisSerializer() {
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jackson2JsonRedisSerializer.setObjectMapper(om);

		return jackson2JsonRedisSerializer;
	}

	/**
	 * RedisTemplate 使用 Jackson 序列化对象
	 */
	@SuppressWarnings({"unchecked", "rawtypes"})
	@Bean
	@Primary
	public <K, V> RedisTemplate<K, V> redisTemplate(RedisConnectionFactory factory) {
		RedisTemplate<K, V> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);

		RedisSerializer<?> jackson2JsonRedisSerializer = jackson2JsonRedisSerializer();
		template.setKeySerializer(jackson2JsonRedisSerializer);
		// 设置值序列化器
		template.setValueSerializer(jackson2JsonRedisSerializer);
		// 设置 Hash 键序列化器
		template.setHashKeySerializer(jackson2JsonRedisSerializer);
		// 设置 Hash 值序列化器
		template.setHashValueSerializer(jackson2JsonRedisSerializer);

		template.afterPropertiesSet();
		return template;
	}

	/**
	 * Redis 缓存默认过期时间
	 */
	@Value("${spring.redis.cacheExpire: 60000}")
	@Min(value = 1000)
	private int mills;

	@Autowired
	private RedissonService redissonService;

	@Autowired
	private CacheConfigs cacheConfigs;

	/**
	 * 自定义缓存管理器
	 */
	@Bean
	@Primary
	public CacheManager cacheManager(RedisConnectionFactory factory) {
		// Redis 缓存配置
		RedisSerializationContext.SerializationPair serializationPair = RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer());
		RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(Duration.ofMillis(mills))
				.serializeKeysWith(serializationPair)
				.serializeValuesWith(serializationPair);
		// CacheWriter 配置
		RedisCacheWriter cacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(factory);

		// 初始化二级缓存
		List<CompositeCache> caches = cacheConfigs.getCacheConfigs().stream().map(config -> {
			Cache<Object, Object> caffeine =
					Caffeine.newBuilder().initialCapacity(config.getInitialCapacity())
							.maximumSize(config.getMaximumSize())
							.expireAfterAccess(30, TimeUnit.SECONDS)
							.softValues().build();
			// CaffeineCache 作为一级缓存
			CaffeineCache caffeineCache = new CaffeineCache(config.getCacheName(), caffeine);
			// RedisCache 作为二级缓存
			RedisCache redisCache = new RedisCache(config.getCacheName(), cacheWriter, cacheConfig);

			return new CompositeCache(config.getCacheName(), config.isAllowNullValues(), caffeineCache, redisCache, redissonService);
		}).collect(Collectors.toList());

		SimpleCacheManager cacheManager = new SimpleCacheManager();
		cacheManager.setCaches(caches);
		return cacheManager;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		log.info("RedisCacheAutoConfiguration enabled");
	}
}
