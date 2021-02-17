package org.zxd.business.common;
/*
 * Project: learn-in-action
 * DateTime: 2020/1/13 14:49
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.zxd.business.usercenter.dto.UserDTO;
import org.zxd.unified.response.utils.ApiResponse;
import org.zxd.unified.response.utils.BusinessException;
import org.zxd.unified.response.utils.ErrorCode;

import static org.zxd.business.common.NonBusinessUtil.containsDigit;
import static org.zxd.business.common.NonBusinessUtil.containsLetter;
import static org.zxd.business.common.NonBusinessUtil.containsSpecial;

@Slf4j
public class BusinessUtil {
	/**
	 * 密码复杂度是否满足要求
	 *
	 * @param password
	 */
	public static void complexityMatch(String password) {
		if (Strings.isNullOrEmpty(password)) {
			BusinessException.throwOut(UserErrors.NULL_PASSWORD);
		}

		if (password.length() < 8 || password.length() > 16) {
			BusinessException.throwOut(UserErrors.SIMPLE_PASSWORD);
		}

		boolean match = containsSpecial(password) &&
				containsLetter(password) &&
				containsDigit(password);
		if (!match) {
			BusinessException.throwOut(UserErrors.SIMPLE_PASSWORD);
		}
	}

	public static String salt() {
		return RandomStringUtils.randomAlphanumeric(8);
	}

	/**
	 * 限流方法限制，参数列表和返回值必须和目标方法一致，并且追加一个 BlockException 参数用于接收异常
	 */
	public static ApiResponse handleLimited(UserDTO userDTO, BlockException e) {
		log.error("请求被限流 {}", Throwables.getStackTraceAsString(e));
		return ApiResponse.no(ErrorCode.RATE_LIMITED);
	}

}
