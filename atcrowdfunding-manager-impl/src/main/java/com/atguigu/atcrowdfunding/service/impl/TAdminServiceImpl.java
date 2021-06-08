package com.atguigu.atcrowdfunding.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TAdminExample;
import com.atguigu.atcrowdfunding.bean.TAdminExample.Criteria;
import com.atguigu.atcrowdfunding.exception.LoginException;
import com.atguigu.atcrowdfunding.mapper.TAdminMapper;
import com.atguigu.atcrowdfunding.service.TAdminService;
import com.atguigu.atcrowdfunding.util.AppDateUtils;
import com.atguigu.atcrowdfunding.util.Const;
import com.atguigu.atcrowdfunding.util.MD5Util;
import com.github.pagehelper.PageInfo;

@Service
public class TAdminServiceImpl implements TAdminService {

	/**
	 * 验证登录，需要 controller --> service --> dao
	 * 		dao 逆向工程已经生成好了，我们需要写好service，供controller调用
	 */
	@Autowired
	TAdminMapper adminMapper;

	@Override
	public TAdmin getTAdminByLogin(HashMap<String, Object> map) {
		
		//
		// 拿出数据，准备判断
		String loginacct = (String)map.get("loginacct");
		String userpswd = (String)map.get("userpswd");
		
		// 根据已有的信息进行查询，mybatis生成的mapper会以example为判断条件
		// 如果example == null 则会返回所有的数据
		TAdminExample example = new TAdminExample();
		// 创建条件，绑定条件
		example.createCriteria().andLoginacctEqualTo(loginacct);
		// 根据用户名，查出此用户，用户名是唯一的，然后在判断密码是否正确
		List<TAdmin> list = adminMapper.selectByExample(example);
		
		// 只有一个人
		if(list != null && list.size() == 1) {
			TAdmin admin = list.get(0);
			// 判断密码是否一致,采用MD5 加密算法进行加密
			if (admin.getUserpswd().equals(MD5Util.digest(userpswd))) {
				// 一致，返回结果
				return admin;
			} else {
				// 返回null，不太好，那到底是用户名出问题了，还是密码出问题？抛异常比较好
				// 密码出错
				throw new LoginException(Const.LOGIN_USERPSWD_ERROR);
			}
		} else {
			// 用户名出错
			throw new LoginException(Const.LOGIN_LOGINACCT_ERROR);
		}
		
		// return null;
	}

	@Override
	public PageInfo<TAdmin> listAdminPage(Map<String, Object> paraMap) {
		
		TAdminExample example = new TAdminExample();
		
		// 如果没有给condition赋 空串 值，这里强转就会出现空指针异常
		String condition = (String)paraMap.get("condition");
		// 拿出模糊查询的条件，首先先判空
		if (!condition.equals("")) { // 如果条件不为空，给example绑定条件
			// 条件是进行模糊匹配，对于用户名、邮箱、账号都带有相同条件的，我们把它查出来
			Criteria criteria1 = example.createCriteria();
			Criteria criteria2 = example.createCriteria();
			Criteria criteria3 = example.createCriteria();
			// 创建三个条件
			criteria1.andLoginacctLike("%"+condition+"%");
			criteria2.andUsernameLike("%"+condition+"%");
			criteria3.andEmailLike("%"+condition+"%");
			// 将三个条件进行或操作
			example.or(criteria1);
			example.or(criteria2);
			example.or(criteria3);
		}
		
		// 倒序排序,因为已有order by 所以直接写createtime desc
		example.setOrderByClause("createtime desc");
		
		// example 未绑定任何条件，查询效果和null相同，即查询所有
		List<TAdmin> list = adminMapper.selectByExample(example);
		
		// 默认是8个导航页，我们选5个
		PageInfo<TAdmin> page = new PageInfo<TAdmin>(list, 5);
		
		return page;
	}

	@Override
	public void saveTAdmin(TAdmin admin) {
		// 保存该用户的信息到数据库
		
		// 给一个默认密码,使用MD5加密
		admin.setUserpswd(MD5Util.digest(Const.DEFAULT_USERPSWD));
		
		// 使用当前时间作为参数传入，有工具类直接调用就好
		admin.setCreatetime(AppDateUtils.getFormatTime());
		
		// 动态SQL，有选择的保存，没有值的不保存 insert into t_admin(loginacct,username,email) value(?,?,?);
		// 一般我们都选择insertSelective
		adminMapper.insertSelective(admin);
	}

	@Override
	public TAdmin getTAdminById(Integer id) {
		// 根据主键查出该用户
		return adminMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateTAdmin(TAdmin admin) {
		// 更新哪个？需要一个主键，主键在前端界面隐藏域中获取
		// 使用POJO的话，代码的鲁棒性也会增强
		
		// 调取admin中的主键，进行有选择的查询，密码和修改时间先不传入，后来单独做一个修改密码的功能
		adminMapper.updateByPrimaryKeySelective(admin);
	}

	@Override
	public void deleteTAdmin(Integer id) {
		// 根据id从数据库删除该用户
		adminMapper.deleteByPrimaryKey(id);
	}
	
	
}
