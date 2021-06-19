package com.atguigu.atcrowdfunding.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

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
		
		http.authorizeRequests().antMatchers("/static/**","/welcome.jsp", "/toLogin").permitAll()
		.anyRequest().authenticated();//剩下都需要认证
		 
		// /login.jsp==POST  用户登陆请求发给Security
		http.formLogin().loginPage("/toLogin")	// 登录失败找这个request mapping
		.usernameParameter("loginacct").passwordParameter("userpswd")
		.loginProcessingUrl("/login")	// /login是spring security 自己的request mapping，执行登录时的校验
		.defaultSuccessUrl("/main")
		.permitAll();
		 
		http.csrf().disable();
		
		// 找我们的映射，登出框架默认是logout
		http.logout().logoutSuccessUrl("/index");
		
		// 访问被拒的话，抛出异常，然后执行这条语句
		http.exceptionHandling().accessDeniedHandler(new AccessDeniedHandler() {

			// servlet的dependency没有导入 会导致找不到HttpServletRequest
			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response,
					AccessDeniedException accessDeniedException) throws IOException, ServletException {
				//   X-Requested-With: XMLHttpRequest
				// 看前端发给我的是同步请求还是异步请求
				String Type = request.getHeader("X-Requested-With");
				System.out.println("================================================");
				System.out.println(Type);
				System.out.println("================================================");
				// 处处是细节 "XMLHttpRequest".equals(Type),因为同步请求getHeader得到的头信息是null
				if ("XMLHttpRequest".equals(Type)) {
					// 证明是异步请求，返回前台一个“403”
					response.getWriter().print("403"); // 403 权限不够，访问被拒绝
				} else {
					// 证明是同步请求，转发到一个错误页面
					request.getRequestDispatcher("/WEB-INF/jsp/error/error403.jsp").forward(request, response);
				}
				
			}
			
		});

		http.rememberMe();
	}
}
