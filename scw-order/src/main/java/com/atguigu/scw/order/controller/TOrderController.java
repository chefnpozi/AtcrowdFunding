package com.atguigu.scw.order.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.scw.order.bean.TOrder;
import com.atguigu.scw.order.service.TOrderService;
import com.atguigu.scw.order.vo.req.OrderInfoSubmitVo;
import com.atguigu.scw.vo.resp.AppResponse;


@RestController
public class TOrderController {
	
	@Autowired
	TOrderService orderService;

	
	
	@RequestMapping("/order/saveOrder")
	public AppResponse<TOrder> saveOrder(@RequestBody OrderInfoSubmitVo orderInfoSubmitVo) {
		
		try {
			TOrder order = orderService.saveOrder(orderInfoSubmitVo);
			
			AppResponse<TOrder> resp = AppResponse.ok(order);
			return resp;
		} catch (Exception e) {
			AppResponse<TOrder> resp = AppResponse.fail(null);
			resp.setMsg("保存订单失败");
			e.printStackTrace();
			return resp;
		}
	}
}
