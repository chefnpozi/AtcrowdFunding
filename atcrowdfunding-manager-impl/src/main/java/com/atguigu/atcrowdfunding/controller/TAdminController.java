package com.atguigu.atcrowdfunding.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.service.TAdminService;
import com.atguigu.atcrowdfunding.service.TRoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class TAdminController {
	
	@Autowired
	TAdminService adminService;
	
	@Autowired
	TRoleService roleService;

	// 日志
	Logger log = LoggerFactory.getLogger(TAdminController.class);
	
	@RequestMapping("/admin/index")
	public String index(@RequestParam(value="pageNum",required=false,defaultValue="1")Integer pageNum,
						@RequestParam(value="pageSize",required=false,defaultValue="2")Integer pageSize,
						Model model,
						@RequestParam(value="condition",required=false,defaultValue="")String condition) {
		
		log.debug("pageNum={}", pageNum);
		log.debug("pageSize={}", pageSize);
		log.debug("condition={}", condition);
		
		
		// request mapping 应与 数据库中的url相关
		// 不传参数的话有默认值pageNum=1，pageSize=10
		
		PageHelper.startPage(pageNum, pageSize); // 线程绑定
		
		// 创建一个map，用于放置参数
		Map<String, Object> paraMap = new HashMap<String, Object>();
		
		paraMap.put("condition", condition);
		
		// 传回当前的分页,先写函数，在创建函数实体
		// PageInfo 放的是分页的信息，每页中显示的是用户信息的列表，故泛型就是TAdmin
		PageInfo<TAdmin> page = adminService.listAdminPage(paraMap);
		
		// 把page放入请求域中
		model.addAttribute("page", page);
		
		return "admin/index";
	}
	
	 
	@RequestMapping("/admin/toAdd")
	public String toAdd() {
		
		return "admin/add";
	}
	
	// @PreAuthorize("hasRole('PM - 项目经理')")
	@RequestMapping("/admin/doAdd")
	public String doAdd(TAdmin admin) {
		// 实际传的是 账户名 用户名 邮箱 三个信息，但是Spring帮我们封装成一个POJO，方便我们处理
		
		adminService.saveTAdmin(admin);
		// 加入到数据库之后，重定向到index页面
		return "redirect:/admin/index";
		// 可以使用页面合理化技术，当给出的值超过最大页码时，自动跳转到最后一页
		// return "redirect:/admin/index?pageNum="+Integer.MAX_VALUE;
	}
	
	@RequestMapping("/admin/toUpdate")
	public String toUpdate(Integer id, Model model) {
		// 传进来要修改信息的用户id
		// pageNum参数在请求域中，没必要一次性取完，下一个方法再取也无妨
		
		TAdmin admin = adminService.getTAdminById(id);
		// 放入请求域
		model.addAttribute("admin", admin);
		// 转发过去，请求域中带有pageNum这个参数
		return "admin/update";
	}
	
	
	@RequestMapping("/admin/doUpdate")
	public String doUpdate(TAdmin admin, Integer pageNum) {
		// 实际传的是 id 账户名 用户名 邮箱 四个信息，但是Spring帮我们封装成一个POJO，方便我们处理
		// 传过来的参数，要么是POJO的属性，要么被形参接收 比如：pageNum
		
		adminService.updateTAdmin(admin);
		// 改动加入到数据库之后，重定向到index界面的 pageNum页面
		// 重定向是找的另一个 request mapping 不是根据spring前缀后缀字符串拼接
		return "redirect:/admin/index?pageNum="+pageNum;
	}
	
	
	@RequestMapping("/admin/doDelete")
	public String doDelete(Integer id, Integer pageNum) {
		// 接收 id 和 pageNum 两个同名参数
		
		adminService.deleteTAdmin(id);
		// 删除该用户之后，重定向到index界面的 pageNum页面
		// 重定向是找的另一个 request mapping 不是根据spring前缀后缀字符串拼接
		return "redirect:/admin/index?pageNum="+pageNum;
	}
	
	@RequestMapping("/admin/doDeleteBatch")
	public String doDeleteBatch(String ids, Integer pageNum) {
		// 接收  ids 把字符串 按 , 分解后就得到要删除的id
		
		ArrayList<Integer> array = new ArrayList<Integer>();
		
		String[] strs = ids.split(","); // 分解字符串
		for (String str : strs) {
			int id = Integer.parseInt(str);
			array.add(id);	// 拿到id组成的集合
		}
		
		// 批量删除
		adminService.deleteBatchTAdmin(array);
		
		// 删除该用户之后，重定向到index界面的 pageNum页面
		// 重定向是找的另一个 request mapping 不是根据spring前缀后缀字符串拼接
		return "redirect:/admin/index?pageNum="+pageNum;
	}
	
	
	@RequestMapping("/admin/toAssign")
	public String toAssign(Integer id, Model model) {
		// 跳转到该用户的修改权限的页面
		
		// 1.根据用户id查询已经拥有角色的id
		List<Integer> roleIdList = roleService.getRoleIdByAdminId(id);
		
		// 2.查询所有角色
		List<TRole> roleList = roleService.getAllRole();
		
		// 3.将所有角色进行划分
		ArrayList<TRole> assignList = new ArrayList<TRole>();
		ArrayList<TRole> unAssignList = new ArrayList<TRole>();
		
		// 放入请求域，放置的是集合的引用
		model.addAttribute("assignList", assignList);
		model.addAttribute("unAssignList", unAssignList);
		
		for (TRole tRole : roleList) {
			if (roleIdList.contains(tRole.getId())) {
				// 如果该角色属于当前用户的角色集合
				assignList.add(tRole); 		// 4.已分配角色的集合
			} else {
				unAssignList.add(tRole);	// 5.未分配角色的集合
			}
		}
		
		return "admin/assignRole";
	}
	
	@ResponseBody
	@RequestMapping("/admin/doAssign")
	public String doAssign(Integer[] roleId, Integer adminId) {
		// 接收 这个用户的id，和要分配给他的roleId
		System.out.println(adminId);
		roleService.saveRoleAndAdminRelationship(roleId, adminId);
		return "ok";
	}
	
	
	@ResponseBody
	@RequestMapping("/admin/doUnAssign")
	public String doUnAssign(Integer[] roleId, Integer adminId) {
		// 接收 这个用户的id，和要取消分配给他的这些roleId
		// System.out.println(adminId);
		roleService.deleteRoleAndAdminRelationship(roleId, adminId);
		return "ok";
	}
}
