package com.atguigu.security.component;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderImpl implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		// 使用MD5加密算法进行加密，框架自己调用
		return MD5Util.digest(rawPassword.toString());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		// encodedPassword 数据库中的密文
		return encodedPassword.equals(MD5Util.digest(rawPassword.toString()));
	}

}
