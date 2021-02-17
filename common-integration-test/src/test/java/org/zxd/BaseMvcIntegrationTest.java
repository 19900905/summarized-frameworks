package org.zxd;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;

public abstract class BaseMvcIntegrationTest extends BaseIntegrationTest {
	@Autowired
	private WebApplicationContext webApplicationContext;

	@PostConstruct
	private void setup() {
		GenericWebApplicationContext register = (GenericWebApplicationContext) webApplicationContext;
		/**
		 * mockMvc 会通过实际的 DispatcherServlet 执行请求并生成响应
		 */
		MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		register.registerBean("mockMvc", MockMvc.class, () -> mockMvc);
	}
}
