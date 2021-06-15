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
<form class="form-inline" role="form" style="float:left;">
  <div class="form-group has-feedback">
    <div class="input-group">
      <div class="input-group-addon">查询条件</div>
      <input id="condition" class="form-control has-success" type="text" placeholder="请输入查询条件">
    </div>
  </div>
  <button id="queryBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
</form>
<button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
<button id="addBtn" type="button" class="btn btn-primary" style="float:right;"><i class="glyphicon glyphicon-plus"></i> 新增</button>
<br>
 <hr style="clear:both;">
          <div class="table-responsive">
            <table class="table  table-bordered">
              <thead>
                <tr >
                  <th width="30">#</th>
				  <th width="30"><input type="checkbox"></th>
                  <th>名称</th>
                  <th width="100">操作</th>
                </tr>
              </thead>
              <tbody>
                 	数据正在加载
                 	页面加载完之后再去发消息给服务器，进行加载数据
              </tbody>
			  <tfoot>
			     <tr >
				     <td colspan="6" align="center">
						<ul class="pagination">
								
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
  
    
	<!-- 执行添加操作，Modal -->
	<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">添加角色(✿◡‿◡)</h4>
	      </div>
	      <div class="modal-body">
	      
	        
	        
				  <div class="form-group">
					<label for="exampleInputPassword1">角色名称</label>
					<input type="text" class="form-control" id="name" name="name" placeholder="请输入角色名称">
				  </div>
				  
			
			
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button id="saveBtn" type="button" class="btn btn-primary">保存 (╯‵□′)╯︵┻━┻</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- 执行修改操作，Modal -->
	<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">修改角色(*^▽^*)</h4>
	      </div>
	      <div class="modal-body">
	      
	        
	        
				  <div class="form-group">
					<label for="exampleInputPassword1">角色名称</label>
					<input type="text" class="form-control" id="name" name="name" placeholder="请输入角色名称">
					<!-- 隐含域用于存储当前要修改的数据的id -->
					<input type="hidden" name="id" >
				  </div>
				  
			
			
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭o(╥﹏╥)o</button>
	        <button id="updateBtn" type="button" class="btn btn-primary">修改 (╯‵□′)╯︵┻━┻</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	<!-- 执行修改权限操作，Modal -->
	<div class="modal fade" id="assignModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	        <h4 class="modal-title" id="myModalLabel">给角色分配权限(*^▽^*)</h4>
	      </div>
	      <div class="modal-body">
	      
	        
	        
				 	<!-- 在这个框中生成树 -->
 					<ul id="treeDemo" class="ztree">
				  
			
			
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭o(╥﹏╥)o</button>
	        <button id="assignBtn" type="button" class="btn btn-primary">分配 (╯‵□′)╯︵┻━┻</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	
    <%--静态包含js代码 --%>
    <%@ include file="/WEB-INF/jsp/common/js.jsp" %>
    
        <script type="text/javascript">
            $(function () { // 页面加载完成后，进行事件处理
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
            
            	initData(1);
            
            });
            
            var json = {
        			// 可以看成传的参数
        			// 参数没传进去，太尴尬了。。
        			pageNum:1,
        			pageSize:2
        	}; // 记得加分号
            
            function initData(pageNum) {
            	
            	
            	json.pageNum = pageNum;
        		
        		var index = -1;
        		
            	// 1.发起ajax请求，获取分页器
            	// 里面是json对象
            	$.ajax({
            		type:'post',
            		url:'${PATH}/role/loadData',
            		// json定义在外面
            		data:json,
            		beforeSend:function(){
            			// 发送数据之前干什么事
            			index = layer.load(0,{time:10*1000}); // 10s 有点长，给定一个临时变量，加载完数据，直接关掉
            			
            			// 返回true才能继续执行
            			return true;
            		},
            		success:function(result){
            			// 成功返回来结果后，在result中，然后我们做什么
            			layer.close(index); // 加载完数据，直接关掉弹层
            			
            			// 把两个函数拆出来
            			
            			initShow(result);
            			
            			initNavg(result);
            		}
            	});
            	
            	
            }
            
            
         	// 2.展示数据
        	function initShow(result){
         		
        		// 先清空，再添加$.each()添加数据
				$('tbody').empty();
        		
        		// alert("initShow");
        		// 取出该页的role（角色）集合
        		var list = result.list;
        		
        		// var content = '';
        		
        		// 迭代集合中的每一个角色
        		$.each(list, function(index, role){
        			// 拼接字符串,先把dom对象转化为jQuery对象
        			var tr = $('<tr></tr>');
        			
        			tr.append('<td>'+(index+1)+'</td>');
        			tr.append('<td><input type="checkbox"></td>');
        			// role是json对象，直接取出其中的信息即可
        			tr.append('<td>'+role.name+'</td>');
        			
        			var td = $('<td></td>');
        			// 分配和取消权限，使用模态框实现
        			td.append('<button type="button" roleId="'+role.id+'" class="assignPermissionClass btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>');
        			// 加上class="updateClass",可以找到这个按钮，然后自定义属性roleId,取出这个角色的id
        			td.append('<button type="button" roleId="'+role.id+'" class="updateClass btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>');
        			td.append('<button type="button" roleId="'+role.id+'" class="deleteClass btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>');
        			
        			tr.append(td);
        			
        			// 把这个角色对应的tr加入到tbody中
        			$('tbody').append(tr);
        		});
        	}
        	// 3.展示分页条
			function initNavg(result){
				
        		// 先清空，再添加$.each()添加数据
				$('.pagination').empty();
				
        		// alert("initNavg");
				// 同样是拼串，通过 样式类pagination 先找到 ul，再往这里面进行拼串
				// 添加的也是jQuery对象，需要对dom对象进行转化
				
				
				// 判断是不是第一页，是的话这个按钮点不了
				if(result.isFirstPage){
					// href="#" 光跳转没有用，数据没有刷新，应该给一个事件，当点击这个按钮，执行initData()函数
					// 尽量和老师一样，加了disable后就不要加onclick事件了
					$('.pagination').append($('<li class="disabled"><a href="#">上一页</a></li>'));
				}else{
					$('.pagination').append($('<li><a onclick="initData('+(result.pageNum-1)+')">上一页</a></li>'));
				}
				
				
				// 迭代导航栏中的页号,先把导航栏中的页号数组取出,看pageInfo的api
				var navigatepageNums = result.navigatepageNums;
				$.each(navigatepageNums, function(index, num){
					if(num == result.pageNum){
						// result代表当前分页，如果num == 当前分页的页号，应该高亮显示
						$('.pagination').append($('<li class="active"><a href="#">'+num+'<span class="sr-only">(current)</span></a></li>'));
					}else{
						$('.pagination').append($('<li><a onclick="initData('+num+')">'+num+'</a></li>'));
					}
				});
				
				// 判断是不是最后一页，是的话这个按钮点不了
				if(result.isLastPage){
					$('.pagination').append($('<li class="disabled"><a href="#">下一页</a></li>'));
				}else{
					$('.pagination').append($('<li><a onclick="initData('+(result.pageNum+1)+')">下一页</a></li>'));
				}
				
				
        	}
			// =================分页查询结束========================
				
				
        	// ===================模糊查询==========================
        	$('#queryBtn').click(function(){
        		// 拿到了这个输入框中的值
        		var condition = $('#condition').val();
        		
        		// 同样需要调用initData()函数来刷新页面，但是怎么把condition传进去呢？
        		// json原本没有condition这个属性，这样可以给它加上这个属性，并赋值
        		json.condition = condition;
        		
        		// 此时再初始化页面，就会出现带有condition筛选过的数据
        		initData(1);
        	});	
        	// ====================================================
        		
        	// =========利用模态框执行添加操作=====================
        		
        		
        	$('#addBtn').click(function(){
        		$('#addModal').modal({
        			show:true,
        			backdrop:'static',
        			keyboard:false
        		});
        	});	
        		
        		
        	$('#saveBtn').click(function(){
        		
        		// 通过id选择器选到模态框，然后 " " 选到子元素，哪个input，通过name属性选到相应input，取出其中的值
        		var name = $('#addModal input[name="name"]').val();
        		
        		// 通过Ajax把这个值传给后台，保存后刷新页面
        		$.ajax({
        			type:"post",
        			url:"${PATH}/role/doAdd",
        			data:{
        				// 传进去的数据会自动匹配类型，或者封装为一个POJO，对应着其中的属性。
        				name:name
        			},
        			beforeSend:function(result){
        				return true;
        			},
        			success:function(result){
        				// 返回的result是一个字符串，使用 "ok" 表示保存成功
        				if(result=="ok"){
        					// 弹层之后1s触发函数，关闭模态框
        					layer.msg("保存成功",{time:1000},function(){
        						$('#addModal').modal('hide');
        						// 但这只是隐藏模态框，需要再把框中的值清除
        						$('#addModal input[name="name"]').val(""); // 赋值空串即可
        						initData(1); // 刷新到第一页，然后进行倒序排序
        					});
        				}else{
        					layer.msg("保存失败");
        				}
        			}
        		});
        		
        		// 倒序排序，自己做一下，不使用initData，还是在后台倒序查询比较合适
        		
        		
        	});	
        	// ========================================================	
        		
        	// =====================修改===============================
        		// 这个按钮是后来刷新出来的，加不上click()这样的点击事件
        		// $(".updateClass").click(function(){});	
        	
        		// tbody是一开始就有的元素，找到它，通过on函数，对里面updateClass样式类 加 click事件，
        	$('tbody').on('click','.updateClass',function(){
        		// alert("update");
        		// 取出roleId
        		// this是DOM对象，不能取出自定义的属性，先变成jQuery对象，然后取出相应的属性
        		var roleId = $(this).attr("roleId");
        		// alert(roleId);
        		// 通过get请求先查到这个数据,第一个是url，第二个是传给后台的参数，以json的形式，第三个是回调函数，后台的返回值是result
        		$.get("${PATH}/role/getRoleById",{id:roleId},function(result){
        			// 同步开发后台返回数据一般在 域 中，异步开发返回数据一般在回调函数的 result
        			
        			// result是一个TRole对象，展示在模态框中
        			$('#updateModal').modal({
        				show:true,
        				backdrop:'static',
        				keyboard:false
        			});
        			
        			// 把从数据库中 查到的数据 回显
        			$('#updateModal input[name="name"]').val(result.name);
        			
        			// 把该条数据的id写在隐含域中，方便修改后存回数据库
        			$('#updateModal input[name="id"]').val(result.id);
        		});
        	});
        		
        		// 上面只是查出这个数据，现在真的要去修改并存储进数据库中
        	$('#updateBtn').click(function(){
        		// 按照模态框中的文本进行修改
        		
        		// 按照自定义的属性名取出相应的值
        		var name = $('#updateModal input[name="name"]').val();
        		var id = $('#updateModal input[name="id"]').val();
        		
        		// post请求发给后端 1.访问url。2.传的参数 3.回调函数
        		$.post("${PATH}/role/doUpdate",{name:name,id:id},function(result){
        			// 返回的result是一个字符串，若是 "ok" , 代表修改成功
        			if(result == "ok"){
        				layer.msg("修改成功",{time:1000},function(){
        					// 成功便关闭模态框
        					$('#updateModal').modal('hide');
        					// 刷新这一页的数据,之前刷新这一页时，页号便已记录在json的pageNum中
        					initData(json.pageNum);
        				});
        			} else {
        				layer.msg("修改失败");
        			}
        		});
        	});
        	// =====================修改===============================
        		
        		
        	// =====================删除开始===============================	
        		// 同修改
        	$('tbody').on('click','.deleteClass',function(){
        		
        		// 取出id，在数据库删除此条信息，然后刷新页面
        		// 不能使用样式类，因为该页中的每一条数据都是该样式类，这样取的id就永远是第一条数据，永远删第一个
        		var id = $(this).attr("roleId");
        		//询问框
            	layer.confirm('您是否要删除此条信息？', {
            	  btn: ['确认','撤销'] //按钮
            	}, function(index){
            		
            		// 把id传给后台进行删除
            		$.post("${PATH}/role/doDelete",{id:id},function(result){
            			// 返回 “ok” 便是删除成功
            			if(result=="ok") {
            				layer.msg("删除成功",{time:1000},function(){
            					
            					// 刷新这一页的数据,之前刷新这一页时，页号便已记录在json的pageNum中
            					initData(json.pageNum);
            					
            				});
            			} else {
            				layer.msg("删除失败");
            			}
            		});
            		
            	  	layer.close(index);
            	}, function(index){
            	  	// 撤销的话什么也不做，只是关闭弹出框。
            		layer.close(index);
            	});
        	});
        		
        	// =====================删除结束===============================	
        		
        		
        	// =====================分配和取消分配权限===========开始=========
        	
        	 var RId = ''; // 声明一个全局变量
        		// 后来刷新出来的按钮，不能加click事件
        	$("tbody").on("click",".assignPermissionClass",function(){
        		// 弹出模态框
        		$('#assignModal').modal({
        			show:true,
        			keyboard:false,
        			backdrop:'static'
        		});
        		// roleId 挂在了分配按钮上 ,赋给全局变量
        		RId = $(this).attr("roleId");
        		
        		initTree(); // 初始化在模态框中
        	});
        	
        	
        	function initTree(){
        		
        		// 对树的一些设置
        		var setting = {
        				check: {
                			// 让树中的节点前面有了复选框
                			enable:true
                		},
                		data: {
                			simpleData: {
                				enable:true,
                				// 父节点为pid
                				pIdKey:"pid"
                			},
                			key: {
                				url:"xxx",
                				// 显示的字段不是name，而是title
                				name:"title"
                			}
                		},
                		view: {
                			addDiyDom: function(treeId, treeNode){
            					// 通过节点tid 拼接 _ico 拿到这个 span ，把之前fa样式删掉
            					$("#"+treeNode.tId+"_ico").removeClass();//.addClass();
            					// 找到这个 span，在前面加一个新的bootstrap的span，treeNode.icon是bootstrap的一种样式
            					$("#"+treeNode.tId+"_span").before("<span class='"+treeNode.icon+"'></span>")
            				}
                		}
        		};
        		
        		// 加载 树需要的一些数据
        		$.get("${PATH}/permission/getAllPermission",function(result){
        			// result是permission对象集合序列化为json的结果
        			// 生成树,并返回这棵树的对象
        			var treeObj = $.fn.zTree.init($('#treeDemo'), setting, result);
        			// 展开树
        			treeObj.expandAll(true);
        			
        			// 回显此角色所拥有的所有权限、记得写在这个get请求里面，这样才是串行
        			$.get("${PATH}/role/getPermissionIdByRoleId",{roleId:RId},function(permissionIds){
        				// result是permissionId的集合
        				$.each(permissionIds, function(index, pid){
        					
        					var permissionId = pid;
        					// 看API
            				var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            				// 拿到这棵树中 属性id = permissionId的这个节点，第三个参数是指定在那个节点下进行搜索，不指定则进行全局搜索
            				var node = treeObj.getNodeByParam("id", permissionId, null);
            				// 给这个节点打钩，要打钩，不要联动（父子节点），不要回调。
            				treeObj.checkNode(node, true, false , false);
        				});
        			});
        		});
        		
        	}
        	
        	// 点击提交按钮,模态框一直隐藏在幕后，并不是后来刷新出来的，可以调用click函数
        	$('#assignBtn').click(function(){
        		// 目标 ： 给当前角色分配权限，需要知道是哪个角色 -> roleId , 分配那些权限 -> permissions
        		var json = {
        			roleId:RId
        		};
        		// 拿到我们的树对象
        		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        		// 拿到树中选中的节点，这些节点是permission对象集合序列化后的结果
        		var permissions = treeObj.getCheckedNodes(true);
        		
        		// 目前我们不提交空菜单
        		// 把permissions拼进json中，发给后台
        		$.each(permissions, function(index, permission){
        			// 主要拼进去permission的id就好
        			var permissionId = permission.id;
        			// json中的ids不用事先声明，往json中传入参数会直接声明
        			// .ids[] 不好，以多重数组的方式传参
        			json['ids['+index+']'] = permissionId;
        		});
        		// ids数组已传入json
        		// 1.传参时，使用permissionId作为形参，或是Datas，打印数据，看拿到了吗
        		// 2.传的ids是和Datas中的ids对上的
        		$.post("${PATH}/role/doAssignPermissionToRole", json, function(result){
        			// 返回的是 "ok"，表示保存  角色  和  权限  之间的关系成功
        			if(result=="ok"){
        				layer.msg("分配权限成功");
        				
        				$('#assignModal').modal('hide');
        			} else {
        				layer.msg("分配权限失败");
        				
        				$('#assignModal').modal('hide');
        			}
        		});
        	});
        	
        	// =====================分配和取消分配权限===========结束=========
        		
        </script>
  </body>
</html>
    