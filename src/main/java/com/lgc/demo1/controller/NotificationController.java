package com.lgc.demo1.controller;

import com.lgc.demo1.dto.NotificationDTO;
import com.lgc.demo1.enums.NotificationTypeEnum;
import com.lgc.demo1.model.User;
import com.lgc.demo1.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by codedrinker on 2019/6/14.
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    /**
     *  读取通知的内容
     * @param request
     * @param id
     * @return java.lang.String
     */
    @GetMapping("/notification/{id}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name = "id") Long id) {

        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id, user);

        if (NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()
                || NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterId();
        } else {
            return "redirect:/";
        }
    }
}
