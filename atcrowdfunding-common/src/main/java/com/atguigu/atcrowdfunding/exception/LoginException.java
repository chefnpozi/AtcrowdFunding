package com.atguigu.atcrowdfunding.exception;

// 不继承的话是编译期异常，事务不回滚，继承RuntimeException,事务回滚
public class LoginException extends RuntimeException{
	
	// 声明俩构造器就完事了
	
	public LoginException() {};
	
	public LoginException(String message) {
		
		// 有参也只需传给父类就完事
		super(message);
	}
	
}
