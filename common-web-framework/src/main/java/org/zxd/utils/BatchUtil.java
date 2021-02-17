package org.zxd.utils;
/*
 * Project: learn-in-action
 * DateTime: 2020/4/6 18:08
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */

import com.alibaba.fastjson.JSON;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;

import static org.apache.commons.collections4.CollectionUtils.isNotEmpty;

@Slf4j
public class BatchUtil {

	private static final int PROCESSOR_COUNT = Runtime.getRuntime().availableProcessors();
	private static final int WORKER_COUNT = PROCESSOR_COUNT * 4;

	public static <T> void doInParallel(String jobName, Supplier<List<T>> batchSuppiler, Consumer<T> preHandler, Consumer<T> postHandler) {
		List<T> jobRecord = batchSuppiler.get();
		if (isNotEmpty(jobRecord)) {
			ExecutorService executorService = Executors.newFixedThreadPool(WORKER_COUNT, threadFactory(jobName));
			try {
				doJob(batchSuppiler, preHandler, postHandler, jobRecord, executorService);
			} finally {
				try {
					executorService.awaitTermination(1, TimeUnit.MINUTES);
					executorService.shutdown();
				} catch (InterruptedException e) {
					log.error("JobInterrupted {}", Throwables.getStackTraceAsString(e));
				}
			}
		}
	}

	private static <T> void doJob(Supplier<List<T>> batchSuppiler, Consumer<T> preHandler, Consumer<T> postHandler, List<T> jobRecord, ExecutorService executorService) {
		int maxAttempts = 0;
		while (isNotEmpty(jobRecord) && maxAttempts++ < 400) {
			CountDownLatch latch = new CountDownLatch(jobRecord.size());
			for (T job : jobRecord) {
				executorService.submit(() -> {
					try {
						preHandler.accept(job);
					} catch (Exception e) {
						log.error("doJobFailed {} {}", JSON.toJSONString(job), Throwables.getStackTraceAsString(e));
					} finally {
						postHandler.accept(job);
					}
					latch.countDown();
				});
			}
			try {
				latch.await();
			} catch (InterruptedException e) {
				log.error("JobInterrupted {}", Throwables.getStackTraceAsString(e));
			}
			jobRecord = batchSuppiler.get();
		}
	}

	private static ThreadFactory threadFactory(String name) {
		return new ThreadFactoryBuilder().setDaemon(true).setNameFormat("batchJob-" + name + "-%d").build();
	}

}
