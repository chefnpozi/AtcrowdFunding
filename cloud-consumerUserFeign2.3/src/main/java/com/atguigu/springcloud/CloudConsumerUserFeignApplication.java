package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;


@EnableHystrixDashboard	// 开启可视化监控功能
@EnableCircuitBreaker 	// 启用熔断器
@EnableFeignClients		// 启用Feign
@EnableDiscoveryClient	// 使该服务注册到注册中心
@SpringBootApplication
public class CloudConsumerUserFeignApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudConsumerUserFeignApplication.class, args);
	}

}
