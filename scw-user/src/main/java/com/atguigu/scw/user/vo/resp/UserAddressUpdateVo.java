package com.atguigu.scw.user.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class UserAddressUpdateVo {

	@ApiModelProperty("地址id")
	private Integer id = 1;

	@ApiModelProperty("会员地址")
	private String address = "牙齿大街1号";

	@ApiModelProperty("访问令牌")
	private String accessToken = "DAKJLJSDAL45658446";

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
