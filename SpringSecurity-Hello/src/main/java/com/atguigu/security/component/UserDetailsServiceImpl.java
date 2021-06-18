package com.atguigu.security.component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		String queryUser = "SELECT * FROM `t_admin` WHERE loginacct=?";

		// 1、查询指定用户的信息
		Map<String, Object> map = jdbcTemplate.queryForMap(queryUser, username);

		// 查询用户拥有的角色集合
		String sql1 = "SELECT t_role.* FROM t_role LEFT JOIN t_admin_role ON t_admin_role.roleid=t_role.id WHERE t_admin_role.adminid=?";
		List<Map<String, Object>> roleList = jdbcTemplate.query(sql1, new ColumnMapRowMapper(), map.get("id"));

		// 查询用户拥有的权限集合
		String sql2 = "SELECT distinct t_permission.* FROM t_permission LEFT JOIN t_role_permission ON t_role_permission.permissionid = t_permission.id LEFT JOIN t_admin_role ON t_admin_role.roleid=t_role_permission.roleid WHERE t_admin_role.adminid=?";
		List<Map<String, Object>> permissionList = jdbcTemplate.query(sql2, new ColumnMapRowMapper(), map.get("id"));

		// 用户权限=【角色+权限】
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

		for (Map<String, Object> rolemap : roleList) {
			String rolename = rolemap.get("name").toString();
			authorities.add(new SimpleGrantedAuthority("ROLE_" + rolename));
		}
		for (Map<String, Object> permissionmap : permissionList) {
			String permissionName = permissionmap.get("name").toString();
			if (!StringUtils.isEmpty(permissionName)) {
				authorities.add(new SimpleGrantedAuthority(permissionName));
			}
		}

		// return new
		// User(map.get("loginacct").toString(),map.get("userpswd").toString(),
		// AuthorityUtils.createAuthorityList("ADMIN","USER"));
		return new User(map.get("loginacct").toString(), map.get("userpswd").toString(), authorities);

	}

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//		String queryUser = "SELECT * FROM `t_admin` WHERE loginacct=?";
//
//		// 1、查询指定用户的信息
//		Map<String, Object> map = jdbcTemplate.queryForMap(queryUser, username);
//
//		// 2、将查询到的用户封装到框架使用的UserDetails里面
//		return new User(map.get("loginacct").toString(), map.get("userpswd").toString(),
//				AuthorityUtils.createAuthorityList("ROLE_学徒", "罗汉拳"));// 暂时写死，过后数据库中查，角色需要写出ROLE_这个前缀，权限不需要
//	}

}
