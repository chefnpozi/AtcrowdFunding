package com.atguigu.atcrowdfunding.service;

import java.util.HashMap;

import com.atguigu.atcrowdfunding.bean.TRole;
import com.github.pagehelper.PageInfo;

public interface TRoleService {

	PageInfo<TRole> listRolePage(HashMap<String, Object> paramMap);

}
