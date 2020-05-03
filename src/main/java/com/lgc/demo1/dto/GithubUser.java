package com.lgc.demo1.dto;

import lombok.Data;

/**
 *封装授权用户的信息<br>
 *Created by L on  2020/2/21  12:41
 */
@Data
public class GithubUser {
    private String id;
    private String name;
    private String avatarUrl;
    private String bio;

}
