package com.atguigu.scw.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.scw.user.bean.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags="测试Swagger工具的helloworld")
public class HelloController {

	@ApiImplicitParams(value = {
			@ApiImplicitParam(value="姓名",name="name",required=true),
			@ApiImplicitParam(value="年龄",name="age",required=true)
	})
	@ApiOperation("演示接口调用")
	@GetMapping("/hello")
	public String hello(String name, String age) {
		return "OK:"+name+"-"+age;
	}
	
	
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name="name",value="用户姓名",required=true),
			@ApiImplicitParam(name="id",value="用户id",required=true)
	})
	@ApiOperation("测试方法2")
	@PostMapping("/user")
	public User user(String name ,Integer id) {
		User user = new User();
		user.setName(name);
		user.setId(id);
		return user;
	}

	
	
	
	
	

}
