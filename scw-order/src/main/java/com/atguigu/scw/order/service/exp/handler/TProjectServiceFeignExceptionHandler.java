package com.atguigu.scw.order.service.exp.handler;

import org.springframework.stereotype.Component;

import com.atguigu.scw.order.bean.TReturn;
import com.atguigu.scw.order.service.TProjectServiceFeign;
import com.atguigu.scw.vo.resp.AppResponse;

@Component
public class TProjectServiceFeignExceptionHandler implements TProjectServiceFeign {

	@Override
	public AppResponse<TReturn> returnInfo(Integer returnId) {
		AppResponse<TReturn> response = AppResponse.fail(null);
		response.setMsg("调用远程服务失败【返回回报信息returnInfo】");
		return response;
	}

}
