package com.atguigu.scw.user.enums;

public enum UserExceptionEnum {

	
	USER_EXISTS(1, "用户已存在"),
	EMAIL_EXIST(2,"邮箱已经存在"),
	LOGINACCT_LOCKED(3,"账号已经被锁定"),
	USER_SAVE_ERROR(4, "用户保存失败"), 
	USER_UNEXISTS(5, "用户不存在"), 
	USER_PASSWORD_ERROR(6, "用户密码错误");
	
	private int code;
	
	private String message;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	private UserExceptionEnum() {
	}

	private UserExceptionEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public String toString() {
		return "UserExceptionEnum [code=" + code + ", message=" + message + "]";
	}
	
	
}
