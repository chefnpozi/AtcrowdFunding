package com.atguigu.scw.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.atguigu.scw.order.bean.TReturn;
import com.atguigu.scw.order.service.exp.handler.TProjectServiceFeignExceptionHandler;
import com.atguigu.scw.vo.resp.AppResponse;

import io.swagger.annotations.ApiOperation;

@FeignClient(value="SCW-PROJECT",fallback=TProjectServiceFeignExceptionHandler.class)
public interface TProjectServiceFeign {

	
	@GetMapping("/project/details/returns/info/{returnId}")
	public AppResponse<TReturn> returnInfo(@PathVariable("returnId") Integer returnId);
	
}
