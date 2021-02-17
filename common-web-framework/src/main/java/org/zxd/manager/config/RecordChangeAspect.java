package org.zxd.manager.config;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.zxd.distributed.lock.SpelService;

/*
 * Project: learn-in-action
 * DateTime: 2020/3/31 19:14
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */
@Aspect
@Slf4j
@Component
@ConditionalOnBean(SpelService.class)
public class RecordChangeAspect {
	@Autowired
	private SpelService spelService;

	/**
	 * 记录目标数据变更
	 */
	@After(value = "@annotation(change)", argNames = "joinPoint,change")
	public void after(JoinPoint joinPoint, RecordChange change) {
		CompletableFuture.runAsync(() -> {
			try {
				recordDataChange(joinPoint, change);
			} catch (Exception e) {
				log.error(Throwables.getStackTraceAsString(e));
			}
		});
	}

	private void recordDataChange(JoinPoint joinPoint, RecordChange change) {
		long start = System.nanoTime();
		String type = change.type();
		String key = change.key();
		String changeData = change.changeData();

		StandardEvaluationContext context = spelService.createContext(joinPoint);
		Object keyVal = spelService.parseValue(key, context);
		Object dataVal = spelService.parseValue(changeData, context);
		log.info("{} {} {} modifyDate: {} spend: {}", type, keyVal, JSON.toJSONString(dataVal),
				new Date(), TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start));
	}

}
