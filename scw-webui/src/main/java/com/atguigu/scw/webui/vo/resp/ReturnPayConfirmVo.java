package com.atguigu.scw.webui.vo.resp;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/** 回报支付确认的vo */
@Data
public class ReturnPayConfirmVo implements Serializable{

// 项目信息
private Integer projectId;
private String projectName;
private String projectRemark;

// 发起人信息
private Integer memberId;
private String memberName;

// 回报的信息
private Integer returnId;
private String returnName;
private String returnContent;
private Integer num; // 支持数量，默认数量1，不能大于signalpurchase单笔限购数量
private Integer price;// 支持单价
private Integer freight; //运费
private Integer signalpurchase;// 单笔限购数量
private Integer purchase; //总的限购数量


// 所有的double和float的运算在任何情况下都会导致丢失精度，使用BigDecimal进行计算
private BigDecimal totalPrice;// 总价 totalPrice =price* num+ freight

public Integer getProjectId() {
	return projectId;
}

public Integer getPurchase() {
	return purchase;
}

public void setPurchase(Integer purchase) {
	this.purchase = purchase;
}

public void setProjectId(Integer projectId) {
	this.projectId = projectId;
}

public String getProjectName() {
	return projectName;
}

public void setProjectName(String projectName) {
	this.projectName = projectName;
}

public String getProjectRemark() {
	return projectRemark;
}

public void setProjectRemark(String projectRemark) {
	this.projectRemark = projectRemark;
}

public Integer getMemberId() {
	return memberId;
}

public void setMemberId(Integer memberId) {
	this.memberId = memberId;
}

public String getMemberName() {
	return memberName;
}

public void setMemberName(String memberName) {
	this.memberName = memberName;
}

public Integer getReturnId() {
	return returnId;
}

public void setReturnId(Integer returnId) {
	this.returnId = returnId;
}

public String getReturnName() {
	return returnName;
}

public void setReturnName(String returnName) {
	this.returnName = returnName;
}

public String getReturnContent() {
	return returnContent;
}

public void setReturnContent(String returnContent) {
	this.returnContent = returnContent;
}

public Integer getNum() {
	return num;
}

public void setNum(Integer num) {
	this.num = num;
}

public Integer getPrice() {
	return price;
}

public void setPrice(Integer price) {
	this.price = price;
}

public Integer getFreight() {
	return freight;
}

public void setFreight(Integer freight) {
	this.freight = freight;
}

public Integer getSignalpurchase() {
	return signalpurchase;
}

public void setSignalpurchase(Integer signalpurchase) {
	this.signalpurchase = signalpurchase;
}

public BigDecimal getTotalPrice() {
	return totalPrice;
}

public void setTotalPrice(BigDecimal totalPrice) {
	this.totalPrice = totalPrice;
}

@Override
public String toString() {
	return "ReturnPayConfirmVo [projectId=" + projectId + ", projectName=" + projectName + ", projectRemark="
			+ projectRemark + ", memberId=" + memberId + ", memberName=" + memberName + ", returnId=" + returnId
			+ ", returnName=" + returnName + ", returnContent=" + returnContent + ", num=" + num + ", price=" + price
			+ ", freight=" + freight + ", signalpurchase=" + signalpurchase + ", totalPrice=" + totalPrice + "]";
}



}