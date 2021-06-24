package com.atguigu.springcloud.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.springcloud.bean.User;
import com.atguigu.springcloud.dao.UserDao;
import com.atguigu.springcloud.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao ;

	@Override
	public User getUserById(Integer id) {
		return userDao.getUserById(id);
	}
	
}
