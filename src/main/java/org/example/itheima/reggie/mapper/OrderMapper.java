package org.example.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.itheima.reggie.entity.Orders;
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
