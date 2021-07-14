package com.atguigu.scw.user.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class UserDetailVo {

	@ApiModelProperty("登陆账号")
	private String loginacct = "13778965215";
	@ApiModelProperty("用户昵称")
	private String username = "zhangsan";
	@ApiModelProperty("邮箱")
	private String email = "zhangsan@qq.com";
	@ApiModelProperty("实名认证状态  0-未认证  1-认证中  2-认证成功")
	private Byte authstatus = 1;
	@ApiModelProperty("用户类型")
	private Byte usertype = 1;
	@ApiModelProperty("真实姓名")
	private String realname = "张三";
	@ApiModelProperty("身份证号")
	private String cardnum = "2563654354995455";
	@ApiModelProperty("账号类型")
	private Byte accttype = 1;

	public String getLoginacct() {
		return loginacct;
	}

	public void setLoginacct(String loginacct) {
		this.loginacct = loginacct;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Byte getAuthstatus() {
		return authstatus;
	}

	public void setAuthstatus(Byte authstatus) {
		this.authstatus = authstatus;
	}

	public Byte getUsertype() {
		return usertype;
	}

	public void setUsertype(Byte usertype) {
		this.usertype = usertype;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getCardnum() {
		return cardnum;
	}

	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}

	public Byte getAccttype() {
		return accttype;
	}

	public void setAccttype(Byte accttype) {
		this.accttype = accttype;
	}

}
