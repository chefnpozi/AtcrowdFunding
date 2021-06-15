package com.atguigu.atcrowdfunding.service;

import java.util.List;

import com.atguigu.atcrowdfunding.bean.TPermission;

public interface TPermissionService {

	List<TPermission> loadAllPermission();

	void saveTPermission(TPermission permission);

	void deleteTPermissionById(Integer id);

	TPermission getPermissionById(Integer id);

	void updateTPermission(TPermission permission);

	List<TPermission> getAllPermission();

}
