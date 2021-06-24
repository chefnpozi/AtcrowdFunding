package com.atguigu.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.springboot.bean.TAdmin;
import com.atguigu.springboot.service.TAdminService;

@Controller
public class TAdminController {

	@Autowired
	TAdminService adminService;
	
	@ResponseBody
	@RequestMapping("/geTAdminById/{id}")	// url中的占位符
	public TAdmin geTAdminById(@PathVariable("id")Integer id) {	// 将url中的占位符中的值赋给id
		
		
		return adminService.geTAdminById(id);
	}
}
