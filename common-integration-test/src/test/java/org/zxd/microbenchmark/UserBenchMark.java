package org.zxd.microbenchmark;
/*
 * Project: idea-projects
 * DateTime: 2021/2/10 16:18
 * @author: 竺旭东
 * Version: v1.0
 * Desc: TODO
 */

import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.RandomStringUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.GroupThreads;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.zxd.CommonTestApplication;
import org.zxd.business.usercenter.UserWriteService;
import org.zxd.business.usercenter.dto.UserDTO;
import org.zxd.utils.NetUtils;

@BenchmarkMode(Mode.Throughput) // 吞吐量
@OutputTimeUnit(TimeUnit.MILLISECONDS) // 测试结果所使用的时间单位
@State(Scope.Benchmark) // 每个基准测试一个实例，所有线程共享此实例
@Fork(2) // Fork进行的数目
@Warmup(iterations = 4) // 先预热 4轮
@Measurement(iterations = 10) // 进行 10轮测试
public class UserBenchMark {

	ConfigurableApplicationContext context;
	UserWriteService writeService;

	// 初始化：整个 Benchmark 初始化一次
	@Setup(Level.Trial)
	public void before() {
		context = SpringApplication.run(CommonTestApplication.class, new String[]{});
		writeService = context.getBean(UserWriteService.class);
	}

	// 100 个线程同时对此方法进行微基准测试
	@Benchmark
	@GroupThreads(100)
	public void insert() {
		String numeric = RandomStringUtils.randomNumeric(11);

		UserDTO userDTO = UserDTO.builder()
				.username(numeric)
				.password("1qaz!QAZ")
				.conform("1qaz!QAZ")
				.registerIp(NetUtils.ip2Int("127.0.0.1")).build();

		writeService.register(userDTO);
	}

	public static void main(String[] args) throws RunnerException {
		Options options = new OptionsBuilder().include(UserBenchMark.class.getSimpleName()).build();
		new Runner(options).run();
	}
}
