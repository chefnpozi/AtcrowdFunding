<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh_CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

	<%-- 静态包含 包含css文件 --%>
	<%@ include file="/WEB-INF/jsp/common/css.jsp" %>
	
	<style>
	.tree li {
        list-style-type: none;
		cursor:pointer;
	}
	</style>
  </head>

  <body>

    <%--  顶部 是根据用户动态变化的所以使用动态包含 --%>
    <jsp:include page="/WEB-INF/jsp/common/top.jsp"></jsp:include>

    <div class="container-fluid">
      <div class="row">
      
        <%-- 此处是sidebar 也是使用动态包含--%>
        <jsp:include page="/WEB-INF/jsp/common/sidebar.jsp"></jsp:include>
        
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<ol class="breadcrumb">
				  <li><a href="#">首页</a></li>
				  <li><a href="#">数据列表</a></li>
				  <li class="active">修改</li>
				</ol>
			<div class="panel panel-default">
              <div class="panel-heading">表单数据<div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
			  <div class="panel-body">
				
				<%-- 找到表单，点击找到request mapping /admin/doUpdate --%>
				<form id="updateForm" action="${PATH}/admin/doUpdate" method="post">
				  <div class="form-group">
					<label for="exampleInputPassword1">登陆账号</label>
					
					<%-- 多两个隐藏域，name：属性名 value：属性值 ，把id这个属性传入到POJO中
							用来读取当前页的页码，好定向到当前页,此时的pageNum在param中，有上个页面的请求域转发而来 param：内置对象
					 --%>
					<input type="hidden" name="pageNum" value="${param.pageNum}">
					<input type="hidden" name="id" value="${admin.id}">
					<%-- value="${admin.loginacct}" 从model请求域中找出admin.loginacct进行回显 --%>
					<input type="text" class="form-control" id="loginacct" name="loginacct" value="${admin.loginacct}" placeholder="请输入登陆账号">
				  </div>
				  <div class="form-group">
					<label for="exampleInputPassword1">用户名称</label>
					<input type="text" class="form-control" id="username" name="username" value="${admin.username}" placeholder="请输入用户名称">
				  </div>
				  <div class="form-group">
					<label for="exampleInputEmail1">邮箱地址</label>
					<input type="email" class="form-control" id="email" name="email" value="${admin.email}" placeholder="请输入邮箱地址">
					<p class="help-block label label-warning">请输入合法的邮箱地址, 格式为： xxxx@xxxx.com</p>
				  </div>
				  <button id="updateBtn" type="button" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 修改</button>
				  <button type="button" class="btn btn-danger"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
				</form>
				
			  </div>
			</div>
        </div>
      </div>
    </div>
	
	
    <%--静态包含js代码 --%>
    <%@ include file="/WEB-INF/jsp/common/js.jsp" %>
    
        <script type="text/javascript">
            $(function () {
			    $(".list-group-item").click(function(){
				    if ( $(this).find("ul") ) {
						$(this).toggleClass("tree-closed");
						if ( $(this).hasClass("tree-closed") ) {
							$("ul", this).hide("fast");
						} else {
							$("ul", this).show("fast");
						}
					}
				});
            });
            
            $("#updateBtn").click(function(){
            	$("#updateForm").submit();
            });
            
        </script>
        <%-- 上述js代码用来提交表单 --%>
        
  </body>
</html>
    