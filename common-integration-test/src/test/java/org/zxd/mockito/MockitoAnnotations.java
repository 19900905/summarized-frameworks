package org.zxd.mockito;

import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({"unchecked", "rawtypes"})
@Slf4j
public class MockitoAnnotations {
	/**
	 * 创建一个 mock 对象
	 */
	@Mock
	List<String> names;
	/**
	 * 创建 mock 对象，并按需注入其他 mock 对象到目标对象中
	 */
	@InjectMocks
	private Composite composite;
	/**
	 * 创建参数捕获器
	 */
	@Captor
	private ArgumentCaptor<String> argumentCaptor;
	/**
	 * 创建监视器
	 */
	@Spy
	private List<String> spy;

	@Test
	public void inject() {
		final String first = "first";
		Mockito.when(names.get(0)).thenReturn(first);

		final String val = composite.getNames().get(0);
		composite.show();
		Assert.assertEquals(first, val);
	}

	@Test
	public void captor() {
		final List mock = Mockito.mock(List.class);

		final String hello = "hello";
		mock.add(hello);

		Mockito.verify(mock).add(argumentCaptor.capture());

		MockitoAnnotations.log.info("arg {}", argumentCaptor.getValue());
		Assert.assertEquals(hello, argumentCaptor.getValue());
	}
}

@Slf4j
@Data
class Composite {
	private List<String> names;

	public void show() {
		Composite.log.info("{}", names);
	}
}
