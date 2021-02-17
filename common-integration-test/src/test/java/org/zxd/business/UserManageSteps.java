package org.zxd.business;
/*
 * Project: learn-in-action
 * DateTime: 2020/3/8 11:45
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;
import org.zxd.business.usercenter.UserWriteService;
import org.zxd.business.usercenter.dto.UserDTO;
import org.zxd.dao.emums.UserStatusEnum;
import org.zxd.dao.entity.usercenter.UcUser;
import org.zxd.manager.usercenter.UcUserManager;
import org.zxd.unified.response.utils.BusinessException;
import org.zxd.unified.response.utils.ErrorCode;

public class UserManageSteps {

	@Autowired
	private UserWriteService userWriteService;
	private UcUserManager ucUserManager;
	private ErrorCode errorCode;

	@Before("@existUserCheck")
	public void before() {
		ucUserManager = Mockito.mock(UcUserManager.class);
		ReflectionTestUtils.setField(userWriteService, "ucUserManager", ucUserManager);
	}

	@Given("^张三提交一个已存在的用户名称(.*?)和密码(.*?)，并且此用户有如下状态(.*?)$")
	public void 张三提交一个已存在的用户名称Username并且此用户有如下状态UserStatus(String username,
														   String password, UserStatusEnum userStatus) {
		UcUser ucUser = new UcUser();
		ucUser.setUsername(username);
		ucUser.setStatus(userStatus.getCode());

		Mockito.when(ucUserManager.findByUsername(username)).thenReturn(ucUser);

		UserDTO userDTO = UserDTO.builder()
				.username(username)
				.password(password)
				.conform(password).build();
		try {
			userWriteService.register(userDTO);
		} catch (BusinessException e) {
			errorCode = e.getErrorCode();
		}
	}

	@Then("^系统返回提示信息(.*?)$")
	public void 系统返回提示信息ErrorCode(String errorCode) {
		Assert.assertThat(this.errorCode, Is.is(ErrorCode.of(errorCode)));
	}
}
