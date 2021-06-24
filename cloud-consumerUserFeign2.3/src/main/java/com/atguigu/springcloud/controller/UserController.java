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
import com.atguigu.springcloud.service.MovieServiceFeign;
import com.atguigu.springcloud.service.UserService;


@RestController
public class UserController {

	// 本地调用
	@Autowired
	UserService userService ; //本地接口
	
	// Feign接口，用于远程调用
	@Autowired
	MovieServiceFeign movieServiceFeign;
	
	@GetMapping("/getUserById/{id}")
	public User getUserById(@PathVariable("id") Integer id) {
		
		return userService.getUserById(id);
		

		
	}
	
	
	@GetMapping("/buyMovie/{userId}/{movieId}")
	public Map<String, Object> buyMovie(@PathVariable("userId") Integer userId,
										@PathVariable("movieId") Integer movieId) {
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		User user = userService.getUserById(userId);
		map.put("user", user);
		
		// 怎么通过远程调用拿到这个对象呢？通过服务器通信完成
		// Feign进行远程调用
		Movie movie = movieServiceFeign.getMovieById(movieId);
		map.put("movie", movie);
		
		return map;
	}
}
