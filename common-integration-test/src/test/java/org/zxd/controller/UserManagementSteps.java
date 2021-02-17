package org.zxd.controller;
/*
 * Project: learn-in-action
 * DateTime: 2020/3/1 19:35
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */

import com.alibaba.fastjson.JSON;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.zxd.BaseMvcIntegrationTest;
import org.zxd.business.usercenter.dto.UserDTO;
import org.zxd.utils.ApplicationUtil;

public class UserManagementSteps extends BaseMvcIntegrationTest {

	@Given("^张三拥有系统管理员角色$")
	public void 张三拥有系统管理员角色() {
	}

	@When("^当他在新增用户页面输入有效用户信息并提交表单时$")
	public void 当他在新增用户页面输入以下用户信息并提交表单时(UserDTO userDTO) throws Exception {
		MockMvc mockMvc = ApplicationUtil.beanByTye(MockMvc.class);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/ucUser/")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(JSON.toJSONString(userDTO)))
				.andDo(MockMvcResultHandlers.log())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200))
				.andExpect(MockMvcResultMatchers.jsonPath("$.body").isNotEmpty());
	}

	@Then("^系统新增用户 (.*) 成功$")
	public void 系统新增此用户并返回用户id(String username) throws Exception {
		MockMvc mockMvc = ApplicationUtil.beanByTye(MockMvc.class);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/ucUser/{username}", username)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.log())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(200))
				.andExpect(MockMvcResultMatchers.jsonPath("$.body.username").value(username));
	}

}
