package com.atguigu.springcloud.dao;

import org.springframework.stereotype.Repository;

import com.atguigu.springcloud.bean.User;

@Repository
public class UserDao {

	public User getUserById(Integer id) {
		User user = new User();
		user.setId(id);
		user.setName("zhangsan-"+id);
		return user;
	}

}
