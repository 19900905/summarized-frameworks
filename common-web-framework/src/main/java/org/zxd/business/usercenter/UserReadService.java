package org.zxd.business.usercenter;
/*
 * Project: learn-in-action
 * DateTime: 2020/1/13 17:59
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */

import org.zxd.unified.response.utils.ApiResponse;

public interface UserReadService {
	ApiResponse detail(String userId);
}
