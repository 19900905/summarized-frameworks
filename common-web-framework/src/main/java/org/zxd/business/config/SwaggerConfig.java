package org.zxd.business.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Profile({"dev", "sit"})
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 * @return
	 */
	@Bean
	public Docket createRestFulApi() {
		/**
		 * 指定 swagger 注解扫描的基本包是 org.zxd.web
		 * 指定所有的路径都通过 swagger 进行拦截
		 * 默认访问地址：http://localhost:8080/swagger-ui.html
		 */
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("org.zxd.web"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	/**
	 * @return
	 */
	@Bean
	public ApiInfo apiInfo() {
		/**
		 * 设置一些说明信息
		 */
		return new ApiInfoBuilder().title("Spring Boot 集成 Swagger API")
				.description("Offical Website：https://swagger.io/")
				.termsOfServiceUrl("")
				.version("1.0.0")
				.contact(new Contact("zhuxd", "15505883728", "15505883728@163.com")).build();
	}
}