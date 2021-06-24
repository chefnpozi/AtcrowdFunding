package com.atguigu.vo.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class UserUpdateVo {

	@ApiModelProperty(value = "访问令牌", required = true)
	private String accessToken = "13778965215";

	@ApiModelProperty(value = "用户昵称")
	private String username = "zhangsan";
	@ApiModelProperty("邮箱")
	private String email = "zhangsan@qq.com";

	@ApiModelProperty("真实姓名")
	private String realname = "张三";
	@ApiModelProperty("身份证号")
	private String cardnum = "2563654354995455";

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
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

}
