package com.atguigu.scw.project.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.scw.project.bean.TMember;
import com.atguigu.scw.project.service.exp.handler.MemberServiceFeignExceptionHandler;
import com.atguigu.scw.vo.resp.AppResponse;


@FeignClient(value="SCW-USER",fallback=MemberServiceFeignExceptionHandler.class)
public interface MemberServiceFeign {

	
	@GetMapping("/user/info/getMemberById")
	public AppResponse<TMember> getMemberById(@RequestParam("id") Integer id);
}
