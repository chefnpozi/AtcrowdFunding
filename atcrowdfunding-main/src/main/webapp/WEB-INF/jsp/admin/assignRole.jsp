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
				  <li class="active">分配角色</li>
				</ol>
			<div class="panel panel-default">
			  <div class="panel-body">
				<form role="form" class="form-inline">
				  <div class="form-group">
					<label for="exampleInputPassword1">未分配角色列表</label><br>
					<select id="leftRoleList" class="form-control" multiple size="10" style="width:250px;overflow-y:auto;">
					
                        <c:forEach items="${unAssignList}" var="role">
                        	<option value="${role.id}">${role.name}</option>
                        </c:forEach>
                        
                    </select>
				  </div>
				  <div class="form-group">
                        <ul>
                            <li id="leftToRightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                            <br>
                            <li id="rightToLeftBtn" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                        </ul>
				  </div>
				  <div class="form-group" style="margin-left:40px;">
					<label for="exampleInputPassword1">已分配角色列表</label><br>
					<select id="rightRoleList" class="form-control" multiple size="10" style="width:250px;overflow-y:auto;">
					
                        <c:forEach items="${assignList}" var="role">
                        	<option value="${role.id}">${role.name}</option>
                        </c:forEach>
                        
                    </select>
				  </div>
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
            
            
            // 分配角色
            $('#leftToRightBtn').click(function(){
            	// 要把左边的选中元素转移到右边
            	// 把 选中 的元素打包成一个jQuery对象集合
            	var leftSelectedRoleList = $('#leftRoleList option:selected');
          	
            	// 
            	if(leftSelectedRoleList.length == 0) {
            		layer.msg("请选择角色再进行分配！",{icon:5,time:2000});
            		return false;
            	}
            	
            	// 长度不为0，向后端提交请求
            	var url = "${PATH}/admin/doAssign";
            	
            	var data = '';
            	// 循环拼接角色id,遍历左边选择的角色,列表中是选中的option元素，我们之前把 role.id 放进了这个元素的value域中，直接取出即可。
            	$.each(leftSelectedRoleList, function(index, option){
            		var roleId = option.value;
            		data+='roleId='+roleId+'&';
            	});
            	// 由上一页转来的时候，参数域中有一个adminId
            	data+='adminId=${param.id}'; 	// roleId=1&roleId=2&roleId=3&adminId=1
            	
            	$.post(url, data, function(result){
            		if(result=="ok"){
            			layer.msg("分配成功",{icon:6,time:2000},function(){
            				// 因为我们要移除左边选择的元素，所以加的是副本，而不能是引用
                        	$('#rightRoleList').append(leftSelectedRoleList.clone());
                        	// 移除左边选择好的元素
                        	leftSelectedRoleList.remove();
            			});
            		} else {
            			layer.msg("分配失败",{icon:5,time:2000});
            		}
            	});
            	
            });
            
            // 取消分配角色
            $('#rightToLeftBtn').click(function(){
            	
            	// 要把右边的选中元素转移到左边
            	
            	// 把 选中 的元素打包成一个jQuery对象集合
            	var rightSelectedRoleList = $('#rightRoleList option:selected');
            	
            	
            	if(rightSelectedRoleList.length == 0) {
            		layer.msg("请选择角色再进行取消分配！",{icon:5,time:2000});
            		return false;
            	}
            	
            	// 长度不为0，向后端提交请求
            	var url = "${PATH}/admin/doUnAssign";
            	
            	var data = '';
            	
            	$.each(rightSelectedRoleList, function(index, option){
            		var roleId = option.value;
            		data+='roleId='+roleId+'&';
            	});
            	// 由上一页转来的时候，参数域中有一个adminId
            	data+='adminId=${param.id}'; 	// roleId=1&roleId=2&roleId=3&adminId=1
            	
            	$.post(url, data, function(result){
            		if(result=="ok"){
            			layer.msg("取消分配成功",{icon:6,time:2000},function(){
            				// 加的是副本，而不能是引用
                        	$('#leftRoleList').append(rightSelectedRoleList.clone());
                        	rightSelectedRoleList.remove();
            			});
            		} else {
            			layer.msg("取消分配失败",{icon:5,time:2000});
            		}
            	});
            	
            });
            
            
            
            
            
            
            
            
            
        </script>
  </body>
</html>
    