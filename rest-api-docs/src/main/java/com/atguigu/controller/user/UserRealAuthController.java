package com.atguigu.controller.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.bean.AppResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "用户实名审核模块")
@RequestMapping("/user/auth")
@RestController
public class UserRealAuthController {
	
	@ApiOperation(value="认证申请第1步-用户认证申请开始",position=1) 
	@GetMapping("/start")
	public AppResponse<Object> start(){
		return AppResponse.ok("ok");
	} 
	@ApiOperation(value="认证申请第2步-提交基本信息",position=2) 
	@PostMapping("/baseinfo")
	public AppResponse<Object> baseinfo(){
		return AppResponse.ok("ok");
	}

	
	@ApiOperation(value="认证申请第3步-上传资质信息",position=3) 
	@PostMapping("/certs")
	public AppResponse<Object> certs(){
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="认证申请第4步-确认邮箱信息",position=4) 
	@PostMapping("/email")
	public AppResponse<Object> email(){
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="认证申请第5步-提交实名认证申请",position=5) 
	@PostMapping("/submit")
	public AppResponse<Object> submit(){
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="获取需要上传的资质信息",position=6) 
	@GetMapping("/certs2upload")
	public AppResponse<Object> certs2upload(){
		return AppResponse.ok("ok");
	} 
	
	
}
