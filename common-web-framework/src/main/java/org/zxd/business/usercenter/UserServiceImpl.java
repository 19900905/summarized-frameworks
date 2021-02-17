package org.zxd.business.usercenter;
/*
 * Project: learn-in-action
 * DateTime: 2020/1/13 17:55
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import java.util.Date;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zxd.business.common.BusinessUtil;
import org.zxd.business.common.UserErrors;
import org.zxd.business.usercenter.dto.UserDTO;
import org.zxd.business.usercenter.vo.UserVO;
import org.zxd.dao.emums.UserStatusEnum;
import org.zxd.dao.entity.BaseEntity;
import org.zxd.dao.entity.usercenter.UcUser;
import org.zxd.distributed.lock.DoInLock;
import org.zxd.manager.usercenter.UcUserManager;
import org.zxd.unified.response.utils.ApiResponse;
import org.zxd.unified.response.utils.BusinessException;
import org.zxd.utils.CryptUtil;
import org.zxd.utils.NetUtils;
import org.zxd.utils.TransformUtil;

import static java.util.Objects.isNull;

@Service
@Slf4j
public class UserServiceImpl implements UserWriteService, UserReadService {

	@Autowired
	private UcUserManager ucUserManager;

	/**
	 * 注册用户
	 *
	 * @param userDTO
	 * @return
	 */
	@Override
	@SentinelResource(value = "user-register", blockHandler = "handleLimited",
			blockHandlerClass = BusinessUtil.class)
	@DoInLock(keySpel = "#userDTO.username")
	public ApiResponse register(UserDTO userDTO) {
		BusinessUtil.complexityMatch(userDTO.getPassword());
		if (!Objects.equals(userDTO.getPassword(), userDTO.getConform())) {
			BusinessException.throwOut(UserErrors.NOT_EQUALS_PASSWORD);
		}

		UcUser exist = ucUserManager.findByUsername(userDTO.getUsername());
		if (exist != null) {
			if (UserStatusEnum.isLocked(exist.getStatus())) {
				BusinessException.throwOut(UserErrors.USER_LOCKED);
			}

			if (UserStatusEnum.isInvalid(exist.getStatus())) {
				BusinessException.throwOut(UserErrors.USER_INVALID);
			}

			BusinessException.throwOut(UserErrors.USERNAME_EXISTED);
		}

		UcUser ucUser = ucUserManager.insert(toUcUser(userDTO));
		return ApiResponse.yes(ucUser.getUserId());
	}

	private UcUser toUcUser(UserDTO userDTO) {
		UcUser ucUser = TransformUtil.copyProperties(userDTO, UcUser.class);
		ucUser.setSalt(BusinessUtil.salt());
		ucUser.setPassword(CryptUtil.encryptPwd(ucUser.getPassword(), ucUser.getSalt()));
		ucUser.setStatus(UserStatusEnum.OK.getCode());
		ucUser.setRegisterTime(new Date());
		ucUser.setRegisterIp(userDTO.getRegisterIp());
		ucUser.setLastLoginIp(userDTO.getRegisterIp());
		BaseEntity.fillBaseInfo(ucUser);

		return ucUser;
	}

	@Override
	public ApiResponse detail(String username) {
		UcUser ucUser = ucUserManager.findByUsername(username);
		if (isNull(ucUser)) {
			BusinessException.throwOut(UserErrors.USERNAME_NOT_EXISTED);
		}

		UserVO userVO = TransformUtil.copyProperties(ucUser, UserVO.class);
		userVO.setRegisterip(NetUtils.int2Ip(ucUser.getRegisterIp()));
		userVO.setLastLoginIp(NetUtils.int2Ip(ucUser.getLastLoginIp()));
		return ApiResponse.yes(userVO);
	}
}
