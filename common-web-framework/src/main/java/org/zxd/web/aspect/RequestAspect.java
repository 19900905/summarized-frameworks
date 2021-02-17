package org.zxd.web.aspect;

import com.alibaba.fastjson.JSON;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.zxd.utils.ApplicationUtil;
import org.zxd.utils.NetUtils;

/**
 * 控制器入参切面
 */
@Aspect
@Component
@Slf4j
public class RequestAspect {

	/**
	 * 第一个 .* 标识类名称
	 * 第二个 .* 标识方法名称
	 * (..) 标识方法参数
	 */
	@Pointcut("execution(public * org.zxd.web.*.*(..))")
	public void requestLog() {
	}

	@Around("requestLog()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		try {
			final HttpServletRequest request = ApplicationUtil.currentRequest();
			final String requestURI = request.getRequestURI();
			final String method = request.getMethod();
			final String remoteAddr = NetUtils.getRemoteAddr(request);
			final int contentLength = request.getContentLength();
			final long start = System.nanoTime();
			final Object result = joinPoint.proceed();
			final long executeMillions = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
			final String className = joinPoint.getTarget().getClass().getName();
			final String methodName = joinPoint.getSignature().getName();
			// 需要排除 HTTP 对象，否则 json 序列化会报错
			List<Object> args = Stream.of(joinPoint.getArgs()).filter(arg -> !(arg instanceof HttpServletRequest ||
					arg instanceof HttpSession)).collect(Collectors.toList());
			RequestLog requestLog = RequestLog.of(requestURI, method, remoteAddr, contentLength,
					executeMillions, className, methodName, className + "#" + methodName, JSON.toJSONString(args));
			log.info("requestLog {}", requestLog);
			return result;
		} catch (final Throwable e) {
			log.error("请求异常", e);
			throw e;
		}
	}

}
