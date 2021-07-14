package com.atguigu.scw.service.exp.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.atguigu.scw.service.TProjectServiceFeign;
import com.atguigu.scw.vo.resp.AppResponse;
import com.atguigu.scw.webui.vo.resp.ProjectDetailVo;
import com.atguigu.scw.webui.vo.resp.ProjectVo;
import com.atguigu.scw.webui.vo.resp.ReturnPayConfirmVo;

@Component
public class TProjectServiceFeignExceptionHandler implements TProjectServiceFeign {

	@Override
	public AppResponse<List<ProjectVo>> all() {
		AppResponse resp=AppResponse.fail(null);
		resp.setMsg("调用远程服务查询首页热点项目失败");
		
		
		return resp;
	}

	@Override
	public AppResponse<ProjectDetailVo> detailsInfo(Integer projectId) {
		AppResponse resp=AppResponse.fail(null);
		resp.setMsg("调用远程服务项目详情失败");
		
		
		return resp;
	}

	@Override
	public AppResponse<ReturnPayConfirmVo> returnInfo(Integer projectId, Integer returnId) {
		AppResponse resp=AppResponse.fail(null);
		resp.setMsg("调用远程服务查询确认回报失败");
		
		
		return resp;
	}
}
