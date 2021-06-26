package com.atguigu.scw.user.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.scw.user.bean.TMember;
import com.atguigu.scw.user.component.SmsTemplate;
import com.atguigu.scw.user.service.TMemberService;
import com.atguigu.scw.user.vo.req.UserRegistVo;
import com.atguigu.scw.user.vo.resp.UserRespVo;
import com.atguigu.scw.vo.resp.AppResponse;

import io.lettuce.core.models.command.CommandDetail.Flag;
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
	
	@Autowired	
	TMemberService memberService;
	
	@ApiOperation(value="用户登陆") 
	@ApiImplicitParams(value={
			@ApiImplicitParam(value="登陆账号",name="loginacct"),
			@ApiImplicitParam(value="用户密码",name="password")
	})
	@PostMapping("/login")
	public AppResponse<UserRespVo> login(String loginacct,String password){
		
		try {
			
			log.debug("登录表单数据loginacct-{}",loginacct);
			log.debug("登录表单数据password-{}",password);
			
			UserRespVo vo = memberService.getUserByLogin(loginacct, password);
			log.debug("登录成功-{}",loginacct);
			return AppResponse.ok(vo);
		} catch (Exception e) {
			e.printStackTrace();
			
			log.debug("登录失败-{}-{}",loginacct,e.getMessage());
			AppResponse resp = AppResponse.fail(null);
			resp.setMsg(e.getMessage());
			return resp;
		}
	} 
	
	@ApiOperation(value="用户注册") 
	@PostMapping("/register")
	public AppResponse<Object> register(UserRegistVo vo){
		
		// 手机号登录，拿到手机号
		String loginacct = vo.getLoginacct();
		// 判空
		if (StringUtils.isEmpty(loginacct)) {
			// 为空，没有数据返回
			AppResponse<Object> resp = AppResponse.fail(null);
			resp.setMsg("数据不能为空"); // 给返回值一个消息
			return resp;
		}else {
			// 不为空，去redis中拿验证码进行验证
			String code = stringRedisTemplate.opsForValue().get(loginacct);
			// 判断拿到的验证码，有没有因过期而失效
			if (StringUtils.isEmpty(code)) {
				AppResponse<Object> resp = AppResponse.fail(null);
				resp.setMsg("验证码已经失效"); // 给返回值一个消息
				// 这里还要判断一下是否为频繁发送
				return resp;
			} else {
				// 拿到了redis中的验证码，看验证码是否相等
				// code肯定不为空，它在前面不会空指针
				if (!code.equals(vo.getCode())) {
					AppResponse<Object> resp = AppResponse.fail(null);
					resp.setMsg("验证码不一致"); // 给返回值一个消息
					return resp;
				} else {
					// 校验 账号 和 邮箱 是否 唯一
					
					// 此时校验成功，把vo中的信息进行存储
					int falg = memberService.saveTMember(vo);
					
					// 删除当前redis中的验证码
					// stringRedisTemplate.delete(loginacct);
					
					if (falg != 1) {
						AppResponse<Object> resp = AppResponse.fail(null);
						resp.setMsg("存储失败"); 
						return resp;
					}
					// 存储成功
				}
			}
			return AppResponse.ok("ok");
		}
		
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
		// stringRedisTemplate.opsForValue().set(mobile, code.toString());
		// 设置过期时间,15分钟内有效
		stringRedisTemplate.opsForValue().set(mobile, code.toString(), 15, TimeUnit.MINUTES);
		
		log.debug("+++++++++++++++++++++++++++验证码：{}++++++++++++++++++++++++++++++++++++++++++++++++++", code.toString());
		
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
