package com.lgc.demo1.controller;

import com.lgc.demo1.dto.CommentCreateDTO;
import com.lgc.demo1.dto.CommentDTO;
import com.lgc.demo1.dto.ResultDTO;
import com.lgc.demo1.enums.CommentTypeEnum;
import com.lgc.demo1.exception.CustomizeErrorCode;
import com.lgc.demo1.model.Comment;
import com.lgc.demo1.model.User;
import com.lgc.demo1.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 *处理回复<br>
 *Created by L on  2020/3/17  8:28
 */
@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /**
     *  添加回复
     * @param commentCreateDTO
     * @param request
     * @return java.lang.Object
     */
    @ResponseBody
    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request) {
        //校验登录
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        //校验评论内容
        if (commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())) {
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        //设置回复属性
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setContent(commentCreateDTO.getContent());
        comment.setType(commentCreateDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        //保存回复同时添加通知
        commentService.insert(comment, user);
        return ResultDTO.okOf();
    }


    @ResponseBody
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name = "id") Long id) {
        List<CommentDTO> commentDTOS = commentService.listByTargetId(id, CommentTypeEnum.COMMENT);
        return ResultDTO.okOf(commentDTOS);
    }
}
