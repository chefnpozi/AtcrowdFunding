package com.atguigu.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class TMember {
	
	@JsonIgnore
	@ApiModelProperty(hidden=true)
    private Integer id;

	@ApiModelProperty("登陆账号")
    private String loginacct;
	
	@JsonIgnore
	@ApiModelProperty(hidden=true)
    private String userpswd;
	@ApiModelProperty("用户昵称")
    private String username;
	@ApiModelProperty("邮箱")
    private String email;

	@ApiModelProperty("实名认证状态  0-未认证  1-认证中  2-认证成功")
    private Byte authstatus;
	@ApiModelProperty("用户类型")
    private Byte usertype;
	@ApiModelProperty("真实姓名")
    private String realname;
	@ApiModelProperty("身份证号")
    private String cardnum;
	@ApiModelProperty("账号类型")
    private Byte accttype;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginacct() {
        return loginacct;
    }

    public void setLoginacct(String loginacct) {
        this.loginacct = loginacct == null ? null : loginacct.trim();
    }

    public String getUserpswd() {
        return userpswd;
    }

    public void setUserpswd(String userpswd) {
        this.userpswd = userpswd == null ? null : userpswd.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
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
        this.realname = realname == null ? null : realname.trim();
    }

    public String getCardnum() {
        return cardnum;
    }

    public void setCardnum(String cardnum) {
        this.cardnum = cardnum == null ? null : cardnum.trim();
    }

    public Byte getAccttype() {
        return accttype;
    }

    public void setAccttype(Byte accttype) {
        this.accttype = accttype;
    }
}