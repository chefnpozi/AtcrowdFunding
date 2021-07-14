package com.atguigu.scw.project.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.atguigu.scw.project.component.OssTemplate;

@SpringBootConfiguration
public class AppProjectConfig {

//	@Bean
//	@ConfigurationProperties(prefix="oss")	// 通过匹配前缀，注入相应的属性
//	public OssTemplate ossTemplate() {
//		return new OssTemplate();
//	}
	
}
