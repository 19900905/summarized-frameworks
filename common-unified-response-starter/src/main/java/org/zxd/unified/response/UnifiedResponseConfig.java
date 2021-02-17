package org.zxd.unified.response;

import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.handler.HandlerExceptionResolverComposite;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.zxd.unified.response.utils.ApiResponse;

/*
 * Project: self-study-parent
 * DateTime: 17/08/2019 12:20
 * @Author: zhuxd
 * Version: v1.0
 * Desc: 统一输出响应
 */
@Slf4j
public class UnifiedResponseConfig {

	@Autowired
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
	@Autowired
	private MessageSourceService messageSourceService;
	@Autowired
	private HandlerExceptionResolver handlerExceptionResolver;

	/**
	 * 自定义 RequestMappingHandlerAdapter
	 */
	@PostConstruct
	public void customize() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		List<HttpMessageConverter<?>> converters = Lists.newArrayList(converter);
		// 追加内置的 HttpMessageConverters
		converters.addAll(requestMappingHandlerAdapter.getMessageConverters());

		List<Object> responseAdvices = Lists.newArrayList(new UnifiedResponseAdvice(messageSourceService));

		UnifiedResponseReturnValueHandler customHandler = new UnifiedResponseReturnValueHandler(converters, responseAdvices);

		// 读取内置的 ReturnValueHandlers
		List<HandlerMethodReturnValueHandler> returnValueHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
		List<HandlerMethodReturnValueHandler> customHandlers = Lists.newArrayListWithCapacity(returnValueHandlers.size() + 1);
		// 添加自定义 ReturnValueHandler【优先级最高，避免不必要的判断】
		customHandlers.add(customHandler);
		// 追加内置的 ReturnValueHandlers
		customHandlers.addAll(returnValueHandlers);
		// 重置 RequestMappingHandlerAdapter 的 HandlerMethodReturnValueHandlers
		requestMappingHandlerAdapter.setReturnValueHandlers(customHandlers);

		HandlerExceptionResolverComposite composite = (HandlerExceptionResolverComposite) handlerExceptionResolver;
		ExceptionHandlerExceptionResolver exceptionResolver = (ExceptionHandlerExceptionResolver) composite.getExceptionResolvers().get(0);
		exceptionResolver.setReturnValueHandlers(customHandlers);
	}

	/**
	 * 统一响应 ReturnValueHandler
	 */
	static class UnifiedResponseReturnValueHandler extends RequestResponseBodyMethodProcessor {

		public UnifiedResponseReturnValueHandler(List<HttpMessageConverter<?>> converters, List<Object> requestResponseBodyAdvice) {
			super(converters, requestResponseBodyAdvice);
		}

	}

	/**
	 * 统一响应 ResponseBodyAdvice
	 */
	@Value(staticConstructor = "of")
	static class UnifiedResponseAdvice implements ResponseBodyAdvice<Object> {
		private MessageSourceService messageSourceService;

		/**
		 * 转换器必须是 MappingJackson2HttpMessageConverter 及其子类
		 *
		 * @param returnType
		 * @param converterType
		 * @return
		 */
		@Override
		public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
			return MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
		}

		/**
		 * 将目标结果转换为 CommonResponse
		 *
		 * @param body                  实际结果
		 * @param returnType            实际返回类型
		 * @param selectedContentType   匹配的 MediaType
		 * @param selectedConverterType 匹配的 HttpMessageConverter
		 * @param request               请求
		 * @param response              响应
		 * @return
		 */
		@Override
		public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
			// 如果是 json 响应类型
			if (MediaType.APPLICATION_JSON.equals(selectedContentType)
					|| MediaType.APPLICATION_JSON_UTF8.equals(selectedContentType)) {
				ApiResponse resp;
				if (body instanceof ApiResponse) {
					resp = (ApiResponse) body;
				} else {
					resp = ApiResponse.yes(body);
				}

				// 解析国际化 code
				if (StringUtils.isNotBlank(resp.getCode())) {
					resp.setMessage(messageSourceService.parseMessage(resp.getCode()));
				}

				return resp;
			}

			return body;
		}
	}

}
