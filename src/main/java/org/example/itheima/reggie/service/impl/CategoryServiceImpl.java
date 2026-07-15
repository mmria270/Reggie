package org.example.itheima.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.itheima.reggie.common.CustomException;
import org.example.itheima.reggie.entity.Dish;
import org.example.itheima.reggie.entity.Setmeal;
import org.example.itheima.reggie.mapper.CategoryMapper;
import org.example.itheima.reggie.service.CategoryService;
import org.example.itheima.reggie.service.DishService;
import org.example.itheima.reggie.service.SetmealService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, org.example.itheima.reggie.entity.Category> implements CategoryService {
    private final DishService dishService;
    private final SetmealService setmealService;

    public CategoryServiceImpl(DishService dishService, SetmealService setmealService) {
        this.dishService = dishService;
        this.setmealService = setmealService;
    }

    /**
     * 根据id删除分类,删除前需进行关联判断
     * @param ids
     */
    @Override
    public void remove(Long ids) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,ids);

        // 关联菜品
        int count = dishService.count(dishLambdaQueryWrapper);

        if(count>0){
            throw new CustomException("当前分类下关联了菜品,不能删除");
        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,ids);

        // 关联套餐
        int count1 = setmealService.count(setmealLambdaQueryWrapper);
        if(count1>0){
            throw new CustomException("当前分类下关联了套餐,不能删除");
        }

        // 正常删除
        super.removeById(ids);
    }
}
