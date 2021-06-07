<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="/WEB-INF/jsp/error/error.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%--后台使用jsp中的域信息同后端交互 --%>
	<%--/index找的是web.xml中的核心控制器 其中
	<servlet-mapping> <url-pattern>/</url-pattern> 这样路径就对上了
	 --%>
	 
	 <%--
	 	int i = 0;
	 	int y = 3 / i;
	 --%>
	<jsp:forward page="/index"></jsp:forward>
	
	<%-- 
		前台路径：
			浏览器端发起的请求路径
			
			（不以 / 开头）  相对路径：相对的不是当前这个jsp文件
				而是看你是在哪个    请求路径    转过来的，相对的是@RequestMapping("/index")这个请求路径
			
				
			（以 / 开头）  绝对路径：这个很不一样
				表示从服务器的根（ROOT）下进行查找 ：
					D:\Code\Java\crowdfunding\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\ROOT
					${pageContext.request.contextPath} == 
							D:\Code\Java\crowdfunding\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\atcrowdfunding-main
		后台路径：
			服务器端发起的资源请求路径
			<jsp:forward page="/index"></jsp:forward> jsp在服务器端进行解析
			
			绝对路径：
				/index
				那么  / 代表着什么
				代表着  http://localhost:8080/atcrowdfunding-main/ 自己项目的根路径
				为什么由项目的根路径可以直接找到 index.jsp ?
					因为 src/main/webapp 是省略的
					/WEB-INF/jsp 	是我们的前缀
					.jsp 			是我们的后缀
			相对路径:
				相对于当前welcome.jsp的路径
				是与 welcome.jsp 同一级的文件
	 --%>
</body>
</html>
