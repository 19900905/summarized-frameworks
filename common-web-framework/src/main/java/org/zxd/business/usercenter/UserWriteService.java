package org.zxd.business.usercenter;
/*
 * Project: learn-in-action
 * DateTime: 2019/12/5 22:20
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */

import org.zxd.business.usercenter.dto.UserDTO;
import org.zxd.unified.response.utils.ApiResponse;

public interface UserWriteService {

	/**
	 * 注册用户
	 *
	 * @param userDTO
	 * @return
	 */
	ApiResponse register(UserDTO userDTO);
}
