package com.atguigu.atcrowdfunding.component;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.atguigu.atcrowdfunding.bean.TAdmin;


public class TSecurityAdmin extends User {

	// 拿到了数据库中的这个对象
	TAdmin admin ;
	
	public TSecurityAdmin(TAdmin admin, Set<GrantedAuthority> authoritis) {
//		super(admin.getLoginacct(), admin.getUserpswd(), authoritis);
		super(admin.getLoginacct(), admin.getUserpswd(), true, true, true, true, authoritis);
		this.admin = admin;
	}
}
