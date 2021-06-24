package com.atguigu.controller.project;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.bean.AppResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "项目操作模块")
@RequestMapping("/project/operation")
@RestController
public class ProjectOperationController {

	@ApiOperation(value = "项目关注")
	@PostMapping("/star")
	public AppResponse<Object> addstar() {
		return AppResponse.ok("ok");
	}
	
	@ApiOperation(value = "项目取消关注")
	@DeleteMapping("/star")
	public AppResponse<Object> star() {
		return AppResponse.ok("ok");
	}
	
	@ApiOperation(value = "项目预览")
	@GetMapping("/preshow")
	public AppResponse<Object> preshow() {
		return AppResponse.ok("ok");
	}
	
	
	@ApiOperation(value = "项目修改")
	@PutMapping("/update")
	public AppResponse<Object> update() {
		return AppResponse.ok("ok");
	}
	
	@ApiOperation(value = "项目删除")
	@PutMapping("/delete")
	public AppResponse<Object> delete() {
		return AppResponse.ok("ok");
	}
	
	@ApiOperation(value = "项目问题查看")
	@GetMapping("/question")
	public AppResponse<Object> question() {
		return AppResponse.ok("ok");
	}
	
	@ApiOperation(value = "项目问题添加")
	@PostMapping("/question")
	public AppResponse<Object> addQuestion() {
		return AppResponse.ok("ok");
	}
	
	@ApiOperation(value = "项目问题答案添加")
	@PostMapping("/question/answer")
	public AppResponse<Object> addAnswer() {
		return AppResponse.ok("ok");
	}
	
}
