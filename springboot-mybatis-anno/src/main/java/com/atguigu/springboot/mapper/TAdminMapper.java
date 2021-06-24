package com.atguigu.springboot.mapper;



import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.atguigu.springboot.bean.TAdmin;

public interface TAdminMapper {
    

	@Select("select * from t_admin where id = #{id}")
    TAdmin selectByPrimaryKey(Integer id);

    
}