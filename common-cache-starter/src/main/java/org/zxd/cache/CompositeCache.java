package org.zxd.cache;

import java.util.concurrent.Callable;
import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractValueAdaptingCache;
import org.zxd.distributed.lock.RedissonService;

import static java.util.Objects.nonNull;

/*
 * Project: learn-in-action
 * DateTime: 2020/4/6 12:46
 * @author: zhuxd
 * Version: v1.0
 * Desc: 二级缓存
 */
public class CompositeCache extends AbstractValueAdaptingCache {
	/**
	 * 缓存名称
	 */
	private String cacheName;
	/**
	 * 一级缓存
	 */
	private Cache first;
	/**
	 * 二级缓存
	 */
	private Cache second;
	/**
	 * 分布式锁
	 */
	private RedissonService redissonService;

	public CompositeCache(String cacheName, boolean allowNullValues, Cache first, Cache second, RedissonService redissonService) {
		super(allowNullValues);
		this.cacheName = cacheName;
		this.first = first;
		this.second = second;
		this.redissonService = redissonService;
	}

	@Override
	protected Object lookup(Object key) {
		ValueWrapper wrapper = first.get(key);
		if (nonNull(wrapper)) {
			return wrapper.get();
		}

		wrapper = second.get(key);
		if (nonNull(wrapper)) {
			tryPutCache(key, wrapper.get());
			return wrapper.get();
		}

		return null;
	}

	@Override
	public String getName() {
		return cacheName;
	}

	@Override
	public Object getNativeCache() {
		return first.getNativeCache();
	}

	/**
	 * 缓存加载器默认使用本地缓存
	 */
	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		return (T) redissonService.invokeWithLock(String.format("cache:%s:%s", cacheName, String.valueOf(key)), () -> {
			try {
				// 从 Redis 缓存中读取到了数据
				ValueWrapper wrapper = get(key);
				if (nonNull(wrapper)) {
					tryPutCache(key, wrapper.get());
					return wrapper.get();
				}

				T value = valueLoader.call();
				tryPutCache(key, value);
				return value;
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		});
	}

	private <T> void tryPutCache(Object key, T value) {
		if (isAllowNullValues() || nonNull(value)) {
			putInCache(key, value);
		}
	}

	private void putInCache(Object key, Object value) {
		second.putIfAbsent(key, value);
		first.putIfAbsent(key, value);
	}

	@Override
	public void put(Object key, Object value) {
		tryPutCache(key, value);
	}

	@Override
	public void evict(Object key) {
		second.evict(key);
		first.evict(key);
	}

	@Override
	public void clear() {
		second.clear();
		first.clear();
	}
}
