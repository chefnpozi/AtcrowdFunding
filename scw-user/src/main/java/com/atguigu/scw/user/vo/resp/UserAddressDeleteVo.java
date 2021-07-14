package com.atguigu.scw.user.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class UserAddressDeleteVo {

	@ApiModelProperty("访问令牌")
	private String accessToken = "DJSJKO4561984DDSDS";

	@ApiModelProperty("地址id")
	private Integer id = 1;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
