package org.zxd.manager.usercenter;
/*
 * Project: learn-in-action
 * DateTime: 2020/1/13 17:33
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */

import org.zxd.dao.entity.usercenter.UcUser;

public interface UcUserManager {
	/**
	 * 新增用户并返回 userId
	 *
	 * @param record
	 * @return 用户唯一标识
	 */
	UcUser insert(UcUser record);

	/**
	 * 根据用户名称查询
	 */
	UcUser findByUsername(String username);
}
