package com.lgc.demo1.dto;

import com.lgc.demo1.model.User;
import lombok.Data;

/**
 *关联user和question<br>
 *Created by L on  2020/2/24  8:11
 */
@Data
public class QuestionDTO {
    private Long id;
    private Long creator;
    private String title;
    private String description;
    private String tag;
    private Integer commentCount;
    private Integer likeCount;
    private Integer ViewCount;
    private Long gmtModified;
    private Long gmtCreate;
    private User user;
}
