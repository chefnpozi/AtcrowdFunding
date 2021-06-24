package com.atguigu.controller.project;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.bean.AppResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "项目信息模块")
@RequestMapping("/project")
@RestController
public class ProjectInfoController {

	@ApiOperation(value="获取首页广告项目") 
	@GetMapping("/adv")
	public AppResponse<Object> adv(){
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="获取首页热门推荐项目") 
	@GetMapping("/recommend/index")
	public AppResponse<Object> index(){
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="获取首页分类推荐项目") 
	@GetMapping("/recommend/type")
	public AppResponse<Object> type(){
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="获取项目总览列表") 
	@GetMapping("/all/index")
	public AppResponse<Object> all(){
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="获取项目系统分类信息") 
	@GetMapping("/sys/type")
	public AppResponse<Object> systype(){
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="获取项目系统标签信息") 
	@GetMapping("/sys/tags")
	public AppResponse<Object> systags(){
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="获取项目详情信息") 
	@GetMapping("/info/detail")
	public AppResponse<Object> detail(){
		return AppResponse.ok("ok");
	} 
	
	@ApiOperation(value="获取项目回报档位信息") 
	@GetMapping("/return/info")
	public AppResponse<Object> support(){
		return AppResponse.ok("ok");
	} 	

	
}
