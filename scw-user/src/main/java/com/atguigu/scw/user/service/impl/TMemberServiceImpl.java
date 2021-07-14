package com.atguigu.scw.user.service.impl;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.javassist.compiler.ast.Member;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.atguigu.scw.user.bean.TMember;
import com.atguigu.scw.user.bean.TMemberAddress;
import com.atguigu.scw.user.bean.TMemberAddressExample;
import com.atguigu.scw.user.bean.TMemberExample;
import com.atguigu.scw.user.enums.UserExceptionEnum;
import com.atguigu.scw.user.exception.UserException;
import com.atguigu.scw.user.mapper.TMemberAddressMapper;
import com.atguigu.scw.user.mapper.TMemberMapper;
import com.atguigu.scw.user.service.TMemberService;
import com.atguigu.scw.user.vo.req.UserRegistVo;
import com.atguigu.scw.user.vo.resp.UserRespVo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly=true)
public class TMemberServiceImpl implements TMemberService{

	@Autowired
	TMemberMapper memberMapper;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;

	@Autowired
	TMemberAddressMapper memberAddressMapper;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.REPEATABLE_READ) // 传播级别和隔离是做什么
	public int saveTMember(UserRegistVo vo) {
		
		try {
			// 选择性插入的是member对象，所以先将 vo 对象对拷到 do 对象
			TMember member = new TMember();
			// 对拷的工具类
			BeanUtils.copyProperties(vo, member);
			// username 有不为空的 约束
			member.setUsername(vo.getLoginacct());
			
			String userpswd = vo.getUserpswd();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			// 密码加密
			member.setUserpswd(encoder.encode(userpswd));
			
			log.debug("注册会员成功-{}",member);
			
			int flag = memberMapper.insertSelective(member);
			
			return flag;
		} catch (Exception e) {
			
			e.printStackTrace();
			log.error("注册会员失败-{}",e.getMessage());
//			throw new RuntimeException("保存会员失败");
			// 调用枚举类的构造器，生成对象
			throw new UserException(UserExceptionEnum.USER_SAVE_ERROR);
		}
	}

	@Override
	public UserRespVo getUserByLogin(String loginacct, String password) {
		
		UserRespVo vo = new UserRespVo();
		
		TMemberExample example = new TMemberExample();
		example.createCriteria().andLoginacctEqualTo(loginacct);
		
		List<TMember> list = memberMapper.selectByExample(example);
		
		if (list == null || list.size() != 1) {
			throw new UserException(UserExceptionEnum.USER_UNEXISTS);
		}
		// 用户存在且唯一
		TMember member = list.get(0);
		// 判断密码是否正确
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		// !member.getUserpswd().equals(encoder.encode(password))
		// 因为是加盐加密，所以不能使用equals，调用match方法匹配 原密码 与 数据库中加密后的密码
		if (!encoder.matches(password, member.getUserpswd())) {
			throw new UserException(UserExceptionEnum.USER_PASSWORD_ERROR);
		}
		// 用户存在，且密码相等 对拷
		BeanUtils.copyProperties(member, vo);
		
		// member 中没有 token 值 , 使用 UUID 生成一个通用唯一标识符
		String accessToken = UUID.randomUUID().toString().replaceAll("-", "");
		
		vo.setAccessToken(accessToken);
		// 加载至redis，方便以后校验, 校验是否来过，存id就好，需要时再去数据库中查
		stringRedisTemplate.opsForValue().set(accessToken, member.getId().toString());
		
		return vo;
	}

	@Override
	public TMember getMemberById(Integer id) {
		// memberId 是主键
		TMemberExample example = new TMemberExample();
		example.createCriteria().andIdEqualTo(id);
		List<TMember> list = memberMapper.selectByExample(example);
		TMember member = list.get(0);
		return member;
	}

	@Override
	public List<TMemberAddress> listAddress(int memberId) {
		TMemberAddressExample example = new TMemberAddressExample();
		example.createCriteria().andMemberidEqualTo(memberId);
		
		return memberAddressMapper.selectByExample(example);
	}
}
