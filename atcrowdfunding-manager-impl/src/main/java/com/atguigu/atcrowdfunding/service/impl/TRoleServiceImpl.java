package com.atguigu.atcrowdfunding.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.bean.TRoleExample;
import com.atguigu.atcrowdfunding.mapper.TRoleMapper;
import com.atguigu.atcrowdfunding.service.TRoleService;
import com.github.pagehelper.PageInfo;

@Service
public class TRoleServiceImpl implements TRoleService{

	@Autowired
	TRoleMapper roleMapper;

	@Override
	public PageInfo<TRole> listRolePage(HashMap<String, Object> paramMap) {
		
		TRoleExample example = new TRoleExample();
		
		String condition = (String)paramMap.get("condition");
		// 判空
		if(!StringUtils.isEmpty(condition)) {
			// 绑定条件，只有一个名字，按照他来进行模糊查询
			example.createCriteria().andNameLike("%"+condition+"%");
		}
		// 若是condition == "" 查询所有
		List<TRole> list = roleMapper.selectByExample(example);
		
		// 把这个集合中的对象，按照5个导航页进行分页
		PageInfo<TRole> page = new PageInfo<TRole>(list, 5);
		
		return page;
	}

	@Override
	public void saveTRole(TRole role) {
		// 有选择性的把role保存到数据库
		roleMapper.insertSelective(role);
	}

	@Override
	public TRole getTRoleById(Integer id) {
		// 通过id返回这个角色
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateTRole(TRole role) {
		// 修改role
		roleMapper.updateByPrimaryKey(role);
	}

	@Override
	public void deleteTRole(Integer id) {
		roleMapper.deleteByPrimaryKey(id);
	}
}
