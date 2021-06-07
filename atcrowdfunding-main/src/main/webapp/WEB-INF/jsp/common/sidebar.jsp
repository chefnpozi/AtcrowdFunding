<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-sm-3 col-md-2 sidebar">
			<div class="tree">
				<ul style="padding-left:0px;" class="list-group">
				
				
					<c:forEach items="${menuList}" var="parent">
						
						<%-- 看孩子是否为空 ,如果集合为空，咱们直接展示父菜单--%>
						<c:if test="${empty parent.children}">
							<li class="list-group-item tree-closed" >
								<%-- 路径记得加上 ${PATH}/ 跳转页面存在数据库中的url里 parent.name == getName() icon 是图标，bootstrap中的参数，记录在了数据库中 --%>
								<a href="${PATH}/${parent.url}"><i class="${parent.icon}"></i>${parent.name}</a> 
							</li>
						</c:if>
						
						
						<%-- 看孩子是否为空 ,如果集合不为空，迭代父菜单中的子菜单--%>
						<c:if test="${not empty parent.children}">
						
							<li class="list-group-item tree-closed">
								<%-- 调用集合的size（）方法来显示子菜单的数目--%>
								<span><i class="${parent.icon}"></i>${parent.name}<span class="badge" style="float:right">${parent.children.size()}</span></span> 
								
								<ul style="margin-top:10px;display:none;">
									<%-- 开始迭代子菜单--%>
									<c:forEach items="${parent.children}" var="child">
										<li style="height:30px;">
											<%-- 把属性都换成孩子的--%>
											<a href="${PATH}/${child.url}"><i class="${child.icon}"></i>${child.name}</a> 
										</li>
									</c:forEach>
									
								</ul>
								
							</li>
							
						</c:if>
						
						
					</c:forEach>
					
					
				</ul>
			</div>
        </div>