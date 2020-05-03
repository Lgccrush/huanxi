package com.lgc.demo1.controller;

import com.lgc.demo1.cache.TagCache;
import com.lgc.demo1.model.Question;
import com.lgc.demo1.model.User;
import com.lgc.demo1.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 *问题发布<br>
 *Created by L on  2020/2/24  7:50
 */
@Controller
@RequiredArgsConstructor
public class PublishController {
    private final QuestionService questionService;

    @GetMapping("/publish")//跳转到发布页面
    public String publish(Model model) {
        model.addAttribute("tags", TagCache.get());//缓存在页面的标签
        return "publish";
    }

    /**
     *  发布提问
     * @param title
     * @param description
     * @param tag
     * @param id
     * @param model
     * @param request
     * @return java.lang.String
     */
    @PostMapping("/publish")//接收页面参数,标题,描述,标签,提问ID
    public String doPublish(@RequestParam(value = "title", required = false) String title,
                            @RequestParam(value = "description", required = false) String description,
                            @RequestParam(value = "tag", required = false) String tag,
                            @RequestParam(value = "id", required = false) Long id,
                            Model model, HttpServletRequest request) {
        //设置回显
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        model.addAttribute("tags", TagCache.get());
        //校验标题/补充/标签是否为空,用户是否登录
        if (StringUtils.isBlank(title)) {
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(description)) {
            model.addAttribute("error", "补充不能为空");
            return "publish";
        }
        if (StringUtils.isBlank(tag)) {
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
        String invalid = TagCache.filterInvalid(tag);
        if (StringUtils.isNotBlank(invalid)) {
            model.addAttribute("error", "输入非法标签:" + invalid);
            return "publish";
        }
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }
        Question question = new Question();
        question.setCreator(user.getId());
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setId(id);
        questionService.createOrUpdate(question);

        return "redirect:/";
    }


}
