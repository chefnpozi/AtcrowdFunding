package com.atguigu.atcrowdfunding.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.bean.TPermission;
import com.atguigu.atcrowdfunding.mapper.TPermissionMapper;
import com.atguigu.atcrowdfunding.service.TPermissionService;

@Service
public class TPermissionServiceImpl implements TPermissionService {

	@Autowired
	TPermissionMapper permissionMapper;

	@Override
	public List<TPermission> loadAllPermission() {
		// 查出所有的 permission		
		return permissionMapper.selectByExample(null);
	}

	@Override
	public void saveTPermission(TPermission permission) {
		permissionMapper.insertSelective(permission);
	}

	@Override
	public void deleteTPermissionById(Integer id) {
		permissionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public TPermission getPermissionById(Integer id) {
		return permissionMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateTPermission(TPermission permission) {
		permissionMapper.updateByPrimaryKeySelective(permission);
	}
}
