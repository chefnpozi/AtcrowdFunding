package com.atguigu.controller.user;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.bean.AppResponse;
import com.atguigu.entity.TMember;
import com.atguigu.enums.AccttypeEnume;
import com.atguigu.enums.AuthEnume;
import com.atguigu.enums.UserTypeEnume;
import com.atguigu.vo.LoginRespVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "用户登陆注册模块")
@RequestMapping("/user")
@RestController
public class UserLoginRegistController {
	
	@ApiOperation(value="用户登陆") 
	@ApiImplicitParams(value={
			@ApiImplicitParam(value="登陆账号",name="loginacct"),
			@ApiImplicitParam(value="用户密码",name="password")
	})
	@PostMapping("/login")
	public AppResponse<LoginRespVo> login(String loginacct,String password){
		LoginRespVo respVo = new LoginRespVo();
		TMember member = new TMember();
		member.setLoginacct("15353475896");
		member.setRealname("张三");
		member.setUsertype(UserTypeEnume.USER_VIP2.getCode());
		member.setAccttype(AccttypeEnume.BUSINESS_MAN.getCode());
		member.setAuthstatus(AuthEnume.AUTHING.getCode());
		member.setCardnum("145645893212365646");
		member.setUsername("zhangsan");
		
		respVo.setAccessToken(UUID.randomUUID().toString().replace("-", ""));
		respVo.setMember(member);
		return AppResponse.ok(respVo);
	} 
	
	@ApiOperation(value="用户注册") 
	@PostMapping("/register")
	public AppResponse<Object> register(){
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="发送短信验证码") 
	@PostMapping("/sendsms")
	public AppResponse<Object> sendsms(){
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="验证短信验证码") 
	@PostMapping("/valide")
	public AppResponse<Object> valide(){
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="重置密码") 
	@PostMapping("/reset")
	public AppResponse<Object> reset(){
		return AppResponse.ok("ok");
	} 
	
	

}
