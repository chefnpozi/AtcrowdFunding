package com.atguigu.scw.webui.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.scw.service.TMemberServiceFeign;
import com.atguigu.scw.service.TProjectServiceFeign;
import com.atguigu.scw.vo.resp.AppResponse;
import com.atguigu.scw.webui.vo.resp.ProjectDetailVo;
import com.atguigu.scw.webui.vo.resp.ReturnPayConfirmVo;
import com.atguigu.scw.webui.vo.resp.UserAddressVo;
import com.atguigu.scw.webui.vo.resp.UserRespVo;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Controller
public class TProjectController {
  
	@Autowired
	TProjectServiceFeign projectServiceFeign;
	
	@Autowired
	TMemberServiceFeign  memberServiceFeign;
	

	
	// 结算
	
	@RequestMapping("/project/confirm/order/{num}")
	public String confirmOrder(@PathVariable("num") Integer num,
			Model model, HttpSession session) {
		
		// 结算需要地址信息，此时需要知道当前的用户信息，从中获得地址
		// 之前登录过，session中应该有用户的信息，如果没有，应让用户先去登录
		UserRespVo userRespVo = (UserRespVo)session.getAttribute("loginMember");
		
		log.debug("用户信息： {}", userRespVo);
		System.out.println("用户信息：");
		System.out.println(userRespVo);
		
		if (userRespVo == null) {
			// 记下当前的url，先去登录，登录完成后再跳转回来
			session.setAttribute("preUrl", "/project/confirm/order/"+num);
			return "redirect:/login";
		}
		
		String accessToken = userRespVo.getAccessToken();
		
		log.debug("用户的accessToken： {}", accessToken);
		System.out.println("用户的accessToken：");
		System.out.println(accessToken);
		
		// 由 accessToken -> member -> addressList
		AppResponse<List<UserAddressVo>> resp = memberServiceFeign.address(accessToken);
		List<UserAddressVo> addressList = resp.getData();
		
		log.debug("用户的地址列表： {}", addressList);
		System.out.println("用户的地址列表：");
		System.out.println(addressList);
		
		// 将 addressList 装入 session 域
		session.setAttribute("memberAddressList", addressList);
		System.out.println("用户的地址是：");
		System.out.println(addressList);
		
		// session域是页面共享的，多次购买会跳转到此页面，取出num来更新总价格
		ReturnPayConfirmVo returnPayConfirmVo = (ReturnPayConfirmVo)session.getAttribute("returnPayConfirmVoSession");
		// 重新计算
		returnPayConfirmVo.setNum(num);
		BigDecimal total = new BigDecimal(returnPayConfirmVo.getPrice() * num + returnPayConfirmVo.getFreight());
		returnPayConfirmVo.setTotalPrice(total);
		
		log.debug("用户的最终支持vo信息： {}", returnPayConfirmVo);
		System.out.println("用户的最终支持vo信息： ");
		System.out.println(returnPayConfirmVo);
		
		// 重新放入session域
		session.setAttribute("returnPayConfirmVoSession", returnPayConfirmVo);
		
		return "project/pay-step-2";
	}
	
	
	
	
	
	
	
	@RequestMapping("/project/support/{projectId}/{returnId}")
	public String support(@PathVariable("projectId") Integer projectId,
			@PathVariable("returnId") Integer returnId,
			Model model, HttpSession session) {
		
		// 拿到这个项目中这个回报的有关信息，项目数据，回报数据，发起人数据
		AppResponse<ReturnPayConfirmVo> returnInfo = projectServiceFeign.returnInfo(projectId, returnId);
		ReturnPayConfirmVo data = returnInfo.getData();
		log.debug("项目中的详细回报信息： {}",data);
		// 装入请求域
		model.addAttribute("returnPayConfirmVo", data);
		// 装入session域
		session.setAttribute("returnPayConfirmVoSession", data);
		
		return "project/pay-step-1";
	}
	
	
	@RequestMapping("/project/projectInfo")
	public String index(@RequestParam("id") Integer id,Model model) {

		System.out.println(id);
		AppResponse<ProjectDetailVo> resp= projectServiceFeign.detailsInfo(id);
		ProjectDetailVo vo=resp.getData();
		model.addAttribute("projectDetailVo",vo);

		return "project/index";
	}
	
	
}
