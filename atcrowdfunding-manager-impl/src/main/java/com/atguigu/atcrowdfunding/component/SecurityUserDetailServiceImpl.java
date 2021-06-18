package com.atguigu.atcrowdfunding.component;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TAdminExample;
import com.atguigu.atcrowdfunding.bean.TPermission;
import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.mapper.TAdminMapper;
import com.atguigu.atcrowdfunding.mapper.TPermissionMapper;
import com.atguigu.atcrowdfunding.mapper.TRoleMapper;

// 纳入IOC容器进行管理
@Component
public class SecurityUserDetailServiceImpl implements UserDetailsService {

	
	// 查询用户信息
	@Autowired
	TAdminMapper adminMapper;
	
	@Autowired
	TRoleMapper roleMapper;
	
	@Autowired
	TPermissionMapper permissionMapper;
	
	/*
	 * 这一步实际上是做了我们自己写的doLogin，查询数据库，对来访者的信息进行核实，通过传进来的username，查出数据库中的那一条信息，
	 * 封装为UserDetails返回
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		// 1.查询用户对象
		TAdminExample example = new TAdminExample();
		// spring security 传进来的 username 实际上是数据库中的 loginacct
		example.createCriteria().andLoginacctEqualTo(username);
		List<TAdmin> list = adminMapper.selectByExample(example);
		
		if (list != null && list.size() == 1) {
			
			// 根据 loginacct 返回这条信息
			TAdmin admin = list.get(0);
			Integer adminId = admin.getId();
			
			// 2.查询角色集合
			// 通过adminId --> 用户角色关系表join角色表 --> 角色集合信息
			List<TRole> roleList = roleMapper.roleListByAdminId(adminId);
			
			// 3.查询权限集合
			// 通过adminId --> 权限集合信息   	==	 adminId --> 用户表 --> 用户角色关系表 --> 角色表 --> 角色权限关系表 --> 权限表 --> permission
			List<TPermission> permissionList = permissionMapper.permissionListByAdminId(adminId);
			
			// 4.构建用户所有权限集合 ==> （角色+权限）
			// 把上述的两个集合中的元素封装成 权限对象加入此集合
			HashSet<GrantedAuthority> authoritis = new HashSet<GrantedAuthority>();
			
			for (TRole role : roleList) {
				// 角色加入权限集合 需要再前面+ ROLE_
				// SimpleGrantedAuthority 是 GrantedAuthority 的一个实现类，形参为String
				authoritis.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
			}
			
			for (TPermission permission : permissionList) {
				// permission.getName() 拿到的是带有前缀的权限
				authoritis.add(new SimpleGrantedAuthority(permission.getName()));
			}
			
			// 形参 ： 账户名 密码 权限集合
			return new User(admin.getLoginacct(), admin.getUserpswd(), authoritis);
		}else {
			return null;
		}
	}

}
