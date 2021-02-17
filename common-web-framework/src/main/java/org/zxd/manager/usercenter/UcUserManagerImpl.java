package org.zxd.manager.usercenter;
/*
 * Project: learn-in-action
 * DateTime: 2020/1/13 17:35
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.zxd.dao.entity.usercenter.UcUser;
import org.zxd.dao.uc.UcUserMapper;
import org.zxd.manager.config.RecordChange;
import org.zxd.utils.CacheNames;
import org.zxd.utils.DistributedId;

@Repository
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UcUserManagerImpl implements UcUserManager {

	@Autowired
	private UcUserMapper ucUserMapper;

	/**
	 * 新增用户并返回 userId
	 *
	 * @param record
	 * @return 用户唯一标识
	 */
	@Override
	@RecordChange(type = "userInfo", key = "#record.userId", changeData = "#record")
	/**
	 * 方法的返回值会被加入到缓存中
	 * cacheNames：缓存名称，可以指定多个
	 * key：基于 SpEL 表达式进行计算的缓存键，不指定缓存键时，根据方法参数自动计算。
	 * 		#result 表示方法的返回值对象
	 * 		#root.method 表示方法对象【java.lang.reflect.Method method】
	 * 		#root.caches 表示缓存对象
	 *  	#root.methodName 表示方法名称
	 *  	#root.targetClass 表示类的全限定名称
	 *  	#root.args[0]}, {@code #p0} or {@code #a0 都表示第一个参数对象
	 * keyGenerator：实现 org.springframework.cache.interceptor.KeyGenerator 接口的 Bean 名称
	 * cacheManager：缓存管理器的 Bean 名称
	 * cacheResolver：缓存解析器的 Bean 名称
	 * condition：基于 SpEl 表达式进行解析的缓存条件，只有条件为 true 时才进行缓存
	 */
	@CachePut(cacheNames = CacheNames.USER, key = "#result.username")
	public UcUser insert(UcUser record) {
		record.setUserId(DistributedId.nextId());
		int insert = ucUserMapper.insert(record);

		Assert.isTrue(insert == 1, "add user failed");
		return record;
	}

	/**
	 * 根据用户名称查询
	 *
	 * @param username
	 */
	@Cacheable(cacheNames = CacheNames.USER, key = "#username")
	@Override
	public UcUser findByUsername(String username) {
		return ucUserMapper.findByUsername(username);
	}

}
