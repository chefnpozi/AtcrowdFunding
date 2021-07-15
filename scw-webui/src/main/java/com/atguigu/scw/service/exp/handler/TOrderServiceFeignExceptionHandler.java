package com.atguigu.scw.service.exp.handler;

import org.springframework.stereotype.Component;

import com.atguigu.scw.service.TOrderServiceFeign;
import com.atguigu.scw.vo.resp.AppResponse;
import com.atguigu.scw.webui.vo.req.OrderInfoSubmitVo;
import com.atguigu.scw.webui.vo.resp.TOrder;

@Component
public class TOrderServiceFeignExceptionHandler implements TOrderServiceFeign {

	@Override
	public AppResponse<TOrder> saveOrder(OrderInfoSubmitVo orderInfoSubmitVo) {
		
		AppResponse<TOrder> response = AppResponse.fail(null);
		response.setMsg("调用远程服务【保存订单】失败");
		return response;
	}

	
	

}
