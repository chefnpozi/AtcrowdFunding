package com.atguigu.atcrowdfunding.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class AtcrowdFundingSecurityConfig extends WebSecurityConfigurerAdapter {

	// 注入服务对象，这个服务对象应该和数据库有关
	@Autowired
	UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// super.configure(auth);
		
		// 数据库校验账号密码，加载权限信息。密码编码器、加盐
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// super.configure(http);
		
		http.authorizeRequests().antMatchers("/static/**","/welcome.jsp").permitAll()
		.anyRequest().authenticated();//剩下都需要认证
		 
		// /login.jsp==POST  用户登陆请求发给Security
		http.formLogin().loginPage("/login")
		.usernameParameter("loginacct").passwordParameter("userpswd")
		.defaultSuccessUrl("/main").permitAll();
		 
		http.csrf().disable();
		
		// 找我们的映射
		http.logout().logoutSuccessUrl("/index");
		 
		//http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);

		http.rememberMe();
	}
}
