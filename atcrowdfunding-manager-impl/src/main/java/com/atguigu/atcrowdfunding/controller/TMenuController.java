package com.atguigu.atcrowdfunding.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.bean.TMenu;
import com.atguigu.atcrowdfunding.service.TMenuService;

@Controller
public class TMenuController {
	
	@Autowired
	TMenuService menuService;

	@RequestMapping("/menu/index")
	public String index() {
		return "menu/index";
	}
	
	@ResponseBody
	@RequestMapping("/menu/loadTree")
	public List<TMenu> loadTree() {
		
		List<TMenu> list = menuService.listAllTreeMenu();
		
		// 以json形式返回 List<TMenu> 
		return list;
	}
	
	
	@ResponseBody
	@RequestMapping("/menu/doAdd")
	public String doAdd(TMenu menu) {
		
		menuService.addTMenu(menu);
		
		// 返回 ok 表示成功
		return "ok";
	}
	
	
	@ResponseBody
	@RequestMapping("/menu/doDelete")
	public String doDelete(Integer id) {
		
		menuService.deleteTMenu(id);
		
		// 返回 ok 表示成功
		return "ok";
	}
	
	
	@ResponseBody
	@RequestMapping("/menu/getMenuById")
	public TMenu getMenuById(Integer id) {
		
		TMenu menu = menuService.getMenuById(id);
		
		// 返回 ok 表示成功
		return menu;
	}
	
	
	@ResponseBody
	@RequestMapping("/menu/doUpdate")
	public String doUpdate(TMenu menu) {
		// 传过来的参数，使用一个POJO接收，记得传参的时候名字要对应
		
		menuService.updateTMenu(menu);
		
		// 返回 ok 表示成功
		return "ok";
	}
	
}
