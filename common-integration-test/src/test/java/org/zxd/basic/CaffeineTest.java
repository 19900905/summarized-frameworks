package org.zxd.basic;
/*
 * Project: learn-in-action
 * DateTime: 2020/4/6 16:15
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import java.util.concurrent.TimeUnit;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CaffeineTest {

	@Test
	public void quick() {
		// 无加载器的缓存
		Cache<Object, Object> cache = Caffeine.newBuilder()
				.initialCapacity(8)
				.maximumSize(1024)
				.expireAfterAccess(1, TimeUnit.MINUTES)
				.weakValues().build();
		String key = "key";
		cache.put(key, "value");
		Object present = cache.getIfPresent(key);
		Assertions.assertNotNull(present);

		// 带有加载器的缓存
		LoadingCache<String, String> loadingCache = Caffeine.newBuilder()
				.initialCapacity(8)
				.maximumSize(1024)
				.expireAfterAccess(1, TimeUnit.MINUTES)
				.weakValues().build(new CacheLoader<String, String>() {
					@Nullable
					@Override
					public String load(@NonNull String key) throws Exception {
						return key + "load from db";
					}
				});
		String val = loadingCache.get(key);
		Assertions.assertEquals(val, key + "load from db");
	}
}
