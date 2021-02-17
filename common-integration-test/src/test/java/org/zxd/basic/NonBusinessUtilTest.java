package org.zxd.basic;
/*
 * Project: learn-in-action
 * DateTime: 2020/3/8 12:22
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */

import org.junit.Test;
import org.zxd.business.common.NonBusinessUtil;
import org.zxd.unified.response.utils.BusinessException;
import org.zxd.unified.response.utils.ErrorCode;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class NonBusinessUtilTest {

	@Test
	public void isPhoneValid() {
		assertThat(NonBusinessUtil.isPhoneValid("15505883728"),
				is(true));
		assertThat(NonBusinessUtil.isPhoneValid("1550588372q"),
				is(false));
	}

	@Test
	public void containsLetter() {
		assertThat(NonBusinessUtil.containsLetter("123"), is(false));
		assertThat(NonBusinessUtil.containsLetter("123a"), is(true));
	}

	@Test
	public void containsDigit() {
		assertThat(NonBusinessUtil.containsDigit("abc"), is(false));
		assertThat(NonBusinessUtil.containsDigit("ab1c"), is(true));
	}

	@Test
	public void containsSpecial() {
		assertThat(NonBusinessUtil.containsSpecial("123abc"), is(false));
		assertThat(NonBusinessUtil.containsSpecial("12ab!"), is(true));
	}

	@Test()
	public void checkNull() {
		ErrorCode errorCode = ErrorCode.ERROR;
		try {
			NonBusinessUtil.checkNull(null, errorCode);
		} catch (BusinessException e) {
			assertThat(e.getErrorCode(), is(errorCode));
		}
	}
}
