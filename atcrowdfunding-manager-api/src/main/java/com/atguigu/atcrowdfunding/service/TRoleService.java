package com.atguigu.atcrowdfunding.service;

import java.util.HashMap;
import java.util.List;

import com.atguigu.atcrowdfunding.bean.TRole;
import com.github.pagehelper.PageInfo;

public interface TRoleService {

	PageInfo<TRole> listRolePage(HashMap<String, Object> paramMap);

	void saveTRole(TRole role);

	TRole getTRoleById(Integer id);

	void updateTRole(TRole role);

	void deleteTRole(Integer id);

	List<Integer> getRoleIdByAdminId(Integer id);

	List<TRole> getAllRole();

	void saveRoleAndAdminRelationship(Integer[] roleId, Integer adminId);

	void deleteRoleAndAdminRelationship(Integer[] roleId, Integer adminId);

	void doAssignPermissionToRole(Integer roleId, List<Integer> ids);

	List<Integer> getPermissionIdByRoleId(Integer roleId);

	

}
