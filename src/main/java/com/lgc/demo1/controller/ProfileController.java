package com.lgc.demo1.controller;

import com.lgc.demo1.dto.PaginationDTO;
import com.lgc.demo1.model.User;
import com.lgc.demo1.service.NotificationService;
import com.lgc.demo1.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 *个人中心<br>
 *Created by L on  2020/3/8  15:54
 */
@Controller
@RequiredArgsConstructor
public class ProfileController {
    private final QuestionService questionService;
    private final NotificationService notificationService;
    /**
     * 个体提问和最新回复
     *Created by L on  2020/3/8  17:25
     */
    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "action") String action,
                          Model model,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size) {
        //校验用户登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        //个人提问和回复的跳转
        if ("questions".equals(action)) {
            model.addAttribute("sectionName", "我的提问");
            model.addAttribute("section", "questions");
            //个人提问列表
            PaginationDTO paginationDTO = questionService.limitByUserId(user.getId(), page, size);
            model.addAttribute("pagination", paginationDTO);
        } else if ("replies".equals(action)) {
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "最新回复");
            //最新回复通知列表
            PaginationDTO paginationDTO = notificationService.list(user.getId(), page, size);
            model.addAttribute("pagination", paginationDTO);
        }
        return "profile";
    }
}
