package com.atguigu.springboot.mapper;



import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.atguigu.springboot.bean.TAdmin;

public interface TAdminMapper {
    

    

    TAdmin selectByPrimaryKey(Integer id);

    
}