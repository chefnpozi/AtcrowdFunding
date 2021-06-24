package com.atguigu.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.springcloud.bean.Movie;
import com.atguigu.springcloud.service.exp.MovieServiceFeignExceptionHandler;

// fallback指定熔断后应返回怎样的兜底数据，这些写在这个类中，记得要被Spring管理起来@Controller

@FeignClient(value="CLOUD-PROVIDER-MOVIE",fallback=MovieServiceFeignExceptionHandler.class)	// 指定远程调用哪一个服务，服务名必须和远程服务名称一致，不区分大小写
public interface MovieServiceFeign {

	// 声明一个所需要的其他项目的接口，与远程一致
	
	@GetMapping("/getMovieById/{id}")
	public Movie getMovieById(@PathVariable("id") Integer id);
}
