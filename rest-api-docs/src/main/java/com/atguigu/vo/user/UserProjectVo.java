package com.atguigu.vo.user;

import com.atguigu.enums.OrderStatusEnumes;
import com.atguigu.enums.ProjectStatusEnume;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class UserProjectVo {

	@ApiModelProperty(value = "项目id")
	private Integer id = 1;

	@ApiModelProperty(value = "项目名字")
	private String name = "活性富氢净水直饮机";

	@ApiModelProperty(value = "项目状态  0-草稿  2-审核中 ....")
	private Byte status = ProjectStatusEnume.DRAFT.getCode();

	@ApiModelProperty(value = "订单编号")
	private String orderId = "44546456489132";

	@ApiModelProperty(value = "筹资金额")
	private Long money = 100000L;

	@ApiModelProperty(value = "筹资天数")
	private Integer day = 45;

	@ApiModelProperty(value = "发布日期")
	private String deploydate = "2018-12-27";

	@ApiModelProperty(value = "当前项目已经支持金额")
	private Long supportmoney = 150000L;

	@ApiModelProperty(value = "支持日期")
	private String orderCreateTime = "2019-1-2";

	@ApiModelProperty(value = "我支持的回报id")
	private Integer returnId = 15;

	@ApiModelProperty(value = "我支持的金额")
	private Integer orderMoney = 199;

	@ApiModelProperty(value = "我支持的回报数量")
	private Integer returnNum = 1;

	@ApiModelProperty(value = "交易状态/订单状态")
	private Byte orderStatus = OrderStatusEnumes.PAYED.getCode();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getMoney() {
		return money;
	}

	public void setMoney(Long money) {
		this.money = money;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public String getDeploydate() {
		return deploydate;
	}

	public void setDeploydate(String deploydate) {
		this.deploydate = deploydate;
	}

	public Long getSupportmoney() {
		return supportmoney;
	}

	public void setSupportmoney(Long supportmoney) {
		this.supportmoney = supportmoney;
	}

	public String getOrderCreateTime() {
		return orderCreateTime;
	}

	public void setOrderCreateTime(String orderCreateTime) {
		this.orderCreateTime = orderCreateTime;
	}

	public Integer getReturnId() {
		return returnId;
	}

	public void setReturnId(Integer returnId) {
		this.returnId = returnId;
	}

	public Integer getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Integer orderMoney) {
		this.orderMoney = orderMoney;
	}

	public Integer getReturnNum() {
		return returnNum;
	}

	public void setReturnNum(Integer returnNum) {
		this.returnNum = returnNum;
	}

	public Byte getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Byte orderStatus) {
		this.orderStatus = orderStatus;
	}

}
