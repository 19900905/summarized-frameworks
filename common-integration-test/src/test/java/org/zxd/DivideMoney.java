package org.zxd;
/*
 * Project: idea-projects
 * DateTime: 2020/11/11 20:14
 * @author: zhuxd
 * Version: v1.0
 * Desc: TODO
 */

import com.google.common.collect.Lists;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

public class DivideMoney {

	public static void main(String[] args) {
		List<Integer> doubles = divideMoney(10, 100);
		System.out.println(Arrays.toString(doubles.toArray()));
	}

	/**
	 * 评测题目: 无
	 * 请编写一个红包随机算法。需求为：给定一定的金额，一定的人数，保证每个人都能随机获得一定的金额。
	 * 比如100元的红包，10个人抢，每人分得一些金额。
	 * 约束条件为，最佳手气金额不能超过最大金额的90%。请给出代码实现。谢谢.
	 *
	 * @param people 总人数
	 * @param total  总金额
	 * @return
	 */
	public static List<Integer> divideMoney(Integer people, Integer total) {
		checkValue(people);
		checkValue(total);
		if (people > total) {
			throw new RuntimeException("金额不够分");
		}

		// 最小金额
		int min = 1;
		// 最大金额
		int highLimit = BigDecimal.valueOf(total * 0.9).intValue();
		// 当前红包限制
		double currentLimit;
		// 剩余金额
		double remainMoney = total;
		// 当前红包金额
		double currentMoney = min;
		// 实际中奖金额
		int winMoney;
		int remainPeople;

		List<Integer> moneies = Lists.newArrayList();
		SecureRandom random = new SecureRandom();
		for (int i = 0; i < people; i++) {
			if (i == people - 1) {
				moneies.add(keepPoint(remainMoney));
				break;
			}

			currentLimit = remainMoney / (people - i) * 2;
			currentMoney = random.nextDouble() * currentLimit;
			if (currentMoney < min) {
				currentMoney = min;
			} else if (currentMoney > highLimit) {
				currentMoney = highLimit;
			}

			winMoney = keepPoint(currentMoney);
			remainPeople = people - i - 1;
			if (remainMoney - winMoney < remainPeople) {
				winMoney = (winMoney - remainPeople) / 2;
			}

			moneies.add(winMoney);
			remainMoney -= winMoney;
		}

		return moneies;
	}

	/**
	 * 保留两位小数
	 *
	 * @param currentMoney
	 * @return
	 */
	private static int keepPoint(double currentMoney) {
		return BigDecimal.valueOf(currentMoney).setScale(2, RoundingMode.FLOOR).intValue();
	}

	/**
	 * 有效性校验，整数必须为正
	 *
	 * @param people
	 */
	private static void checkValue(Integer people) {
		if (people == null || people < 0) {
			throw new RuntimeException("invalid input");
		}
	}

}
