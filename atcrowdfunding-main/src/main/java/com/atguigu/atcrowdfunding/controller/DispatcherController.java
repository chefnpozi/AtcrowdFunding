package com.atguigu.atcrowdfunding.controller;



import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TMenu;
import com.atguigu.atcrowdfunding.service.TAdminService;
import com.atguigu.atcrowdfunding.service.TMenuService;
import com.atguigu.atcrowdfunding.util.Const;

@Controller
public class DispatcherController {
	

	// 测试Git
	
	// spring自动注入相应的实现类
	@Autowired
	TAdminService adminService;
	
	@Autowired
	TMenuService menuService;
	
	Logger log = LoggerFactory.getLogger(DispatcherController.class);
	
	// 相当于在http://localhost:8080/atcrowdfunding-main/ 下生成一个虚拟文件
	@RequestMapping("/index") // http://localhost:8080/atcrowdfunding-main/index
	public String index() {
		log.debug("跳转到系统主页面。。");
		return "index";
	}

	@RequestMapping("/toLogin")
	public String login() {
		log.debug("跳转到登录主页面...");
		return "login";
	}
	
	@RequestMapping("/main")
	public String main(HttpSession session) {
		log.debug("跳转到后台main页面...");
		
		// 进行一些操作，使得菜单可以刷新出来，这里连接数据库的 菜单表
		// 存放父菜单
		// 多次加载页面，只需加载一次菜单树
		if(session == null) {
			// session为null，去登录一下，然后创建session
			return "redirect:/toLogin";
		}
		// 目前session不为空，先去拿数据，在判断数据是否存在
		List<TMenu> menuList = (List<TMenu>)session.getAttribute("menuList");
				
		if(menuList == null) {
			// 拿到数据并存入session
			menuList = menuService.listMenuAll();
			session.setAttribute("menuList", menuList);
		}
		// debug
//		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//		System.out.println(menuList);
//		for (TMenu tMenu : menuList) {
//			System.out.println(tMenu.getName());
//			System.out.println(tMenu.getUrl());
//		}
//		System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		
		// 此时 session 中已有数据，这样就不会频繁的去数据库拿数据了，减轻了数据库的压力
		
		// return 是进行  视图解析
		// 多对 main 进行一次映射的原因是防止多次刷新页面时，多次登录
		return "main";
	}
	

	// 引入spring security 框架，登录收到框架的管控
	
//	@RequestMapping("/doLogin")
//	public String doLogin(String loginacct, String userpswd, HttpSession session, Model model) {
//
//		log.debug("开始登录");
//		
//		log.debug("loginacct = {}", loginacct);
//		log.debug("userpswd = {}", userpswd);
//		
//		// 把要验证的数据进行打包，准备进行验证
//		HashMap<String, Object> map = new HashMap<String, Object>();
//		map.put("loginacct", loginacct);
//		map.put("userpswd", userpswd);
//		
//		// 可能会有异常，如果有 把它抓住
//		try {
//			// 把map中的数据 --> Service --> Dao 到数据库中进行验证 返回登录这个人的信息
//			TAdmin admin = adminService.getTAdminByLogin(map);
//			// 没有异常，把用户信息放在 session域中
//			session.setAttribute(Const.LOGIN_ADMIN, admin);
//			
//			log.debug("登录成功");
//			// return "main"; // 登录成功，到 main界面
//			// session域中的数据是跨页面的。改为转发，避免表单重复提交
//			return "redirect:/main"; // 重定向是到一个映射上。
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//			// 打印日志
//			log.debug("登录失败={}...", e.getMessage());
//			// 上述只是对控制台打印消息，怎么让前端知道？把信息放入model中就好了 ==（放在请求域中 request）
//			model.addAttribute("message", e.getMessage());
//			return "login";	//登录失败，到 login界面,重新登录
//		}
//		
//	}
	
	
//	@RequestMapping("/logout")
//	public String logout(HttpSession session) {
//		log.debug("注销系统，跳转到主页面...");
//		
//		if(session != null) {
//			// 如果session还存在 ，删除数据， 注销session
//			session.removeAttribute(Const.LOGIN_ADMIN);
//			session.invalidate();
//		}
//		// 重定向到@RequestMapping("/index")这个方法，注意这不是在拼串
//		return "redirect:/index";
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
