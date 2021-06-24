package com.atguigu.enums;

/**
 * 项目状态
 */
public enum ProjectStatusEnume {
	
	DRAFT((byte)0,"草稿"),
	SUBMIT_AUTH((byte)1,"提交审核申请"),
	AUTHING((byte)2,"后台正在审核"),
	AUTHED((byte)3,"后台审核通过"),
	AUTHFAIL((byte)4,"审核失败");
	
	private byte code;
	private String status;
	public byte getCode() {
		return code;
	}
	public void setCode(byte code) {
		this.code = code;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	private ProjectStatusEnume(byte code, String status) {
		this.code = code;
		this.status = status;
	}
	

}
