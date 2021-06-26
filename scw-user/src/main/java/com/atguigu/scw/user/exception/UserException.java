package com.atguigu.scw.user.exception;

import com.atguigu.scw.user.enums.UserExceptionEnum;

public class UserException extends RuntimeException { // 运行时异常会导致事务回滚

	public UserException() {
		
	}
	
	public UserException(UserExceptionEnum userSaveError) {
		super(userSaveError.getMessage());
	}

	
}
