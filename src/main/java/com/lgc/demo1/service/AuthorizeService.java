package com.lgc.demo1.service;

import com.alibaba.fastjson.JSON;
import com.lgc.demo1.dto.AccessTokenDTO;
import com.lgc.demo1.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 *获取授权登录信息<br>
 *Created by L on  2020/2/21  12:30
 */
@Service
public class AuthorizeService {
    /**
     *  获取access_token
     * @param accessTokenDTO
     * @return java.lang.String
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            System.out.println(string);
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
        }
        return null;
    }

    /**
     *  获取授权用户的信息
     * @param accessToken
     * @return com.lgc.demo1.dto.GithubUser
     */
    public GithubUser getGithubUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
        }
        return null;
    }
}
