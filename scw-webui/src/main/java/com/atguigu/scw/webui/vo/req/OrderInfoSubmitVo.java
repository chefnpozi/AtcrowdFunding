package com.atguigu.scw.webui.vo.req;

import java.io.Serializable;

import lombok.Data;

@Data
public class OrderInfoSubmitVo implements Serializable {
	
	private String accessToken;
	private Integer projectid;
	private Integer returnid;
	private Integer rtncount;

	private String address;
	private Byte invoice;
	private String invoictitle;
	private String remark;
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public Integer getProjectid() {
		return projectid;
	}
	public void setProjectid(Integer projectid) {
		this.projectid = projectid;
	}
	public Integer getReturnid() {
		return returnid;
	}
	public void setReturnid(Integer returnid) {
		this.returnid = returnid;
	}
	public Integer getRtncount() {
		return rtncount;
	}
	public void setRtncount(Integer rtncount) {
		this.rtncount = rtncount;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Byte getInvoice() {
		return invoice;
	}
	public void setInvoice(Byte invoice) {
		this.invoice = invoice;
	}
	public String getInvoictitle() {
		return invoictitle;
	}
	public void setInvoictitle(String invoictitle) {
		this.invoictitle = invoictitle;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
