package org.example.itheima.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.example.itheima.reggie.common.BaseContext;
import org.example.itheima.reggie.common.R;
import org.example.itheima.reggie.entity.Orders;
import org.example.itheima.reggie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}",orders);
        orderService.submit(orders);
        return R.success("下单成功");
    }


    @GetMapping("/userPage")
    public R<Page> page(int page,int pageSize){
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Orders::getOrderTime);

        orderService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    @GetMapping("/page")
    public R<Page> page2(int page,int pageSize){
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        LambdaQueryWrapper<Orders> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Orders::getOrderTime);

        orderService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    @PutMapping
    public R<String> updateStatus(@RequestBody Orders orders){
        log.info("修改订单状态，订单ID：{}，新状态：{}", orders.getId(), orders.getStatus());

        if(orders.getId() == null || orders.getStatus() == null){
            return R.error("参数错误");
        }

        Orders order = new Orders();
        order.setId(orders.getId());
        order.setStatus(orders.getStatus());

        orderService.updateById(order);

        return R.success("修改订单状态成功");
    }


    @PostMapping("/again")
    public R<String> again(@RequestBody Orders orders){
        log.info("再来一单，订单ID：{}", orders.getId());

        if(orders.getId() == null){
            return R.error("订单ID不能为空");
        }

        orderService.again(orders);

        return R.success("再来一单成功");
    }
}
