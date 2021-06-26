package com.atguigu.scw.user.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户注册提交的数据VO
 */
@ApiModel
@Data
public class UserRegistVo {
	
	@ApiModelProperty("手机号")
	private String loginacct;
	
	@ApiModelProperty("密码")
	private String userpswd;
	
	@ApiModelProperty("邮箱")
	private String email;
	
	@ApiModelProperty("验证码")
	private String code;
	
	@ApiModelProperty("用户类型：0 - 个人 ，1 - 企业")
	private String usertype;
}
