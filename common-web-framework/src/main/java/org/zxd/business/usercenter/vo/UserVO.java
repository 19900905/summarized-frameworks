package org.zxd.business.usercenter.vo;
/*
 * Project: learn-in-action
 * DateTime: 2020/1/16 22:46
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */

import java.util.Date;
import lombok.Data;

@Data
public class UserVO {
	/**
	 * 用户标识
	 */
	private String userId;

	/**
	 * 用户名，首选手机号
	 */
	private String username;

	/**
	 * 用户状态
	 */
	private Integer status;

	/**
	 * 注册IP地址
	 */
	private String registerip;

	/**
	 * 注册时间
	 */
	private Date registerTime;

	/**
	 * 上次登录IP地址
	 */
	private String lastLoginIp;

	/**
	 * 上次登录时间
	 */
	private Date lastLoginTime;

	/**
	 * 登录次数
	 */
	private Integer loginTimes;
}
