package com.atguigu.atcrowdfunding.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.service.TRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class TRoleController {

	@Autowired
	TRoleService roleService;
	
	@RequestMapping("/role/index")
	public String index() {
		// 跳转到角色主页面
		return "role/index";
	}
	
	@ResponseBody
	@RequestMapping("/role/loadData")
	public PageInfo<TRole> loadData(@RequestParam(value="pageNum", required=false, defaultValue="1")Integer pageNum,
									@RequestParam(value="pageSize", required=false, defaultValue="2")Integer pageSize) {
		// 主页面已经加载完成，接收Ajax请求，返回page分页信息
		
		// 线程 绑定
		PageHelper.startPage(pageNum, pageSize);
		
		// 存放参数的集合
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		// paramMap.put(key, value);
		
		// 拿到符合pageNum, pageSize的分页信息
		PageInfo<TRole> page = roleService.listRolePage(paramMap);
		// 直接返回信息，不进行拼串，序列化成json的方式返回到界面的result，然后界面在进行处理
		// 如果是对象，序列化为json串
		// 如果是String，原样输出
		return page;
	}
}
