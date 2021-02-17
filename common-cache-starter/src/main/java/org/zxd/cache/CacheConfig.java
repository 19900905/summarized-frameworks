package org.zxd.cache;

import java.util.concurrent.TimeUnit;
import lombok.Data;

/**
 * 缓存配置类
 *
 * @author zhuxd
 */
@Data
public class CacheConfig {
	/**
	 * 缓存名称
	 */
	private String cacheName;
	/**
	 * CaffeineCache 初始化容量
	 */
	private int initialCapacity;
	/**
	 * CaffeineCache 最大容量
	 */
	private int maximumSize;
	/**
	 * CaffeineCache 过期时间
	 */
	private int expire;
	/**
	 * CaffeineCache 过期时间单位
	 */
	private TimeUnit timeUnit;
	/**
	 * CaffeineCache 过期类型
	 */
	private ExpireType expireType;
	/**
	 * 是否允许缓存 null 值
	 */
	private boolean allowNullValues;

	static enum ExpireType {
		/**
		 * 最后一次写入或访问后经过固定时间过期
		 */
		EXPIRE_AFTER_ACCESS,
		/**
		 * 最后一次写入后经过固定时间过期
		 */
		EXPIRE_AFTER_WRITE,
		/**
		 * 创建缓存或者最近一次更新缓存后经过固定的时间间隔，刷新缓存
		 */
		REFRESH_AFTER_WRITE
	}
}
