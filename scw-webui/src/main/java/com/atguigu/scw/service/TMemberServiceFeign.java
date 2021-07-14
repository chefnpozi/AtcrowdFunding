package com.atguigu.scw.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.scw.service.exp.handler.TMemberServiceFeignExceptionhandler;
import com.atguigu.scw.vo.resp.AppResponse;
import com.atguigu.scw.webui.vo.resp.UserAddressVo;
import com.atguigu.scw.webui.vo.resp.UserRespVo;


@FeignClient(value="SCW-USER",fallback=TMemberServiceFeignExceptionhandler.class)
public interface TMemberServiceFeign {

	@PostMapping("/user/login")
	public AppResponse<UserRespVo> login(@RequestParam("loginacct")  String loginacct, @RequestParam("password") String password);

	// 从token找到用户，再找到用户的地址列表
	@GetMapping("/user/info/address")
	public AppResponse<List<UserAddressVo>> address(@RequestParam("accessToken")  String accessToken);
}
