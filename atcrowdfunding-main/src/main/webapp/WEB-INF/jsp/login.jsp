<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
	
	<%-- 抽取公共内容然后导入
		css.jsp
	 --%>
	 <%@ include file="/WEB-INF/jsp/common/css.jsp" %>
	 
	
	<style>

	</style>
  </head>
  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
      </div>
    </nav>

    <div class="container">
		<%-- 下面的js代码一旦submit（）这个表单将会以post的方式 跳转到 doLogin --%>
      <form id="loginForm" class="form-signin" role="form" action="${PATH}/login" method="post">
        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户登录</h2>
          <%-- 使用c标签库进行if判断 
          		从域中取出消息，如果消息不为空，打印消息：用户名为空，密码不对
          --%>
          <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION}">
        	<div class="form-group has-success has-feedback">
		  		${SPRING_SECURITY_LAST_EXCEPTION.message}
		    </div> 
          </c:if>
          
		  <div class="form-group has-success has-feedback">
		  		<%-- 请输入登录账号 在这里读取数据到域中去  value 是为了回显，数据不丢--%>
		  	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			<input type="text" class="form-control" id="loginacct" name="loginacct" value="superadmin" placeholder="请输入登录账号" autofocus>
			<span class="glyphicon glyphicon-user form-control-feedback"></span>
		  </div> 
		  <div class="form-group has-success has-feedback">
		  		<%-- 请输入登录密码 在这里读取数据到域中去 通过name进行查询--%>
			<input type="password" class="form-control" id="userpswd" name="userpswd" value="123456" placeholder="请输入登录密码" style="margin-top:10px;">
			<span class="glyphicon glyphicon-lock form-control-feedback"></span>
		  </div>
		  <div class="form-group has-success has-feedback">
			<select class="form-control" >
                <option value="member">会员</option>
                <option value="user">管理</option>
            </select>
		  </div>
        <div class="checkbox">
          <label>
            <input type="checkbox" name="remember-me"> 记住我
          </label>
          <br>
          <label>
            忘记密码
          </label>
          <label style="float:right">
            <a href="reg.html">我要注册</a>
          </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="dologin()" > 登录</a>
      </form>
    </div>
    
    <%--静态包含js代码 --%>
    <%@ include file="/WEB-INF/jsp/common/js.jsp" %>
    
    <script>
    function dologin() {
    	
    	// 先检验一下,使用id选择器拿到loginacct的值
    	var loginacct = $("#loginacct").val();
    	
    	if($.trim(loginacct)==""){
    		// 时间持续两秒，icon是小图标，编号5是比较伤心的那一个
    		layer.msg("用户名不可以为空", {time:2000, icon:5});
    		
    		// 不执行以下代码
    		return false;
    	}
    	
        $("#loginForm").submit();
    }
    </script>
  </body>
</html>