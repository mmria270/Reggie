package org.example.itheima.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;

public interface CategoryService extends IService<org.example.itheima.reggie.entity.Category> {
    public void remove(Long id);
}
