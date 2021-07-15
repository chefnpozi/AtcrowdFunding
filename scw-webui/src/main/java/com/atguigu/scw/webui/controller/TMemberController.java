package com.atguigu.scw.webui.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.scw.webui.vo.req.OrderFormInfoSubmitVo;

@Controller
public class TMemberController {
   
	
	@RequestMapping("/member/minecrowdfunding")
	public String toMinecrowdfunding() {
		return "/member/minecrowdfunding";
	}
}
