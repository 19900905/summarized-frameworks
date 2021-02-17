package org.zxd.utils;

/*
 * Project: self-study-parent
 * DateTime: 08/08/2019 22:55
 * @Author: zhuxd
 * Version: v1.0
 */
public class DistributedId {
	private static final SnowFlakeIdWorker SNOWFLAKE_ID_WORKER = new SnowFlakeIdWorker(1, 1);

	public static final String nextId() {
		return String.valueOf(SNOWFLAKE_ID_WORKER.nextId());
	}
}