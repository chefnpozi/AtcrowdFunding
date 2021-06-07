package com.atguigu.atcrowdfunding.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.bean.TMenu;
import com.atguigu.atcrowdfunding.mapper.TMenuMapper;
import com.atguigu.atcrowdfunding.service.TMenuService;

// 可记得把对应的注解加上去
@Service
public class TMenuServiceImpl implements TMenuService{

	@Autowired
	TMenuMapper menuMapper;
	
	@Override
	public List<TMenu> listMenuAll() {
		
		Logger log = LoggerFactory.getLogger(TMenuServiceImpl.class);
		
		// example == null ，查询出的是所有数据
		List<TMenu> allList = menuMapper.selectByExample(null);
		// 返回的是父菜单的列表，子菜单要作为父菜单的属性
		ArrayList<TMenu> menuList = new ArrayList<TMenu>();
		// 为了快速找到父菜单，创建一个map集合
		HashMap<Integer, TMenu> cache = new HashMap<Integer, TMenu>();
		
		
		for (TMenu tMenu : allList) {
			if (tMenu.getPid() == 0) {
				// 证明这个菜单是父菜单,加入父菜单
				menuList.add(tMenu);
				// 方便查找
				cache.put(tMenu.getId(), tMenu);
				
			}
		}
		
		// 不要使用else，万一你的父菜单还没收到集合里去
		
		for (TMenu tMenu : allList) {
			Integer pid = tMenu.getPid();
			if (pid != 0) {
				// 证明是子菜单,是孩子菜单,那么通过pid去找到父亲id
				TMenu parent = cache.get(pid);
				// 把当前孩子收容到父亲的 Children 集合中
				parent.getChildren().add(tMenu);
			}
		}
		
		// 打印日志
		log.debug("菜单={}", menuList);
		
		// 返回父菜单的集合
		return menuList;
	}

}
