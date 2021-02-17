package org.zxd.distributed.lock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 * Project: learn-in-action
 * DateTime: 2020/4/6 18:49
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
public @interface DoInLock {
	String keySpel();
}
