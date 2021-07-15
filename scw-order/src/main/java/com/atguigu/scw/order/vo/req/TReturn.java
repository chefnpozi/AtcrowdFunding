package com.atguigu.scw.order.vo.req;

import lombok.Data;

@Data
public class TReturn {
    private Integer id;

    private Integer projectid;

    private Byte type;

    private Integer supportmoney;

    private String content;

    private Integer count;

    private Integer signalpurchase;

    private Integer purchase;

    private Integer freight;

    private Byte invoice;

    private Integer rtndate;

}