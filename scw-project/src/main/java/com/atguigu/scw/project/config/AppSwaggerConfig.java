package com.atguigu.scw.project.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;

import io.swagger.annotations.Api;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootConfiguration
@EnableSwagger2
public class AppSwaggerConfig {

	// 声明这是个配置类
	// 配置swagger在当前项目中

	@Value("${swagger2.enable:false}")
	private boolean enable = false;

	@Bean("项目模块")
	public Docket projectApis() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("项目模块").select()
				.apis(RequestHandlerSelectors.withClassAnnotation(Api.class)).paths(PathSelectors.regex("/project.*"))
				.build().apiInfo(apiInfo()).enable(enable);
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("尚筹网-系统平台接口文档").description("提供用户模块/审核模块/项目模块/支付模块的文档")
				.termsOfServiceUrl("http://www.atguigu.com/").version("1.0").build();
	}

}
