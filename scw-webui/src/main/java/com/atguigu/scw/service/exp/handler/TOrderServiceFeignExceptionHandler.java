package com.atguigu.scw.service.exp.handler;

import org.springframework.stereotype.Component;

import com.atguigu.scw.service.TOrderServiceFeign;
import com.atguigu.scw.vo.resp.AppResponse;
import com.atguigu.scw.webui.vo.req.OrderInfoSubmitVo;
import com.atguigu.scw.webui.vo.resp.TOrder;

@Component
public class TOrderServiceFeignExceptionHandler implements TOrderServiceFeign {

	
	public AppResponse<TOrder> saveOrder(OrderInfoSubmitVo orderInfoSubmitVo) {
		AppResponse<TOrder> resp=AppResponse.fail(null);
		resp.setMsg("调用远程服务保存订单失败");
		
		
		return resp;
	}

}
