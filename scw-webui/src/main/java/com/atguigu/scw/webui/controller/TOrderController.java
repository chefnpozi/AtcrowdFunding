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
	public String payOrder(OrderFormInfoSubmitVo vo, HttpSession session) {
		
		// 1.下单
		
		// 把表单提交上来的 OrderFormInfoSubmitVo vo 还有页面 session 域中的信息，封装到 OrderInfoSubmitVo 中
		OrderInfoSubmitVo orderInfoSubmitVo = new OrderInfoSubmitVo();
		
		BeanUtils.copyProperties(vo, orderInfoSubmitVo);
		
		// 从session域中拿出用户信息，进行封装
		UserRespVo userRespVo = (UserRespVo)session.getAttribute("loginMember");
		// 如果session域中没有用户信息，userRespVo == null, 请用户先去登录
		if (userRespVo == null) {
			// 进行重定向，不要转发，有 @ResponseBody 注解
			return "redirect:/login";
		}
		// 现在用户信息拿到了，取用户的 token
		orderInfoSubmitVo.setAccessToken(userRespVo.getAccessToken());
		// 拿到用户的支付确认信息，封装到 orderInfoSubmitVo
		ReturnPayConfirmVo returnPayConfirmVo = (ReturnPayConfirmVo)session.getAttribute("returnPayConfirmVoSession");
		// 将 returnPayConfirmVo 中的信息进行封装 把其中的 项目id，回报id，支持数量，进行封装
		orderInfoSubmitVo.setProjectid(returnPayConfirmVo.getProjectId());
		orderInfoSubmitVo.setReturnid(returnPayConfirmVo.getReturnId());
		orderInfoSubmitVo.setRtncount(returnPayConfirmVo.getNum());
		
		// 将用户要支付的订单信息封装在 orderInfoSubmitVo 这个大 VO 中，然后把这个大VO存储在数据库中
		// 调用Feign接口对order服务进行远程调用，存储这个订单的信息,拿到返回的order对象
		AppResponse<TOrder> resp = orderServiceFeign.saveOrder(orderInfoSubmitVo);
		TOrder order = resp.getData();
		// order 已存入数据库，返回的是存入订单的信息
		
		
		// 2.支付
		String projectName = returnPayConfirmVo.getProjectName();
		
		String result = payOrder(order.getOrdernum(), order.getMoney(), projectName, vo.getRemark());
		
		System.out.println("###########################");
		System.out.println(result);
		System.out.println("###########################");
		
		// result是一个表单，提交此表单，会出来二维码支付页面
		return result;
	}



	private String payOrder(String ordernum, Integer money, String orderName, String remark) {
		
		try {
			//获得初始化的AlipayClient
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
			
			//设置请求参数
			AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
			alipayRequest.setReturnUrl(AlipayConfig.return_url);
			alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
			
			//商户订单号，商户网站订单系统中唯一订单号，必填
			String out_trade_no = ordernum;
			//付款金额，必填
			String total_amount = money.toString();
			//订单名称，必填
			String subject = orderName;
			//商品描述，可空
			String body = remark;
			
			alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
					+ "\"total_amount\":\""+ total_amount +"\"," 
					+ "\"subject\":\""+ subject +"\"," 
					+ "\"body\":\""+ body +"\"," 
					+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
			
			
			
			//请求
			String result = alipayClient.pageExecute(alipayRequest).getBody();
			
			return result;
		} catch (AlipayApiException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	
	@ResponseBody
	@RequestMapping("/order/updateOrderStatus")
	public String updateOrderStatus() {
		
		System.out.println("支付宝支付完成后异步通知结果");
		// 交易完成，返回 "success" 字符串给支付宝服务器，表示交易完成
		return "success";
	}
	
}
