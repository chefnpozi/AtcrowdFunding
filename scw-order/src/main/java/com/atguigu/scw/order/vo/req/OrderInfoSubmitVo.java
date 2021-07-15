package com.atguigu.scw.order.vo.req;

import java.io.Serializable;

import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class OrderInfoSubmitVo implements Serializable {

	private String accessToken;
	private Integer projectid;
	private Integer returnid;
	private Integer rtncount;

	private String address;
	private Byte invoice;
	private String invoictitle;
	private String remark;

}
