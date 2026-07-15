package org.example.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.itheima.reggie.entity.User;
import org.example.itheima.reggie.mapper.UserMapper;
import org.example.itheima.reggie.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
