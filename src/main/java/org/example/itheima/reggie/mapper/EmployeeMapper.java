package org.example.itheima.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.itheima.reggie.entity.Employee;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
