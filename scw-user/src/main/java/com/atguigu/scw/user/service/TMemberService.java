package com.atguigu.scw.user.service;

import com.atguigu.scw.user.vo.req.UserRegistVo;
import com.atguigu.scw.user.vo.resp.UserRespVo;

public interface TMemberService {

	int saveTMember(UserRegistVo vo);

	UserRespVo getUserByLogin(String loginacct, String password);

}
