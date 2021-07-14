package com.atguigu.scw.service.exp.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.atguigu.scw.service.TMemberServiceFeign;
import com.atguigu.scw.vo.resp.AppResponse;
import com.atguigu.scw.webui.vo.resp.UserAddressVo;
import com.atguigu.scw.webui.vo.resp.UserRespVo;

@Component
public class TMemberServiceFeignExceptionhandler implements TMemberServiceFeign {

	@Override
	public AppResponse<UserRespVo> login(String loginacct, String password) {
		
		AppResponse<UserRespVo> resp=AppResponse.fail(null);
		resp.setMsg("调用远程服务[登录]失败");
		
		
		return resp;
	}

	@Override
	public AppResponse<List<UserAddressVo>> address(String accessToken) {
		AppResponse<List<UserAddressVo>> resp=AppResponse.fail(null);
		resp.setMsg("调用远程服务[根据用户查询地址]失败");
		
		
		return resp;
	}

}
