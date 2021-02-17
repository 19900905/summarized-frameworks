package org.zxd.dao.uc;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.zxd.RollbackIntegrationTest;
import org.zxd.dao.emums.UserStatusEnum;
import org.zxd.dao.emums.YesNoEnum;
import org.zxd.dao.entity.usercenter.UcUser;
import org.zxd.utils.AssertUtil;
import org.zxd.utils.CryptUtil;
import org.zxd.utils.DistributedId;
import org.zxd.utils.NetUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/*
 * Project: learn-in-action
 * DateTime: 2020/1/9 20:21
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */

public class UcUserMapperTest extends RollbackIntegrationTest {

	@Autowired
	private UcUserMapper ucUserMapper;

	@Test
	public void insert() {
		UcUser ucUser = new UcUser();
		ucUser.setUserId(DistributedId.nextId());
		ucUser.setUsername("15505883728");
		ucUser.setSalt(RandomStringUtils.randomAlphanumeric(8));
		ucUser.setPassword(CryptUtil.encryptPwd("1qaz@WSX", ucUser.getSalt()));
		ucUser.setStatus(UserStatusEnum.OK.getCode());
		ucUser.setRegisterIp(NetUtils.ip2Int("127.0.0.1"));
		ucUser.setRegisterTime(new Date());
		ucUser.setLastLoginIp(NetUtils.ip2Int("127.0.0.1"));
		ucUser.setLastLoginTime(new Date());
		ucUser.setLoginTimes(Integer.valueOf(1));
		ucUser.setCreateAt(new Date());
		ucUser.setUpdateAt(new Date());
		ucUser.setDeleted(YesNoEnum.NO.getCode());
		ucUser.setVersion(1);

		int insert = ucUserMapper.insert(ucUser);
		assertEquals(1, insert);

		UcUser existed = ucUserMapper.findByUsername(ucUser.getUsername());

		Field[] declaredFields = UcUser.class.getDeclaredFields();
		List<String> fields = Stream.of(declaredFields).map(Field::getName).collect(Collectors.toList());

		boolean equals = AssertUtil.equals(ucUser, existed, fields);
		assertTrue(equals);
	}
}