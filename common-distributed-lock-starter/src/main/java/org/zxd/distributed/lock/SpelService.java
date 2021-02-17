package org.zxd.distributed.lock;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/*
 * Project: learn-in-action
 * DateTime: 2020/4/6 18:54
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */
public class SpelService {
	private ExpressionParser expressionParser = new SpelExpressionParser();
	private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
	private ConcurrentMap<String, Expression> expressionCache = new ConcurrentHashMap<>(32);

	public Object directParse(JoinPoint joinPoint, String spel) {
		StandardEvaluationContext context = createContext(joinPoint);
		return parseValue(spel, context);
	}

	public Object parseValue(String key, StandardEvaluationContext context) {
		Expression expression = expressionCache.computeIfAbsent(key, cachekey -> {
			return expressionParser.parseExpression(cachekey);
		});
		return expression.getValue(context);
	}

	public StandardEvaluationContext createContext(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String[] parameterNames = parameterNameDiscoverer.getParameterNames(signature.getMethod());
		Object[] args = joinPoint.getArgs();
		StandardEvaluationContext context = new StandardEvaluationContext();
		for (int i = 0; i < parameterNames.length; i++) {
			context.setVariable(parameterNames[i], args[i]);
		}
		return context;
	}
}
