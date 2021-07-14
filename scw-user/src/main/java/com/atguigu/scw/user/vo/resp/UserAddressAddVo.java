package com.atguigu.scw.user.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class UserAddressAddVo {

	@ApiModelProperty("访问令牌")
	private String accessToken = "DJSJKO4561984DDSDS";

	@ApiModelProperty("会员地址")
	private String address = "牙齿大街2号";

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
