package com.atguigu.scw.user.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.scw.user.component.SmsTemplate;
import com.atguigu.scw.vo.resp.AppResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.dynamic.scaffold.MethodRegistry.Handler.ForImplementation;

@Slf4j
@Api(tags = "用户登陆注册模块")
@RequestMapping("/user")
@RestController
public class UserLoginRegistController {
	
	@Autowired
	SmsTemplate smsTemplate;
	
	@Autowired	
	StringRedisTemplate stringRedisTemplate;
	
//	@ApiOperation(value="用户登陆") 
//	@ApiImplicitParams(value={
//			@ApiImplicitParam(value="登陆账号",name="loginacct"),
//			@ApiImplicitParam(value="用户密码",name="password")
//	})
//	@PostMapping("/login")
//	public AppResponse<LoginRespVo> login(String loginacct,String password){
//		LoginRespVo respVo = new LoginRespVo();
//		TMember member = new TMember();
//		member.setLoginacct("15353475896");
//		member.setRealname("张三");
//		member.setUsertype(UserTypeEnume.USER_VIP2.getCode());
//		member.setAccttype(AccttypeEnume.BUSINESS_MAN.getCode());
//		member.setAuthstatus(AuthEnume.AUTHING.getCode());
//		member.setCardnum("145645893212365646");
//		member.setUsername("zhangsan");
//		
//		respVo.setAccessToken(UUID.randomUUID().toString().replace("-", ""));
//		respVo.setMember(member);
//		return AppResponse.ok(respVo);
//	} 
	
	@ApiOperation(value="用户注册") 
	@PostMapping("/register")
	public AppResponse<Object> register(){
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="发送短信验证码") 
	@PostMapping("/sendsms")
	public AppResponse<Object> sendsms(String mobile){
		
		// 构建随机数，当做验证码发给手机
		StringBuilder code = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			// 随机 0-9 的整数
			code.append(new Random().nextInt(10));
		}
		
		Map<String, String> querys = new HashMap<String, String>();		
		
		querys.put("mobile", mobile);
		querys.put("param", "code:" + code.toString());
		querys.put("tpl_id", "TP1711063");
		
		smsTemplate.sendSms(querys);
		
		// 将发给手机号的验证码，存入redis
		stringRedisTemplate.opsForValue().set(mobile, code.toString());
		
		log.debug("验证码：{}", code.toString());
		
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
