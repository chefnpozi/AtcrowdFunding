package com.atguigu.scw.user.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UserRespVo {

	private String loginacct;

	// 令牌
	@ApiModelProperty("访问令牌，请妥善保管，以后每次请求都要带上")
	private String accessToken;

	private String username;

	private String email;

	// 新注册的账号，默认值为0
	private String authstatus = "0";

	private String usertype;

	private String realname;

	private String cardnum;

	private String accttype;
}
