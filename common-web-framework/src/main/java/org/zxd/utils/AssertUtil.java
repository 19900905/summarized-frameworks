package org.zxd.utils;
/*
 * Project: learn-in-action
 * DateTime: 2020/1/13 16:13
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */

import java.lang.reflect.Field;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.util.Assert;
import org.springframework.util.ObjectUtils;

import static java.util.Objects.requireNonNull;

public class AssertUtil {

	public static final boolean equals(Object first, Object second, List<String> propertyNames) {
		requireNonNull(first);
		requireNonNull(second);
		Assert.isTrue(CollectionUtils.isNotEmpty(propertyNames), "propertyNames can not be empty");

		Class<?> firstClz = first.getClass();
		Class<?> secondClz = second.getClass();
		try {
			for (String propertyName : propertyNames) {
				Object srcValue = getValue(first, firstClz, propertyName);
				Object targetValue = getValue(second, secondClz, propertyName);
				if (!ObjectUtils.nullSafeEquals(srcValue, targetValue)) {
					return false;
				}
			}
		} catch (NoSuchFieldException | IllegalAccessException e) {
			return false;
		}

		return true;
	}

	private static Object getValue(Object src, Class<?> srcClass, String propertyName) throws NoSuchFieldException, IllegalAccessException {
		Field srcField = srcClass.getDeclaredField(propertyName);
		srcField.setAccessible(true);
		return srcField.get(src);
	}
}
