package com.atguigu.atcrowdfunding.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.bean.TRoleExample;
import com.atguigu.atcrowdfunding.bean.TRolePermission;
import com.atguigu.atcrowdfunding.bean.TRolePermissionExample;
import com.atguigu.atcrowdfunding.mapper.TAdminRoleMapper;
import com.atguigu.atcrowdfunding.mapper.TRoleMapper;
import com.atguigu.atcrowdfunding.mapper.TRolePermissionMapper;
import com.atguigu.atcrowdfunding.service.TRoleService;
import com.github.pagehelper.PageInfo;

@Service
public class TRoleServiceImpl implements TRoleService{

	@Autowired
	TRoleMapper roleMapper;
	
	@Autowired
	TAdminRoleMapper adminRoleMapper;
	
	@Autowired
	TRolePermissionMapper rolePermissionMapper;

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

	@Override
	public List<Integer> getRoleIdByAdminId(Integer id) {
		
		// 没有AdminId,得自己写一个接口，这个接口定义在 用户 和 角色 的 关系表里
		return adminRoleMapper.getRoleIdByAdminId(id);
		
	}

	@Override
	public List<TRole> getAllRole() {
		// 查出所有角色
		return roleMapper.selectByExample(null);
	}

	@Override
	public void saveRoleAndAdminRelationship(Integer[] roleId, Integer adminId) {
		adminRoleMapper.saveRoleAndAdminRelationship(roleId, adminId);
	}

	@Override
	public void deleteRoleAndAdminRelationship(Integer[] roleId, Integer adminId) {
		adminRoleMapper.deleteRoleAndAdminRelationship(roleId, adminId);
	}

	@Override
	public void doAssignPermissionToRole(Integer roleId, List<Integer> ids) {
		// 分配roleid = roleId的角色，ids数组中的permission
		// 可以使用Integer[]接收前台给的json吗 形参传的是 ids 还是 permissionId
		// 还是用Datas
		// 先删除之前保存的这个角色的权限
		
		TRolePermissionExample example = new TRolePermissionExample();
		// 绑定条件
		example.createCriteria().andRoleidEqualTo(roleId);
		rolePermissionMapper.deleteByExample(example);
		// 然后再分配
		rolePermissionMapper.doAssignPermissionToRole(roleId, ids);
	}

	@Override
	public List<Integer> getPermissionIdByRoleId(Integer roleId) {
		
		TRolePermissionExample example = new TRolePermissionExample();
		example.createCriteria().andRoleidEqualTo(roleId);
		List<TRolePermission> selectByExample = rolePermissionMapper.selectByExample(example);
		// 找出所有的TRolePermission对象，把其中的 Permissionid 拿出来
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		for (TRolePermission rolePermission : selectByExample) {
			arrayList.add(rolePermission.getPermissionid());
		}
		return arrayList;
	}

	
}
