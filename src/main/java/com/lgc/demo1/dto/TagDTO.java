package com.lgc.demo1.dto;

import lombok.Data;

import java.util.List;

/**
 *标签<br>
 *Created by L on  2020/3/19  22:11
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
