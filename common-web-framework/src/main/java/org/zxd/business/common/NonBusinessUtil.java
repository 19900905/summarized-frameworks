package org.zxd.business.common;
/*
 * Project: learn-in-action
 * DateTime: 2020/3/8 11:22
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */

import java.util.regex.Pattern;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.zxd.unified.response.utils.BusinessException;
import org.zxd.unified.response.utils.ErrorCode;

import static java.util.Objects.isNull;

@UtilityClass
public class NonBusinessUtil {
	private static final Pattern LETTER = Pattern.compile("[a-zA-z]");
	private static final Pattern DIGIT = Pattern.compile("[0-9]");
	private static final Pattern SPECIAL = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
	private static final Pattern PHONE = Pattern.compile("^\\d{11}$");

	/**
	 * 校验手机号是否为11位数字
	 *
	 * @param cellphone
	 * @return
	 */
	public static boolean isPhoneValid(String cellphone) {
		return StringUtils.isNotBlank(cellphone) && PHONE.matcher(cellphone).matches();
	}

	public static boolean containsLetter(String word) {
		return matches(LETTER, word);
	}

	public static boolean containsDigit(String word) {
		return matches(DIGIT, word);
	}

	public static boolean containsSpecial(String word) {
		return matches(SPECIAL, word);
	}

	private static boolean matches(Pattern pattern, String password) {
		return pattern.matcher(password).find();
	}

	public static <T> void checkNull(T t, ErrorCode errorCode) {
		if (isNull(t)) {
			BusinessException.throwOut(errorCode);
		}
	}

}
