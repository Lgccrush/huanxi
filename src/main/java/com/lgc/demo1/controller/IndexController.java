package com.lgc.demo1.controller;

import com.lgc.demo1.dto.PaginationDTO;
import com.lgc.demo1.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *主页<br>
 *Created by L on  2020/2/21  8:35
 */
@Controller
@RequiredArgsConstructor
public class IndexController {
    private final QuestionService questionService;

    /**
     *  在主页上分页展示所有提问
     * @param page
     * @param size
     * @param model
     * @return java.lang.String
     */
    @GetMapping("/")//请求地址 使用了thymeleaf 直接路由到index.HTML
    public String index(Model model,//用于页面存值
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "search", required = false) String search) {
        PaginationDTO pagination = questionService.limit(search, page, size);
        model.addAttribute("pagination", pagination);
        model.addAttribute("search", search);
        return "index";//转发到主页
    }
}
