package org.zxd.dao.entity.usercenter;

import java.util.Date;
import lombok.Data;
import org.zxd.dao.entity.BaseEntity;

@Data
public class UcUser extends BaseEntity {
	/**
	 * 用户编号
	 */
	private String userId;

	/**
	 * 用户名称
	 */
	private String username;

	/**
	 * 加密盐值
	 */
	private String salt;

	/**
	 * 加密密码
	 */
	private String password;

	/**
	 * 用户状态
	 */
	private Integer status;

	/**
	 * 注册IP
	 */
	private Integer registerIp;

	/**
	 * 注册时间
	 */
	private Date registerTime;

	/**
	 * 最近登录IP
	 */
	private Integer lastLoginIp;

	/**
	 * 最近登录时间
	 */
	private Date lastLoginTime;

	/**
	 * 登录次数
	 */
	private Integer loginTimes;
}