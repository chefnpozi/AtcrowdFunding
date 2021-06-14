package com.atguigu.atcrowdfunding.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.bean.TPermission;
import com.atguigu.atcrowdfunding.service.TPermissionService;

@Controller
public class PermissionController {

	@Autowired
	TPermissionService permissionService;
	
	@RequestMapping("/permission/index")
	public String index() {
		// 跳转到权限分配主页面
		return "permission/index";
	}
	
	@ResponseBody
	@RequestMapping("/permission/loadTree")
	public List<TPermission> loadTree() {
		// 返回所有的 permission列表，序列化为json
		List<TPermission> list = permissionService.loadAllPermission();
		return list;
	}
	
	
	@ResponseBody
	@RequestMapping("/permission/doAdd")
	public String doAdd(TPermission permission) {
		// 保存当前的permission
		permissionService.saveTPermission(permission);
		return "ok";
	}
	
	
	@ResponseBody
	@RequestMapping("/permission/doDelete")
	public String doDelete(Integer id) {
		// 根据id删除当前的permission
		permissionService.deleteTPermissionById(id);
		return "ok";
	}
	
	
	@ResponseBody
	@RequestMapping("/permission/getPermissionById")
	public TPermission getPermissionById(Integer id) {
		// 根据id拿到当前的permission的 POJO
		TPermission permission = permissionService.getPermissionById(id);
		return permission;
	}
	
	
	@ResponseBody
	@RequestMapping("/permission/doUpdate")
	public String doUpdate(TPermission permission) {
		// 拿一个POJO接收 当前的permission
		permissionService.updateTPermission(permission);
		return "ok";
	}
}
