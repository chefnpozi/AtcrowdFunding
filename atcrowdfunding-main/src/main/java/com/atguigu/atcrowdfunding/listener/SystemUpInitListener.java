package com.atguigu.atcrowdfunding.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atguigu.atcrowdfunding.util.Const;

/**
 * 监听application对象的创建和销毁
 * @author DELL
 *
 */
public class SystemUpInitListener implements ServletContextListener{

	
	Logger log = LoggerFactory.getLogger(SystemUpInitListener.class);
	
	// 作用是在一开始初始化一些变量
	// 例如：pageContext.request.contextPath 简写为PATH
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		// 拿到当前的application域，查一下这个函数是干什么的
		ServletContext application = sce.getServletContext();
		// 拿到上下文路径
		String contextPath = application.getContextPath();
		// 记录日志
		log.debug("当前应用的上下文路径：{}", contextPath);
		// 在application域中 对PATH进行赋值
		// 为了代码的鲁棒性，我们定义了一个常量类，方便维护
		application.setAttribute(Const.PATH, contextPath);
//		application.setAttribute("PATH", contextPath);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// 记录日志
		log.debug("当前应用被销毁");
		
	}

}
