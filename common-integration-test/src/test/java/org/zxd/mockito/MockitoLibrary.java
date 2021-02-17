package org.zxd.mockito;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.MockingDetails;
import org.mockito.Mockito;
import org.mockito.mock.MockCreationSettings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.RETURNS_SMART_NULLS;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

/**
 * create by zhuxd at 2018年8月1日T上午10:48:44
 * keep it simple and stupid
 */
@SuppressWarnings({"unchecked", "boxing"})
@Slf4j
public class MockitoLibrary {

	/**
	 * Let's verify some behaviour
	 */
	@Test
	public void verifyBehavior() {
		List<String> mock = mock(List.class);
		String target = "hello mockito";
		mock.add(target);
		mock.clear();

		/**
		 * 	一旦创建 mock 对象，他会记住所有的交互操作，你可以验证你感兴趣的操作
		 */
		verify(mock).add(target);
		verify(mock).clear();
	}

	/**
	 * 存根
	 */
	@Test
	public void stub() {
		/**
		 * 1）默认情况下，对于所有具有返回值的方法，mock 对象会返回默认值。
		 * 	返回值类型为对象，mock 返回 null；
		 * 	返回值类型为包装类，mock 返回其对应基本类型的默认值；
		 * 	返回值类型为集合，mock 对象返回空集合。
		 * 2）stub 可以被覆盖，但是不建议覆盖已经定义的行为。
		 * 3）一旦被 stub，无论调用多少次，指定的方法永远会返回 stub 的结果。
		 * 4）
		 */
		LinkedList<Integer> mock = mock(LinkedList.class);
		when(mock.get(0)).thenReturn(1);
		when(mock.getLast()).thenReturn(-1);

		log.info("get(0) {}", mock.get(0));
		log.info("getLast() {}", mock.getLast());
		log.info("get(999) {}", mock.get(999));

		verify(mock).get(0);
		verify(mock).getLast();
	}

	/**
	 * Argument matchers
	 */
	@Test
	public void argumentMatchers() {
		List<String> mock = mock(List.class);
		/**
		 * 	1）参数匹配能够提供弹性验证和存根
		 */
		when(mock.get(anyInt())).thenReturn("element");
		when(mock.get(eq(999))).thenReturn("999");
		when(mock.contains(isNotNull())).thenReturn(Boolean.TRUE);

		log.info("anyInt {}", mock.get(0));
		log.info("anyInt {}", mock.get(1));
		log.info("get(999) {}", mock.get(999));
		log.info("isNotNull {}", mock.contains(""));
		log.info("isNotNull {}", mock.contains(null));

		verify(mock, times(3)).get(anyInt());
		verify(mock, times(2)).contains(any());
	}

	/**
	 * Verifying exact number of invocations
	 */
	@Test
	public void verifyNumbers() {
		List<Integer> mock = mock(List.class);
		mock.add(1);
		mock.add(2);
		mock.add(2);
		mock.add(3);
		mock.add(3);
		mock.add(3);

		verify(mock).add(1); // 不指定 times 默认验证一次
		verify(mock, times(2)).add(2); // 指定的行为严格发生 N 次
		verify(mock, times(3)).add(3);

		verify(mock, never()).add(4);    // 指定的行为未发生
		verify(mock, atLeastOnce()).add(1); // 指定的行为至少发生一次
		verify(mock, atMost(2)).add(2);    // 指定的行为最多发生 N 次
		verify(mock, atLeast(3)).add(3);    // 指定的行为至少发生 N 次
	}

	/**
	 * Stubbing void methods with exceptions
	 */
	@Test
	public void stubVoid() {
		List mock = mock(List.class);
		// 验证 void 方法抛出异常
		when(mock.get(anyInt())).thenThrow(IndexOutOfBoundsException.class);
		try {
			mock.get(0);
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
		}
	}

	/**
	 * Verification in order
	 */
	@Test
	public void order() {
		List mock = mock(List.class);
		mock.add(1);
		mock.add(2);

		/**
		 *	 验证单个对象操作的执行顺序
		 */
		InOrder orderMock = inOrder(mock);
		orderMock.verify(mock).add(1);
		orderMock.verify(mock).add(2);

		List first = mock(List.class);
		List second = mock(List.class);
		first.add(1);
		second.add(2);

		/**
		 * 	验证多个对象操作的执行顺序
		 */
		InOrder order = inOrder(first, second);
		order.verify(first).add(1);
		order.verify(second).add(2);
	}

	/**
	 * Making sure interaction(s) never happened on mock
	 */
	@Test
	public void interaction() {
		List first = mock(List.class);
		verifyZeroInteractions(first); // 验证指定的 mock 对象没有任何交互
	}

	/**
	 * Finding redundant invocations
	 */
	@Test
	public void noMoreInteraction() {
		List mock = mock(List.class);
		mock.add(1);
		mock.add(2);

		verify(mock).add(1);
		verify(mock).add(2);
		verifyNoMoreInteractions(mock); // 验证指定的 mock 对象没有更多的交互
	}

	/**
	 * Stubbing consecutive calls
	 */
	@Test
	public void consecutiveCalls() {
		List mock = mock(List.class);

		// 连续存根调用
		when(mock.get(0)).thenReturn(1, 2, 3).thenThrow(IndexOutOfBoundsException.class);

		assertEquals(1, mock.get(0));
		assertEquals(2, mock.get(0));
		assertEquals(3, mock.get(0));
		try {
			mock.get(0);
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
		}
	}

	/**
	 * Stubbing with callbacks
	 */
	@Test
	public void callbacks() {
		List mock = mock(List.class);
		when(mock.get(0)).thenAnswer(mockInvocation -> {
			Object[] arguments = mockInvocation.getArguments();
			return Arrays.stream(arguments).map(String::valueOf).reduce("args:", String::concat);
		});

		log.info("{}", mock.get(0));
	}

	/**
	 * doReturn()|doThrow()| doAnswer()|doNothing()|doCallRealMethod() family of methods
	 */
	@Test
	public void doFamily() {
		List mock = mock(List.class);
		doReturn(1).when(mock).get(0);
		doThrow(IndexOutOfBoundsException.class).when(mock).get(1);

		assertEquals(1, mock.get(0));
		try {
			mock.get(1);
		} catch (Exception e) {
			assertTrue(e instanceof IndexOutOfBoundsException);
		}
	}

	/**
	 * Changing default return values of unstubbed invocations
	 */
	@Test
	public void changeDefault() {
		List mock = mock(List.class, Mockito.RETURNS_SMART_NULLS);
		log.info("{}", mock.get(0));
	}

	/**
	 * Capturing arguments for further assertions
	 */
	@Test
	public void argumentsCaptor() {
		List<String> mock = mock(List.class);
		mock.add("hello");

		ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
		verify(mock).add(argumentCaptor.capture()); // 在验证过程中捕获实际交互时传递的参数

		log.info("arg {}", argumentCaptor.getValue());
		log.info("args {}", argumentCaptor.getAllValues());

		assertEquals("hello", argumentCaptor.getValue());
	}

	/**
	 * Resetting mocks
	 */
	@Test
	public void reset() {
		List mock = mock(List.class);
		when(mock.get(0)).thenReturn(1);

		log.info("first {}", mock.get(0));
		assertEquals(1, mock.get(0));
		Mockito.reset(mock);

		log.info("second {}", mock.get(0));
		assertEquals(null, mock.get(0));
	}

	/**
	 * Aliases for behavior driven development
	 * http://en.wikipedia.org/wiki/Behavior_Driven_Development
	 */
	@Test
	public void bdd() {
		List mock = mock(List.class);
		// 给定某个条件时，将会返回指定的结果
		given(mock.get(0)).willReturn(1);
		Object val = mock.get(0); // when
		assertEquals(1, val); // then
	}

	/**
	 * 序列化和其他设置
	 */
	@Test
	public void settings() {
		List mock = mock(List.class, withSettings().defaultAnswer(RETURNS_SMART_NULLS).serializable().verboseLogging());
		mock.add(1);

		verify(mock).add(1);
	}

	/**
	 * Verification with timeout
	 */
	@Test
	public void verfiyTimeout() {
		List mock = mock(List.class);
		mock.add(1);
		mock.add(2);
		mock.add(2);

		verify(mock, timeout(1000)).add(1);    // 指定验证的超时时间为 1000 毫秒
		verify(mock, timeout(1000).times(2)).add(2);    // 指定 2 次验证的总超时时间为 1000 毫秒
	}

	/**
	 * Mocking details
	 */
	@Test
	public void mockDetails() {
		List mock = mock(List.class);

		// 获取 mock 对象的详细信息
		MockingDetails details = mockingDetails(mock);
		boolean isMock = details.isMock();
		boolean isSpy = details.isSpy();

		assertTrue(isMock);
		assertFalse(isSpy);

		// 获取 Mock 对象的配置信息
		MockCreationSettings<?> settings = details.getMockCreationSettings();
	}

	/**
	 * Better generic support with deep stubs (Since 1.10.0)
	 */
	@Test
	public void deepStubs() {
		First mock = mock(First.class, RETURNS_DEEP_STUBS); // 深度存根

		String kristy = "kristy";
		when(mock.getSecond().getName()).thenReturn(kristy);

		String name = mock.getSecond().getName();
		assertEquals(kristy, name);
	}

}

@Data
class First {
	private Second second;
}

@Data
class Second {
	private String name;
}
