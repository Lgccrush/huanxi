package com.lgc.demo1.controller;

import com.lgc.demo1.dto.AccessTokenDTO;
import com.lgc.demo1.dto.GithubUser;
import com.lgc.demo1.exception.CustomizeErrorCode;
import com.lgc.demo1.exception.CustomizeException;
import com.lgc.demo1.model.User;
import com.lgc.demo1.service.AuthorizeService;
import com.lgc.demo1.service.NotificationService;
import com.lgc.demo1.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 *授权登录<br>
 *Created by L on  2020/2/21  12:18
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class AuthorizeController {
    private final AuthorizeService authorizeService;//获取GitHub授权所需参数
    private final UserService userService;//处理用户信息存入数据库
    //GitHub授权所需参数反正到配置文件
    @Value("${github.clientId}")
    private String clientId;
    @Value("${github.clientSecret}")
    private String clientSecret;
    @Value("${github.redirectUri}")
    private String redirectUri;

    @GetMapping("/callback")//处理GitHub授权回调地址
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        //获取授权用户的信息
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = authorizeService.getAccessToken(accessTokenDTO);
        GithubUser githubUser = authorizeService.getGithubUser(accessToken);
        //校验获取授权用户的信息
        if (githubUser != null && githubUser.getId() != null) {
            //获取成功设置存入数据库的用户信息
            User user = new User();
            //token用于存入cookie 一边获取用户登录态
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userService.createOrUpdate(user);//保存或更新用户信息
            //设置cookie周期
            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(60 * 60 * 24 * 30 * 6);
            response.addCookie(cookie);
            return "redirect:/";
        } else {
            log.error("callback get github error,{}", githubUser);
            // 登录失败，重新登录
            throw new CustomizeException(CustomizeErrorCode.SYS_ERROR);
        }
    }

    /**
     *  退出登录
     * @param request
     * @param response
     * @return java.lang.String
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        //清除cookie和session
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";

    }
}
