package org.zxd.dao.uc;

import org.apache.ibatis.annotations.Param;
import org.zxd.dao.entity.usercenter.UcUser;

/**
 * UcUser 数据库访问层
 */
public interface UcUserMapper {
	/**
	 * 新增用户
	 */
	int insert(UcUser record);

	/**
	 * 根据用户名查找用户
	 */
	UcUser findByUsername(@Param("username") String username);
}