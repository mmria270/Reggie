package org.example.itheima.reggie.controller;

import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.itheima.reggie.common.R;
import org.example.itheima.reggie.entity.User;
import org.example.itheima.reggie.service.UserService;
import org.example.itheima.reggie.utils.SMSUtils;
import org.example.itheima.reggie.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //获取手机号
        String phone = user.getPhone();
        if(!StringUtils.isEmpty(phone)){
            //生成随机的4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("生成的验证码：{}",code);
            //调用阿里云提供的短信服务API完成发送短信
//            SMSUtils.sendMessage("瑞吉外卖","SMS_335370878",phone,code);
            //需要将生成的验证码保存到Session
            session.setAttribute(phone,code);
            return R.success(code);
        }
        return R.error("短信发送失败");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        log.info("map:{}",map);

        //获取手机号
        String phone = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();
        //从Session中获取保存的验证码
        Object codeInSession = session.getAttribute(phone);
        //进行验证码的比对（页面提交的验证码和Session中保存的验证码比对）
        if(codeInSession != null && codeInSession.equals(code)){
            //如果能够比对成功，说明登录成功
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);

            User user = userService.getOne(queryWrapper);
            if(user == null){
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);
        }
        return R.error("登录失败");
    }

    @PostMapping("/loginout")
    public R<String> logout(HttpSession  session){
        log.info("用户退出登录");

        // 获取当前登录用户的手机号
        Long userId = (Long) session.getAttribute("user");

        if(userId != null){
            // 根据用户ID查询用户信息，获取手机号
            User user = userService.getById(userId);
            if(user != null && user.getPhone() != null){
                // 清除该手机号的验证码
                session.removeAttribute(user.getPhone());
            }
        }

        // 清除用户登录状态
        session.removeAttribute("user");

        // 可选：使整个Session失效
        // session.invalidate();

        return R.success("退出成功");
    }

}
