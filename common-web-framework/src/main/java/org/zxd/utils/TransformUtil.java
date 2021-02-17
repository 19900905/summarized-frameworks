package org.zxd.utils;
/*
 * Project: learn-in-action
 * DateTime: 2020/1/14 16:59
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */

import org.springframework.beans.BeanUtils;

public class TransformUtil {

	public static <O> O copyProperties(Object src, Class<O> targetClz) {
		O dest = BeanUtils.instantiateClass(targetClz);
		BeanUtils.copyProperties(src, dest);
		return dest;
	}
}
