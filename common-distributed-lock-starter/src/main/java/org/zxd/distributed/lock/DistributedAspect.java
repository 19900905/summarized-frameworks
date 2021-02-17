package org.zxd.distributed.lock;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.zxd.utils.ExceptionWrapper;

/*
 * Project: learn-in-action
 * DateTime: 2020/4/6 18:49
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */
@Aspect
public class DistributedAspect {
	@Autowired
	private SpelService spelService;

	@Autowired
	private RedissonService redissonService;

	@Around(value = "@annotation(doInLock)", argNames = "joinPoint,doInLock")
	public Object around(ProceedingJoinPoint joinPoint, DoInLock doInLock) {
		Object lockKey = spelService.directParse(joinPoint, doInLock.keySpel());
		return redissonService.invokeWithLock(String.valueOf(lockKey), () -> {
			try {
				return joinPoint.proceed();
			} catch (Throwable throwable) {
				throw ExceptionWrapper.of(throwable);
			}
		});
	}
}
