package com.atguigu.scw.project.vo.req;

import java.util.List;

import com.atguigu.scw.vo.req.BaseVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel
public class ProjectBaseInfoVo extends BaseVo{

	@ApiModelProperty("项目之前的临时token")
	private String projectToken;// 项目的临时token
	 
	
	@ApiModelProperty("项目的分类id")
	private List<Integer> typeids; // 项目的分类id
	@ApiModelProperty("项目的标签id")
	private List<Integer> tagids; // 项目的标签id
	 
	
	@ApiModelProperty("项目名称")
	private String name;// 项目名称
	
	@ApiModelProperty("项目简介")
	private String remark;// 项目简介
	 
	@ApiModelProperty("筹资金额")
	private Integer money;// 筹资金额
	 
	@ApiModelProperty("筹资天数")
	private Integer day;// 筹资天数
	 
	
	@ApiModelProperty("项目头部图片")
	private String headerImage;// 项目头部图片
	 
	@ApiModelProperty("项目详情图片")
	private List<String> detailsImage;// 项目详情图片
	
	//发起人信息：自我介绍，详细自我介绍，联系电话，客服电话

}
