package com.atguigu.scw.webui.vo.resp;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UserRespVo implements Serializable{
@ApiModelProperty("访问令牌，请妥善保管，以后每次请求都要带上")
private String accessToken;//访问令牌
private String loginacct; //存储手机号
private String username;
private String email;
private String authstatus="0";
private String usertype;
private String realname;
private String cardnum;
@Override
public String toString() {
	return "UserRespVo [accessToken=" + accessToken + ", loginacct=" + loginacct + ", username=" + username + ", email="
			+ email + ", authstatus=" + authstatus + ", usertype=" + usertype + ", realname=" + realname + ", cardnum="
			+ cardnum + ", accttype=" + accttype + "]";
}
private String accttype;
public String getAccessToken() {
	return accessToken;
}
public void setAccessToken(String accessToken) {
	this.accessToken = accessToken;
}
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
public String getAuthstatus() {
	return authstatus;
}
public void setAuthstatus(String authstatus) {
	this.authstatus = authstatus;
}
public String getUsertype() {
	return usertype;
}
public void setUsertype(String usertype) {
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
public String getAccttype() {
	return accttype;
}
public void setAccttype(String accttype) {
	this.accttype = accttype;
}

}