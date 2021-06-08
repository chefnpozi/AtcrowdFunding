package com.atguigu.atcrowdfunding.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.github.pagehelper.PageInfo;

public interface TAdminService {

	TAdmin getTAdminByLogin(HashMap<String, Object> map);

	// 接口中创建不了，先看看是不是 少导入一个jar包
	PageInfo<TAdmin> listAdminPage(Map<String, Object> paraMap);

	void saveTAdmin(TAdmin admin);

	TAdmin getTAdminById(Integer id);

	void updateTAdmin(TAdmin admin);

	void deleteTAdmin(Integer id);

	void deleteBatchTAdmin(ArrayList<Integer> array);
	

}
