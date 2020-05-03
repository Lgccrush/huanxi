package com.lgc.demo1.controller;

import com.lgc.demo1.cache.TagCache;
import com.lgc.demo1.dto.CommentDTO;
import com.lgc.demo1.dto.QuestionDTO;
import com.lgc.demo1.enums.CommentTypeEnum;
import com.lgc.demo1.service.CommentService;
import com.lgc.demo1.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 *提问详情<br>
 *Created by L on  2020/3/8  16:34
 */
@Controller
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final CommentService commentService;
    /**
     *  查看指定提问
     * @param id
     * @param model
     * @return java.lang.String
     */
    @GetMapping("/question/{id}")
    public String question(@PathVariable(value = "id") Long id, Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);
        //累加阅读数
        questionService.incView(id);
        model.addAttribute("comments", comments);
        model.addAttribute("question", questionDTO);
        model.addAttribute("relatedQuestions", relatedQuestions);
        return "question";
    }

    /**
     *  编辑提问
     * @param id
     * @param model
     * @return java.lang.String
     */
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id,
                       Model model) {
        QuestionDTO questionDTO = questionService.getById(id);
        model.addAttribute("title", questionDTO.getTitle());
        model.addAttribute("tag", questionDTO.getTag());
        model.addAttribute("description", questionDTO.getDescription());
        model.addAttribute("id", questionDTO.getId());
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }
}
