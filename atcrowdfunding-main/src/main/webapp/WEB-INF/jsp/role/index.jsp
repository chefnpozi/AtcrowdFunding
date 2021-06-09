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
      <input class="form-control has-success" type="text" placeholder="请输入查询条件">
    </div>
  </div>
  <button type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
</form>
<button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
<button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='form.html'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
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
            
            
            function initData(pageNum, pageSize) {
            	
            	var json = {
        			// 可以看成传的参数
        			// 参数没传进去，太尴尬了。。
        			pageNum:pageNum,
        			pageSize:2
        		}; // 记得加分号
            	
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
        			td.append('<button type="button" class="btn btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>');
        			td.append('<button type="button" class="btn btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>');
        			td.append('<button type="button" class="btn btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>');
        			
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
        </script>
  </body>
</html>
    