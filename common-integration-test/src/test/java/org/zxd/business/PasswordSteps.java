package org.zxd.business;
/*
 * Project: learn-in-action
 * DateTime: 2020/3/3 21:58
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.zxd.business.common.BusinessUtil;
import org.zxd.unified.response.utils.BusinessException;
import org.zxd.unified.response.utils.ErrorCode;

public class PasswordSteps {

	private ErrorCode errorCode;

	@Given("^系统管理员新增用户输入此密码(.*)时$")
	public void 系统管理员新增用户输入此密码Password时(String password) {
		try {
			BusinessUtil.complexityMatch(password);
			errorCode = ErrorCode.SUCCESS;
		} catch (BusinessException e) {
			errorCode = e.getErrorCode();
		}
	}

	@Then("^系统需要返回对应的提示信息(.*)$")
	public void 系统需要返回对应的提示信息Message(String errorCode) {
		Assert.assertThat(this.errorCode, Is.is(ErrorCode.of(errorCode)));
	}
}
