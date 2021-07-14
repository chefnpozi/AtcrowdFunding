package com.atguigu.scw.webui.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.atguigu.scw.service.TOrderServiceFeign;
import com.atguigu.scw.vo.resp.AppResponse;
import com.atguigu.scw.webui.config.AlipayConfig;
import com.atguigu.scw.webui.vo.req.OrderFormInfoSubmitVo;
import com.atguigu.scw.webui.vo.req.OrderInfoSubmitVo;
import com.atguigu.scw.webui.vo.resp.ReturnPayConfirmVo;
import com.atguigu.scw.webui.vo.resp.TOrder;
import com.atguigu.scw.webui.vo.resp.UserRespVo;

@Controller
public class TOrderController {
    
	@Autowired
	TOrderServiceFeign orderServiceFeign; 
	
	@ResponseBody
	@RequestMapping("/order/pay")
	public String payOrder(OrderFormInfoSubmitVo vo,HttpSession session) {
		
		//1.下单
		OrderInfoSubmitVo orderInfoSubmitVo=new OrderInfoSubmitVo();
		
		BeanUtils.copyProperties(vo,orderInfoSubmitVo);
		
		UserRespVo  userRespVo =  (UserRespVo)session.getAttribute("loginMember");
		if(userRespVo==null)
		{
			return "redirect:/login";
		}
		
		String accessToke=userRespVo.getAccessToken();
		orderInfoSubmitVo.setAccessToken(accessToke);
		
		ReturnPayConfirmVo returnPayConfirmVo = (ReturnPayConfirmVo)session.getAttribute("returnPayConfirmVoSession");
		if(returnPayConfirmVo==null)
		{
			return "redirect:/login";
		}
		
		orderInfoSubmitVo.setProjectid(returnPayConfirmVo.getProjectId());
		orderInfoSubmitVo.setReturnid(returnPayConfirmVo.getReturnId());
		orderInfoSubmitVo.setRtncount(returnPayConfirmVo.getNum());
		
		AppResponse<TOrder> resp=  orderServiceFeign.saveOrder(orderInfoSubmitVo);
		TOrder order=resp.getData();
		
		
		
		//2.支付 	
		String orderName = returnPayConfirmVo.getProjectName();
				
		String result = payOrder(order.getOrdernum(), order.getMoney(),orderName,vo.getRemark()); 
		System.out.println("***********************");
		System.out.println(result);
		System.out.println("***********************");

		
		//return "redirect:/member/minecrowdfunding";
		return result ; // 这是一个表单，返回给浏览器，并且立即提交表单，出来二维码支付页面
	}
	
	private String payOrder(String ordernum, Integer money, String orderName, String remark) {
		try {
			//获得初始化的AlipayClient 
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
			
			//设置请求参数
			AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
			alipayRequest.setReturnUrl(AlipayConfig.return_url);
			alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

			
			alipayRequest.setBizContent("{\"out_trade_no\":\""+ ordernum +"\"," 
					+ "\"total_amount\":\""+ money +"\"," 
					+ "\"subject\":\""+ orderName +"\"," 
					+ "\"body\":\""+ remark +"\"," 
					+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

			String result = alipayClient.pageExecute(alipayRequest).getBody();
			return result ;
		} catch (AlipayApiException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	
	//临时写在客户端，正常应该写在订单服务系统中。  需要内网穿透。
	@ResponseBody
	@RequestMapping("/order/updateOrderStatus")
	public String updateOrderStatus() {
		
		// request.getParameterMap();
		System.out.println("支付宝支付完成后，异步通知结果....");
		
		return "success";  //业务完成，必须返回"success"字符串给支付宝服务器，表示交易完成。
	}
	
}
