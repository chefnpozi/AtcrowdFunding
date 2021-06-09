package com.atguigu.atcrowdfunding.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.mapper.TRoleMapper;
import com.atguigu.atcrowdfunding.service.TRoleService;
import com.github.pagehelper.PageInfo;

@Service
public class TRoleServiceImpl implements TRoleService{

	@Autowired
	TRoleMapper roleMapper;

	@Override
	public PageInfo<TRole> listRolePage(HashMap<String, Object> paramMap) {
		
		// 先查出所有角色，有条件以后再说
		List<TRole> list = roleMapper.selectByExample(null);
		
		// 把这个集合中的对象，按照5个导航页进行分页
		PageInfo<TRole> page = new PageInfo<TRole>(list, 5);
		
		return page;
	}
}
