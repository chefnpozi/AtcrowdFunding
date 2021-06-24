package com.atguigu.springboot.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

	@ResponseBody
	@GetMapping("/hello")
	public String handle01(){
	return "OK!+哈哈";
	}


}
