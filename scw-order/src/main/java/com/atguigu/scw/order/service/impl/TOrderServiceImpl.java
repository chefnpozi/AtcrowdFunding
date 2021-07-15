package com.atguigu.scw.order.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.atguigu.scw.enums.OrderStatusEnumes;
import com.atguigu.scw.order.bean.TOrder;
import com.atguigu.scw.order.bean.TReturn;
import com.atguigu.scw.order.mapper.TOrderMapper;
import com.atguigu.scw.order.service.TOrderService;
import com.atguigu.scw.order.service.TProjectServiceFeign;
import com.atguigu.scw.order.vo.req.OrderInfoSubmitVo;
import com.atguigu.scw.utils.AppDateUtils;
import com.atguigu.scw.vo.resp.AppResponse;


@Service
public class TOrderServiceImpl implements TOrderService {

	@Autowired
	TOrderMapper orderMapper;
	
	@Autowired
	StringRedisTemplate stringRedisTemplate;
	
	@Autowired
	TProjectServiceFeign projectServiceFeign;
	

	@Override
	public TOrder saveOrder(OrderInfoSubmitVo orderInfoSubmitVo) {
		// 将 orderInfoSubmitVo 中的信息封装到 order 中，进行返回
		TOrder order = new TOrder();
		
		String accessToken = orderInfoSubmitVo.getAccessToken();
		String memberId = stringRedisTemplate.opsForValue().get(accessToken);
		order.setMemberid(Integer.parseInt(memberId));
		
		order.setProjectid(orderInfoSubmitVo.getProjectid());
		order.setReturnid(orderInfoSubmitVo.getReturnid());
		
		order.setRtncount(orderInfoSubmitVo.getRtncount());
		
		order.setAddress(orderInfoSubmitVo.getAddress());
		order.setInvoice(orderInfoSubmitVo.getInvoice().toString());
		order.setInvoictitle(orderInfoSubmitVo.getInvoictitle());
		order.setRemark(orderInfoSubmitVo.getRemark());
		
		// 生成唯一订单号
		String orderNum = UUID.randomUUID().toString().replaceAll("-", "");
		order.setOrdernum(orderNum);
		// 使用工具类生成订单创建时间
		order.setCreatedate(AppDateUtils.getFormatTime());
		// 通过枚举来设置订单的状态
		order.setStatus(OrderStatusEnumes.UNPAY.getCode()+"");
		
		AppResponse<TReturn> returnInfo = projectServiceFeign.returnInfo(orderInfoSubmitVo.getReturnid());
		TReturn data = returnInfo.getData();
		// 从回报中拿出单件回报的支持单价，以及运费,计算总价格后存入order
		Integer totalPrice = orderInfoSubmitVo.getRtncount() * data.getSupportmoney() + data.getFreight();
		order.setMoney(totalPrice);
		
		orderMapper.insertSelective(order);
		System.out.println("保存订单完成");
		
		return order;
	}
	
}
