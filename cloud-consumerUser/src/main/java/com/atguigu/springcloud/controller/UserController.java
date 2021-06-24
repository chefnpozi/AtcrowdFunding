package com.atguigu.springcloud.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.atguigu.springcloud.bean.Movie;
import com.atguigu.springcloud.bean.User;
import com.atguigu.springcloud.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
// import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class UserController {

	// 本地调用
	@Autowired
	UserService userService; // 本地接口

	// 远程调用
	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/getUserById/{id}")
	public User getUserById(@PathVariable("id") Integer id) {

		return userService.getUserById(id);

	}

	// 如果此方法不能返回合适的数据，调用熔断方法，因此熔断方法的 权限修饰符 返回值类型 形参 都是一样的，只对函数名和函数体可以进行修改
	@HystrixCommand(fallbackMethod = "buyMovieHystrix")
	@GetMapping("/buyMovie/{userId}/{movieId}")
	public Map<String, Object> buyMovie(@PathVariable("userId") Integer userId,
			@PathVariable("movieId") Integer movieId) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		User user = userService.getUserById(userId);
		map.put("user", user);

		// 怎么通过远程调用拿到这个对象呢？通过服务器通信完成
		// 采用REST风格
		// 1.采用 RestTemplate 进行远程调用
		// 2.采用Feign进行远程调用
		// Movie movie =
		// restTemplate.getForObject("http://CLOUD-PROVIDER-MOVIE/getMovieById/"+movieId,
		// Movie.class) ;
		Movie movie = restTemplate.getForObject("http://CLOUD-PROVIDER-MOVIE/getMovieById/" + movieId, Movie.class);
		map.put("movie", movie);

		return map;
	}

	
	public Map<String, Object> buyMovieHystrix(@PathVariable("userId") Integer userId,
			@PathVariable("movieId") Integer movieId) {
		Map<String, Object> map = new HashMap<String, Object>();

		User user = new User();
		user.setId(-1);
		user.setName("无此用户");

		Movie movie = new Movie();
		movie.setId(-100);
		movie.setName("无此电影");

		map.put("user", user);
		map.put("movie", movie);

		return map; // 兜底数据。
	}
}
