package org.example.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.itheima.reggie.common.BaseContext;
import org.example.itheima.reggie.common.R;
import org.example.itheima.reggie.entity.ShoppingCart;
import org.example.itheima.reggie.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("add")
    public R<ShoppingCart> add(@RequestBody  ShoppingCart shoppingCart){
        log.info("购物车数据：{}",shoppingCart);

        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);

        Long dishId = shoppingCart.getDishId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,currentId);

        if(dishId != null){
            // 添加到购物车的是菜品
            queryWrapper.eq(ShoppingCart::getDishId,dishId);

        }else{
            // 添加到购物车是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }

        // 查询当前菜品或者套餐是否在购物车中
        ShoppingCart cartServiceOne = shoppingCartService.getOne(queryWrapper);

        if(cartServiceOne != null){
            // 如果已经存在，数量加一
            Integer number = cartServiceOne.getNumber();
            cartServiceOne.setNumber(number + 1);
            shoppingCartService.updateById(cartServiceOne);

        }else{
            // 如果不存在，则添加到购物车，数量默认是1
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            cartServiceOne = shoppingCart;
        }
        return R.success(cartServiceOne);
    }

    @GetMapping("list")
    public R<List<ShoppingCart>> list(){
        log.info("查看购物车");

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());
        queryWrapper.orderByAsc(ShoppingCart::getCreateTime);

        List<ShoppingCart> list = shoppingCartService.list(queryWrapper);

        return R.success(list);
    }

    @DeleteMapping("clean")
    public R<String> clean(){
        // 清空购物车
        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,BaseContext.getCurrentId());

        shoppingCartService.remove(queryWrapper);
        return R.success("清空购物车成功");
    }


    @PostMapping("sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart){
        log.info("减少购物车商品数据：{}",shoppingCart);

        Long currentId = BaseContext.getCurrentId();

        LambdaQueryWrapper<ShoppingCart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId, currentId);

        Long dishId = shoppingCart.getDishId();
        if(dishId != null){
            queryWrapper.eq(ShoppingCart::getDishId, dishId);
        }else{
            queryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }

        ShoppingCart cartServiceOne = shoppingCartService.getOne(queryWrapper);

        if(cartServiceOne != null){
            Integer number = cartServiceOne.getNumber();
            if(number > 1){
                cartServiceOne.setNumber(number - 1);
                shoppingCartService.updateById(cartServiceOne);
            }else{
                shoppingCartService.removeById(cartServiceOne.getId());
            }
        }

        return R.success(cartServiceOne);
    }
}
