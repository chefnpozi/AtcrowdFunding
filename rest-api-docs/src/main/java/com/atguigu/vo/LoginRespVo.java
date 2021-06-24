package com.atguigu.vo;

import com.atguigu.entity.TMember;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class LoginRespVo {
	
	@ApiModelProperty("访问令牌，后续的请求需要提交访问令牌，请妥善保存")
	private String accessToken;
	@ApiModelProperty("返回的用户信息对象")
	private TMember member;
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public TMember getMember() {
		return member;
	}
	public void setMember(TMember member) {
		this.member = member;
	}
	

	

}
