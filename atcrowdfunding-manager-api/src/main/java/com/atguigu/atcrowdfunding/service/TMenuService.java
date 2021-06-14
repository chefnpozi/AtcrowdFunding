package com.atguigu.atcrowdfunding.service;

import java.util.List;

import com.atguigu.atcrowdfunding.bean.TMenu;

public interface TMenuService {

	List<TMenu> listMenuAll(); 		// 组合父子关系 

	List<TMenu> listAllTreeMenu();	// 不用组合父子关系

	void addTMenu(TMenu menu);

	void deleteTMenu(Integer id);

	TMenu getMenuById(Integer id);

	void updateTMenu(TMenu menu);
}
