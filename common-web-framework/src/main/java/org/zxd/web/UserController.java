package org.zxd.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zxd.business.usercenter.UserReadService;
import org.zxd.business.usercenter.UserWriteService;
import org.zxd.business.usercenter.dto.UserDTO;
import org.zxd.unified.response.utils.ApiResponse;
import org.zxd.utils.NetUtils;

/*
 * Project: learn-in-action
 * DateTime: 2019/12/5 22:03
 * Author: zhuxd
 * Version: v1.0
 * Desc: 用户操作
 */
@Api(tags = "用户中心 API 接口")
@RestController
@RequestMapping("/api/v1/ucUser")
public class UserController {
	@Autowired
	private UserWriteService userWriteService;
	@Autowired
	private UserReadService userReadService;

	@ApiOperation(value = "系统管理员-新增用户")
	@PostMapping
	public ApiResponse register(@RequestBody @Validated UserDTO userDTO, HttpServletRequest request) {
		int ip = NetUtils.ip2Int(NetUtils.getRemoteAddr(request));
		userDTO.setRegisterIp(ip);

		return userWriteService.register(userDTO);
	}

	@ApiOperation(value = "系统管理员-读取用户信息")
	@GetMapping("/{username}")
	public ApiResponse detail(@PathVariable("username") String username) {
		return userReadService.detail(username);
	}
}
