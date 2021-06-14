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
				<h3 class="panel-title"><i class="glyphicon glyphicon-th"></i>权限菜单列表</h3>
			  </div>
			  <div class="panel-body">

 					<!-- 在这个框中生成树 -->
 					<ul id="treeDemo" class="ztree">
 						
 					</ul>
          
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
	        <h4 class="modal-title" id="myModalLabel">添加菜单(✿◡‿◡)</h4>
	      </div>
	      <div class="modal-body">
	      
	        
	        
				  <div class="form-group">
					<label for="name">菜单名称</label>
					<input type="hidden" name="pid">
					<input type="text" class="form-control" id="name" name="name" placeholder="请输入菜单名称">
				  </div>
				  
				  <div class="form-group">
					<label for="url">菜单URL</label>
					<input type="text" class="form-control" id="url" name="url" placeholder="请输入菜单URL">
				  </div>
				  
				  <div class="form-group">
					<label for="icon">菜单图标</label>
					<input type="text" class="form-control" id="icon" name="icon" placeholder="请输入菜单图标">
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
	        <h4 class="modal-title" id="myModalLabel">修改菜单(✿◡‿◡)</h4>
	      </div>
	      <div class="modal-body">
	      
	        
	        
				  <div class="form-group">
					<label for="name">菜单名称</label>
					<input type="hidden" name="id">
					<input type="hidden" name="pid">
					<input type="text" class="form-control" id="name" name="name" placeholder="请输入菜单名称">
				  </div>
				  
				  <div class="form-group">
					<label for="url">菜单URL</label>
					<input type="text" class="form-control" id="url" name="url" placeholder="请输入菜单URL">
				  </div>
				  
				  <div class="form-group">
					<label for="icon">菜单图标</label>
					<input type="text" class="form-control" id="icon" name="icon" placeholder="请输入菜单图标">
				  </div>
				  
			
			
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	        <button id="updateBtn" type="button" class="btn btn-primary">保存 (╯‵□′)╯︵┻━┻</button>
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
            
			 // 调用initTree()初始化树
            	initTree();
            });
            
            
            function initTree(){

                var setting = {
            			data: {
            				simpleData: {
            					enable: true,
            					pIdKey: "pid"
            				}
            			},
            			view: {
            				addDiyDom: function(treeId, treeNode){
            					// 通过节点tid 拼接 _ico 拿到这个 span ，把之前fa样式删掉
            					$("#"+treeNode.tId+"_ico").removeClass();//.addClass();
            					// 找到这个 span，在前面加一个新的bootstrap的span，treeNode.icon是bootstrap的一种样式
            					$("#"+treeNode.tId+"_span").before("<span class='"+treeNode.icon+"'></span>")
            				},
            				
            				addHoverDom: function(treeId, treeNode){  
            					// 遍历了19个节点，每个节点 -> TMenu
    							var aObj = $("#" + treeNode.tId + "_a"); // tId = permissionTree_1, ==> $("#permissionTree_1_a")
    							aObj.attr("href", "javascript:;");
    							if (treeNode.editNameFlag || $("#btnGroup"+treeNode.tId).length>0) return;
    							var s = '<span id="btnGroup'+treeNode.tId+'">';
    							// 加上按钮组
    							if ( treeNode.level == 0 ) { // 根节点
    								// 把treeNode.id传入，方便知道添加在哪一个节点下面
    								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addBtn('+treeNode.id+')" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
    							} else if ( treeNode.level == 1 ) { // 分支节点
    								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  onclick="updateBtn('+treeNode.id+')" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
    								if (treeNode.children.length == 0) {
    									// 没有孩子节点，这个节点才能删除
    									s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="deleteBtn('+treeNode.id+')" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
    								}
    								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addBtn('+treeNode.id+')" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
    							} else if ( treeNode.level == 2 ) { // 叶子节点
    								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"  onclick="updateBtn('+treeNode.id+')" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
    								s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="deleteBtn('+treeNode.id+')" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
    							}
    			
    							s += '</span>';
    							// 先拿到 a 链接，然后把拼好的串放在  a 链接后面
    							aObj.after(s);
    						},
    						
    						removeHoverDom: function(treeId, treeNode){
    							// 鼠标移开，删除按钮组
    							$("#btnGroup"+treeNode.tId).remove();
    						}
            			}
            		};
                
    			// 请求后端，发来数据，以json格式
    			var url = "${PATH}/menu/loadTree";
    			var data = {};	// 拿所有数据
    			
    			$.get(url,data,function(result){
    				// 返回的是简单格式的 json 数据，类型是 List<TMenu>
    				var zNodes = result;

    				// 增加一个父节点
    				zNodes.push({"id":0,"name":"系统菜单","icon":"glyphicon glyphicon-star","target":"_self"});
    				
    				// 转化 target 为自身，这样页面不会跳转到空白页
    				$.each(zNodes, function(index, zNode){
    					zNode.target = "_self";
    				});
    				
        			// 页面加载完成后，执行初始化
            		$.fn.zTree.init($("#treeDemo"), setting, zNodes);
            		
            		// 初始化后在进行展开，给出名字就好，不用使用选择器
    				var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
    				treeObj.expandAll(true);
    			});
                
                
            }
            
            // -------------------增 开始---------------------
            
            function addBtn(id){
            	$('#addModal').modal({
            		show:true,
            		backdrop:'static',
            		keyboard:false
            	});
            	// 通过name 选择到为id的隐藏域，然后把父id赋值给它
            	$('#addModal input[name="pid"]').val(id);
            }
            
            // 提交modal
            $('#saveBtn').click(function(){
            	var pid = $('#addModal input[name="pid"]').val();
            	var name = $('#addModal input[name="name"]').val();
            	var url = $('#addModal input[name="url"]').val();
            	var icon = $('#addModal input[name="icon"]').val();
            	
            	var json = {
            		pid : pid,
            		name : name,
            		url : url,
            		icon : icon
            	}
            	// 将拿到的数据传给后端，存储在数据库
            	$.post("${PATH}/menu/doAdd",json,function(result){
            		// 把数据传给后端，存储成功后，关闭模态框，初始化这棵树
            		if(result=="ok") {
            			layer.msg("保存成功");
            			$('#addModal').modal('hide');
            			// 清空数据
            			$('#addModal input[name="pid"]').val("");
                    	$('#addModal input[name="name"]').val("");
                    	$('#addModal input[name="url"]').val("");
                    	$('#addModal input[name="icon"]').val("");
                    	
                    	// 数据更新了，再初始化一下页面。
                    	initTree();
            		} else {
            			layer.msg("保存失败");
            		}
            	});
            });
            
            // -------------------增 结束---------------------
            
            // -------------------删 开始---------------------
            
            function deleteBtn(id){
            	// 给个询问框
            	layer.confirm("您确定要删除这个菜单吗？o(╥﹏╥)o",{btn:["确定","取消"]},function(index){
            		// 确认要删除
            		$.post("${PATH}/menu/doDelete",{id:id},function(result){
            			if(result=="ok"){
            				// 删除成功,成功后还要刷新一下页面 initTree()
            				layer.msg("删除成功", {time:1000}, function(){
            					initTree();
            				});
            			} else {
            				layer.msg("删除失败");
            			}
            		});
            		layer.close(index);
            	},function(index){
            		layer.close(index);
            	});
            }
            // -------------------删 结束---------------------
            
            // -------------------改 开始---------------------
            
            function updateBtn(id){
            	
            	// 回显模态框中的内容
            	$('#updateModal input[name="id"]').val(id);
            	// 怎样拿到当前菜单的各项属性，回显在模态框中
            	// 向后端发请求，要一次数据算了
            	$.get("${PATH}/menu/getMenuById",{id:id},function(result){
            		// 返回一个菜单的json
            		$('#updateModal input[name="pid"]').val(result.pid);
            		$('#updateModal input[name="name"]').val(result.name);
            		$('#updateModal input[name="url"]').val(result.url);
            		$('#updateModal input[name="icon"]').val(result.icon);
            	});
            	
            	// 弹出模态框
            	$('#updateModal').modal({
            		show:true,
            		keyboard:false,
            		backdrop:'static'
            	});
            	
            	// 进行修改
            	$('#updateBtn').click(function(){
            		// 取出框中的值
            		var id = $('#updateModal input[name="id"]').val();
            		var pid = $('#updateModal input[name="pid"]').val();
            		var name = $('#updateModal input[name="name"]').val();
            		var url = $('#updateModal input[name="url"]').val();
            		var icon = $('#updateModal input[name="icon"]').val();
            		
            		// 整合为json
            		var data = {
            			id : id,
            			pid : pid,
            			name : name,
            			url : url,
            			icon : icon
            		};
            		
            		// 修改
            		$.post("${PATH}/menu/doUpdate",data,function(result){
                		// 返回 ok 表示修改成功
                		if(result=="ok"){
                			layer.msg("修改成功",{time:1000},function(){
                				$('#updateModal').modal('hide');
                				// 清空数据
                    			// $('#updateModal input[name="id"]').val("");
                    			// $('#updateModal input[name="pid"]').val("");
                            	// $('#updateModal input[name="name"]').val("");
                            	// $('#updateModal input[name="url"]').val("");
                            	// $('#updateModal input[name="icon"]').val("");
                				initTree();
                			});
                		} else {
                			layer.msg("修改失败");
                			$('#updateModal').modal('hide');
                		}
                	});
            	});
            	
            }
            // -------------------改 结束---------------------
            	
        </script>
  </body>
</html>
    