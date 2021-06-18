package com.atguigu.security.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;


@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration // 声明当前类为一个配置类
@EnableWebSecurity // 声明式配置，启用web安全配置
public class AppWebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	UserDetailsService userDetailsService;//用户详情查询服务组件的接口
	
	@Autowired
	PasswordEncoder passwordEncoder;

	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 默认认证
		// super.configure(auth);
		
		// 实验四：自定义认证用户信息-基于内存的认证方式
		// 账号密码在代码中写死
		
//		auth.inMemoryAuthentication()
//			// RBAC模型，通过角色授予权限，注意角色之间可能存在互斥，包含等关系
//			.withUser("zhangsan").password("123456").roles("学徒","大师")
//			.and()
//			// ACL模型，直接授予权限
//			.withUser("lisi").password("123123").authorities("罗汉拳","武当长拳");
		
		//采用数据库认证方式
		// auth.userDetailsService(userDetailsService);  //默认密码校验，按照明文进行校验。
		// 使用密码编码器，对密码进行 MD5 加密
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
		
		// auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
		
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 取消默认配置
		// super.configure(http);
		
//		// 实验1：授权首页和静态资源
//		http.authorizeRequests()
//			.antMatchers("/layui/**", "/index.jsp").permitAll() // 设置匹配的资源放行
//			.anyRequest().authenticated(); // 剩余的任何资源必须认证
		
		// 实验6：授权首页和静态资源
		http.authorizeRequests()
			// 授权代码是要有顺序的，上来就anyRequest().authenticated(),所有权限都放行了，后面的代码就没有意义了
			.antMatchers("/layui/**", "/index.jsp").permitAll() 
			// .antMatchers("/level1/**").hasRole("学徒")	// 授予不同的角色的访问权限
			// .antMatchers("/level2/**").hasRole("大师")
			// .antMatchers("/level3/**").hasRole("宗师")
			.anyRequest().authenticated(); 
		
		// 实验2：没有权限 就 跳转到自定义的登录页
		// http.formLogin(); // 跳转到默认的登录页,很丑
		http.formLogin().loginPage("/index.jsp")// 没有权限 就 跳转到自定义的登录页
			.usernameParameter("loginacct")		// 登录页面提交表单的属性名(name):用户名
			.passwordParameter("userpswd")		// 登录页面提交表单的属性名(name):用户密码
			.loginProcessingUrl("/index.jsp")	// 提交表单到这个 url ，需要进行指定。默认是login，只是为了和默认的请求表单进行匹配，无所谓，只要这两个匹配就好，只是error前面的路径指定为当前的url
			.defaultSuccessUrl("/main.html");	// 登录成功后到哪个url
		
		// http.logout(); //默认注销请求   请求路径："/logout"
		// logoutUrl("/logout") 和表单中的连接url一致 登出后转回/index.jsp
		http.logout().logoutUrl("/logout").logoutSuccessUrl("/index.jsp");
		
		// http.csrf().disable();		// 禁用CSRF
		
		// 无权访问时，跳转到哪个页面
		http.exceptionHandling().accessDeniedPage("/unauth.html");
		
		// 开启记住我功能
		// http.rememberMe(); // 基于Cookie的方式实现记住我功能
		
		// 基于数据库实现实现记住我功能,即使服务器重启，仍然能够记住我
		JdbcTokenRepositoryImpl ptr = new JdbcTokenRepositoryImpl();
		// 设置一个DAO
		ptr.setDataSource(dataSource);
		http.rememberMe().tokenRepository(ptr);
	}
	
		//MD5+盐+随机数
		//$2a$10$.gt2E3i5WHU6XrDQ/tbJ2uhLtutfCpNMX1I.CH8LSiKjgSc41o1hy
		//$2a$10$6e8Tv0Z/kp.xDMJirgm1jepkllup0z7Fet8XVGMV/ZqXUfTCN2XR.
		//$2a$10$sJrXoUlOp3es.IAFH6YPGuQVwD74iot79T4rc8AhkW7THJFEPr226
		public static void main(String[] args) {
			// 把取到的随机数（密码加密后）中随便一个放入数据库
			BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
			String encode = bcpe.encode("123456");
			System.out.println(encode);
		}
}
