package com.atguigu.scw.webui.controller;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.atguigu.scw.service.TMemberServiceFeign;
import com.atguigu.scw.service.TProjectServiceFeign;
import com.atguigu.scw.vo.resp.AppResponse;
import com.atguigu.scw.webui.vo.resp.ProjectVo;
//import com.atguigu.scw.webui.service.TProjectServiceFeign;
//import com.atguigu.scw.webui.vo.resp.ProjectVo;
import com.atguigu.scw.webui.vo.resp.UserRespVo;

@Controller
public class DispatcherController {

	@Autowired
	TMemberServiceFeign memberServiceFeign;
	
	@Autowired
	TProjectServiceFeign projectServiceFeign;
	
	@Autowired
	RedisTemplate redisTemplate;
	
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		
		if(session!=null) {
			session.removeAttribute("loginMember");
			session.invalidate();
		}
		
		return "redirect:/index";
	}	
	
	@RequestMapping("/doLogin")
	public String doLogin(String loginacct,String userpswd,HttpSession session) {
		System.out.print("*************");
		AppResponse<UserRespVo> resp = memberServiceFeign.login(loginacct, userpswd);
		
		UserRespVo data = resp.getData();
		
		if(data==null) {
			return "login";
		}
		
		session.setAttribute("loginMember", data);
		
		String preUrl=(String)session.getAttribute("preUrl");
		if(StringUtils.isEmpty(preUrl)) {
			return "redirect:/index";
		}else
		{
			return "redirect:"+preUrl;
		}
		
		
	}
	
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}	
	
	
	
	
	
	
	//如果Controller方法只是跳转页面，不做其他操作。可以配置mvc:view-controller标签。
	//<mvc:view-controller path="/index" view-name="index"/>
	//@RequestMapping("/index")
	//public String index(Model model) {
		
		//List<ProjectVo> data = (List<ProjectVo>) redisTemplate.opsForValue().get("projectInfo");
		
		//if(data==null) {
			//AppResponse<List<ProjectVo>> resp = projectServiceFeign.all();
			//data = resp.getData();
			//redisTemplate.opsForValue().set("projectInfo", data,1,TimeUnit.HOURS);
		//}
		
		//model.addAttribute("projectVoList", data);
		
		//return "index";
	//}
	
	@RequestMapping("/index")
	public String index(Model model) {
		List<ProjectVo> data= (List<ProjectVo>)redisTemplate.opsForValue().get("projectInfo");
		if(data==null) {
			// 通过feign接口进行远程调用服务
			AppResponse<List<ProjectVo>> resp=projectServiceFeign.all();
			data=resp.getData();
			// 存入缓存，方便下次查找
			redisTemplate.opsForValue().set("projectInfo",data,1,TimeUnit.HOURS);
		}
		model.addAttribute("ProjectVoList",data);
		
		return "index";
	}
	
}
