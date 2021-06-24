package com.atguigu.controller.pay;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.bean.AppResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "订单模块")
@RequestMapping("/order")
@RestController
public class OrderController {
	
	@ApiOperation(value = "创建订单")
	@PostMapping("/create")
	public AppResponse<Object> create() {
		return AppResponse.ok("ok");
	}
	
	@ApiOperation(value = "立即付款")
	@PostMapping("/pay")
	public AppResponse<Object> pay() {
		return AppResponse.ok("ok");
	}
	
	@ApiOperation(value = "取消订单")
	@PostMapping("/cancle")
	public AppResponse<Object> cancle() {
		return AppResponse.ok("ok");
	}
	
	

}
