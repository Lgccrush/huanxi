package com.lgc.demo1.controller;


import com.lgc.demo1.model.User;
import com.lgc.demo1.service.UserService;
import com.lgc.demo1.util.NoteResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *处理普通用户登录<br>
 *Created by L on  2020/3/9  12:31
 */
@Controller
@RequiredArgsConstructor
public class CnUserLoginController {
    private final UserService userService;
    @RequestMapping("/login/cnUser")
    @ResponseBody //调用json
    public NoteResult<User> login(String name, String password) {
        /*
         * 调用UserService处理登录
         */
        System.out.println(name + ":" + password);
        NoteResult result = userService.checkLogin(name, password);
        System.out.println(result);
//        User user = (User) result.getData();
//        response.addCookie(new Cookie("token",user.getToken()));
        return result;
    }

    @RequestMapping("/login/register")//匹配地址
    @ResponseBody//调用json响应
    public NoteResult<User> register(String name, String password) {
        NoteResult<User> result = userService.add(name, password);
        System.out.println(name + "" + password );
        return result;
    }
}
