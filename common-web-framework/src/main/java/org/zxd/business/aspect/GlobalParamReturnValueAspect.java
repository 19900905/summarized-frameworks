package org.zxd.business.aspect;

import com.alibaba.fastjson.JSON;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/*
 * Project: self-study-parent
 * DateTime: 18/08/2019 17:18
 * @author: zhuxd
 * Version: v1.0
 * Desc: 请求链路日志切面
 */
@Profile("test")
@Aspect
@Component
@Slf4j
public class GlobalParamReturnValueAspect {

	@Pointcut("execution(* org.zxd.business..*(..))")
	public void log() {
	}

	@Around("log()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			final long start = System.nanoTime();
			final Object result = joinPoint.proceed();
			final long executeMillions = (System.nanoTime() - start) / 10 ^ 6;

			final String className = joinPoint.getTarget().getClass().getName();
			final String methodName = joinPoint.getSignature().getName();
			List<Object> args = Stream.of(joinPoint.getArgs()).filter(arg -> !(arg instanceof HttpServletRequest ||
					arg instanceof HttpSession)).collect(Collectors.toList());
			log.info("{}#{} args:{} result:{}", className, methodName, JSON.toJSONString(args), result);
			return result;
		} catch (final Throwable e) {
			log.error("请求异常", e);
			throw e;
		}
	}
}
