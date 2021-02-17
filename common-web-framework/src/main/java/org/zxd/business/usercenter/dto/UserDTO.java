package org.zxd.business.usercenter.dto;
/*
 * Project: learn-in-action
 * DateTime: 2020/1/13 17:47
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zxd.business.common.UserErrors;

@Data
@ApiModel(value = "新增用户信息")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	@ApiModelProperty(value = "用户名称", required = true, example = "zxd")
	@NotBlank(message = UserErrors.NULL_USERNAME_CODE)
	@Pattern(regexp = "^\\d{11}$", message = UserErrors.USERNAME_INVALID_CODE)
	private String username;

	@ApiModelProperty(value = "用户密码", required = true)
	@NotBlank(message = UserErrors.NULL_PASSWORD_CODE)
	private String password;

	@ApiModelProperty(value = "确认密码", required = true)
	@NotBlank(message = UserErrors.NULL_CONFIRM_CODE)
	private String conform;

	/**
	 * 后端隐藏字段: 注册IP
	 */
	@ApiModelProperty(hidden = true)
	private Integer registerIp;
}