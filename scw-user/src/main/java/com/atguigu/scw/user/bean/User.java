package com.atguigu.scw.user.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
@Data
@ApiModel
public class User {
	
	@ApiModelProperty(value="用户主键")
	private Integer id;
	
	@ApiModelProperty(value="用户姓名")
	private String name;

	

	
}
