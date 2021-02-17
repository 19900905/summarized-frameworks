package cucumber.runtime;

import cucumber.api.TypeRegistry;
import cucumber.api.TypeRegistryConfigurer;
import io.cucumber.datatable.DataTableType;
import java.util.Locale;
import org.zxd.business.usercenter.dto.UserDTO;

public class DefaultTypeRegistryConfiguration implements TypeRegistryConfigurer {

	@Override
	public Locale locale() {
		return Locale.ENGLISH;
	}

	@Override
	public void configureTypeRegistry(TypeRegistry typeRegistry) {
		// 注册数据表类型转换
		typeRegistry.defineDataTableType(DataTableType.entry(UserDTO.class));
	}

}