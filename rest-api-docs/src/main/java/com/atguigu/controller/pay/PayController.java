package com.atguigu.controller.pay;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.bean.AppResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "支付模块")
@RequestMapping("/order/pay")
@RestController
public class PayController {
	
	@ApiOperation(value = "支付宝支付")
	@PostMapping("/alipay")
	public AppResponse<Object> alipay() {
		return AppResponse.ok("ok");
	}
	
	@ApiOperation(value = "微信支付")
	@PostMapping("/weixin")
	public AppResponse<Object> weixin() {
		return AppResponse.ok("ok");
	}

}
