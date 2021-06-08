<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	table tbody tr:nth-child(odd){background:#F4F4F4;}
	table tbody td:nth-child(even){color:#C00;}
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
			<div class="panel panel-default">
			  <div class="panel-heading">
				<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
			  </div>
			  <div class="panel-body">
			  
			  
<form id="queryForm" class="form-inline" role="form" style="float:left;" action="${PATH}/admin/index" method="post">
  <div class="form-group has-feedback">
    <div class="input-group">
      <div class="input-group-addon">查询条件</div>
      <%-- 把condition传到后台，进行模糊查询，这个条件通过form表单，使用post传给action哪个路径 ,
      condition与request mapping 中参数相同，这样就可以之间传参
      condition 想要回显，很简单，因为是转发，所以condition在请求域中，param获取即可 .
       --%>
      <input class="form-control has-success" name="condition" value="${param.condition}" type="text" placeholder="请输入查询条件">
    </div>
  </div>
  <%-- 给一个点击事件，通过这个id提交这个表单 --%>
  <button type="button" class="btn btn-warning" onclick="$('#queryForm').submit()"><i class="glyphicon glyphicon-search"></i> 查询</button>
</form>


<button id="deleteBatchBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
<button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${PATH}/admin/toAdd'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
<br>
 <hr style="clear:both;">
          <div class="table-responsive">
            <table class="table  table-bordered">
              <thead>
                <tr >
                  <th width="30">#</th>
                  <%-- 先找到这个复选框 --%>
				  <th width="30"><input id="selectAll" type="checkbox"></th>
                  <th>账号</th>
                  <th>名称</th>
                  <th>邮箱地址</th>
                  <th width="100">操作</th>
                </tr>
              </thead>
              <tbody>
                <%-- 先看看model请求域中有没有拿到这个page ${page}--%>
                
              	<%-- ${page} 取出来是pageInfo，调用它里面的list属性，列表中的每个值是 用户--%>
              	<c:forEach items="${page.list}" var="admin" varStatus="status">
              	
	                <tr>
	                <%-- status记录状态，count记录编号--%>
	                  <td>${status.count}</td>
	                  <%-- 带一个属性id，好对当前的checkedBox进行删除 --%>
					  <td><input type="checkbox" adminId="${admin.id}" ></td>
	                  <td>${admin.loginacct}</td>
	                  <td>${admin.username}</td>
	                  <td>${admin.email}</td>
	                  <td>
					      <button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>
					      <%-- ?id=${admin.id} 参数是当前用户的id，好证明去修改哪一个用户 & 用来连接各个传入参数 --%>
					      <button type="button" class="btn btn-primary btn-xs" onclick="window.location.href='${PATH}/admin/toUpdate?pageNum=${page.pageNum}&id=${admin.id}'"><i class=" glyphicon glyphicon-pencil"></i></button>
						  <button type="button" adminId="${admin.id}" class="deleteBtnClass btn btn-danger btn-xs" ><i class=" glyphicon glyphicon-remove"></i></button>
					  </td>
	                </tr>
                
                </c:forEach>
                
                
              </tbody>
			  <tfoot>
			     <tr >
				     <td colspan="6" align="center">
						<ul class="pagination">
								
								<%-- 怎么判断当前是否是第一页，去看PageInfo源码是怎样设计的
										模糊查询要随着分页信息走，把param.condition 作为参数传入
								--%>
								<c:if test="${page.isFirstPage}">
									<li class="disabled"><a href="#">上一页</a></li>
								</c:if>
								
								<c:if test="${!page.isFirstPage}">
									<%-- ?用来传入pageNum这个参数  --%>
									<li><a href="${PATH}/admin/index?condition=${param.condition}&pageNum=${page.pageNum-1}">上一页</a></li>
								</c:if>
								
								<%-- 遍历的是导航页号的数组  items肯定是要遍历一个容器，去pageInfo中找一找合适的容器--%>
								<%-- navigatepageNums装的是5个页码编号 --%>
								<c:forEach items="${page.navigatepageNums}" var="num">
								
									<%-- if判断是否要高亮显示 
									看当前页的页编号是否与容器中遍历的当前编号重合，重合的话要高亮
									--%>
									<c:if test="${page.pageNum == num}">
										<li class="active"><a href="${PATH}/admin/index?condition=${param.condition}&pageNum=${num}">${num}<span class="sr-only">(current)</span></a></li>
									</c:if>
									
									<%-- 当前页的编号与 遍历的num不相等，正常显示 --%>
									<c:if test="${page.pageNum != num}">
										<li><a href="${PATH}/admin/index?condition=${param.condition}&pageNum=${num}">${num}</a></li>
									</c:if>
									
								</c:forEach>
								
								
								<c:if test="${page.isLastPage}">
									<li class="disabled"><a href="#">下一页</a></li>
								</c:if>
								
								<c:if test="${!page.isLastPage}">
									<li><a href="${PATH}/admin/index?condition=${param.condition}&pageNum=${page.pageNum+1}">下一页</a></li>
								</c:if>
						</ul>
					 </td>
				 </tr>

			  </tfoot>
            </table>
          </div>
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
            
            
            // .deleteBtnClass通过找到这个class定位到这个按钮，增加相应的click事件 
            $(".deleteBtnClass").click(function(){
            	
            	// 拿到当前对象的adminId属性
            	var id = $(this).attr("adminId");
            	//询问框
            	layer.confirm('您是否要删除此条信息？', {
            	  btn: ['确认','撤销'] //按钮
            	}, function(index){
            		
            		// js的注释还是// 
            		// 跳转到哪个链接,id原来是循环的，不能那样取，先通过参数传过来，然后字符串拼接
            		window.location.href="${PATH}/admin/doDelete?pageNum=${page.pageNum}&id="+id;
            		
            	  	layer.close(index);
            	}, function(index){
            	  	// 撤销的话什么也不做，只是关闭弹出框。
            		layer.close(index);
            	});
            	
            });
            
            
            
            
            // 由id选择器找到这个复选框，给它一个点击事件
            $("#selectAll").click(function(){
            	// 一点表头的复选框，表体中的复选框要和表头的复选框状态一致
            	// 因为这个页面只有一个表体tbody，可以由元素选择器选出tbody
            	// " input[type="checkbox"]"选出tbody的子元素，其中的类型为checkbox的input，同样是元素选择器
            	// $("tbody input[type="checkbox"]").attr("checked", this.checked);
            	// 选中相应的元素，把checked属性赋值为表头的状态,this表示#selectAll这个对象，及表头
            	// prop可以多刷几遍，解决了attr的bug
            	$("tbody input[type='checkbox']").prop("checked",this.checked);
            	// 单引号和双引号不要同时使用
            });
            
            // 有id选择器选择到deleteBatchBtn这个按钮,给它一个点击事件
            $("#deleteBatchBtn").click(function(){
            	// 准备批量删除checked == true 的表体(tbody)中的用户，到时候还要给一个弹层
            	
            	// : 是用来把元素集中符合要求的元素拿到，这里拿的是已被选中复选框的元素
            	// : js代码还是别老着用空格了，好伤心啊
            	var checkedBoxList = $("tbody input[type='checkbox']:checked");
            	
            	if(checkedBoxList.length==0) {
            		// 集合长度为0，证明没有选中元素，此时应进行弹层提示
            		layer.msg("请先选中用户，再进行删除！");
            		return false;
            	};
            	
            	// 传给后台，进行删除
            	// 我们传一个串 var ids = '1,2,3,4,5';
            	// 然后用,进行分解
            	
            	var ids='';
            	// index 表示数组下标， element表示迭代集合中的每一个元素
            	$.each(checkedBoxList,function(index, element){
            		// 迭代的这个element是DOM对象$(element)转成jQuery对象，取出id属性
            		var curId = $(element).attr("adminId");
            		
            		// 在前面拼这个 , 第一次不拼就完事了
            		if(index!=0) {
            			ids+=',';
            		}
            		ids+=curId;
            		
            	});
            	// 通过 var array = new array();
            	// array.push(adminId); array.join(","); 这样拼串效果一样，比较优雅
            	
            	
            	// 把拼好的字符串传给后台
            	// window.location.href="${PATH}/admin/doDeleteBatch?ids="+ids;
            	
            	layer.confirm('您是否要删除这些用户信息？', {
              	  btn: ['确认','撤销'] //按钮
              	}, function(index){
              		
              		// js的注释还是// 
              		// 跳转到哪个链接,id原来是循环的，不能那样取，先通过参数传过来，然后字符串拼接
              		window.location.href="${PATH}/admin/doDeleteBatch?pageNum=${page.pageNum}&ids="+ids;
              		
              	  	layer.close(index);
              	}, function(index){
              	  	// 撤销的话什么也不做，只是关闭弹出框。
              		layer.close(index);
              	});
            	
            });
            
            
            
            
            
            
            
            
            
        </script>
        <%-- .deleteBtnClass通过找到这个class定位到这个按钮，增加相应的click事件 --%>
  </body>
</html>
    