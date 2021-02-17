package org.zxd.distributed.lock;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class RedissonService {

	@Autowired
	private RedissonClient redissonClient;

	public <T> T invokeWithLock(String lockKey, Supplier<T> supplier) {
		RLock clientLock = redissonClient.getLock(lockKey);
		try {
			clientLock.tryLock(5, 30, TimeUnit.SECONDS);
			return supplier.get();
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		} finally {
			if (clientLock.isHeldByCurrentThread()) {
				clientLock.unlock();
			}
		}
	}

}
