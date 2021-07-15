package com.atguigu.scw.order.service;

import com.atguigu.scw.order.bean.TOrder;
import com.atguigu.scw.order.vo.req.OrderInfoSubmitVo;

public interface TOrderService {

	TOrder saveOrder(OrderInfoSubmitVo orderInfoSubmitVo);

}
