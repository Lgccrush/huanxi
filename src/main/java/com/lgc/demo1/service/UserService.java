package com.lgc.demo1.service;

import com.lgc.demo1.mapper.UserMapper;
import com.lgc.demo1.model.User;
import com.lgc.demo1.model.UserExample;
import com.lgc.demo1.util.NoteResult;
import com.lgc.demo1.util.NoteUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 *处理用户信息<br>
 *Created by L on  2020/2/22  21:19
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper userMapper;
    /**
     *  新建/更新用户信息
     * @param user
     * @return void
     */
    public void createOrUpdate(User user) {
//        UserExample example = new UserExample();
//        example.createCriteria().andAccountIdEqualTo(user.getAccountId());
//        List<User> users = userMapper.selectByExample(example);
//        if (users.size() == 0) {
//            user.setGmtCreate(System.currentTimeMillis());
//            user.setGmtModified(user.getGmtCreate());
//            userMapper.insert(user);
//        }else {
//            User dbUser = users.get(0);
//            User updateUser = new User();
//            updateUser.setGmtModified(System.currentTimeMillis());
//            updateUser.setAvatarUrl(user.getAvatarUrl());
//            updateUser.setBio(user.getBio());
//            updateUser.setName(user.getName());
//            updateUser.setToken(user.getToken());
//            UserExample userExample = new UserExample();
//            userExample.createCriteria().andIdEqualTo(dbUser.getId());
//            userMapper.updateByExample(updateUser, userExample);
//        }
        //根据accountId查出用户信息
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0) {
            // 没有用户信息 保存用户信息
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        } else {
            //已有用户 更新用户信息
            User dbUser = users.get(0);//已存在数据库中的用户的信息
            User updateUser = new User();//用于更新用户的信息
            updateUser.setGmtModified(System.currentTimeMillis());
            updateUser.setAvatarUrl(user.getAvatarUrl());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());
            UserExample example = new UserExample();
            example.createCriteria()
                    .andIdEqualTo(dbUser.getId());//根据存在数据库中的用户的ID进行更新
            userMapper.updateByExampleSelective(updateUser, example);//update user set dbUser.xxx=updateUser.xxx where
        }
    }
    /**
     *  处理普通用户的登录
     * @param name
     * @param password
     * @return com.lgc.demo3.util.NoteResult<com.lgc.demo3.dto.CnUser>
     */
    public NoteResult<User> checkLogin(String name, String password) {
//		接收结果数据
        NoteResult<User> result = new NoteResult<User>();
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(name);
        List<User> users = userMapper.selectByExample(example);
//		判断是否登录成功
        if (users.size() == 0) {
//			用户名不存在的请况
            result.setType(1);
            result.setMessage("用户名不存在");
            return result;
        }
//		用户名存在再判断密码

        //将用户输入的明文密码加密
        String md5Password = NoteUtil.md5(password);
        if (!users.get(0).getPassword().equals(md5Password)) {
//			密码错误的请况
            result.setType(2);
            result.setMessage("密码错误");
            return result;
        }
//		用户名和密码都正确的请况
        result.setType(0);
        result.setMessage("登录成功");
        result.setData(users.get(0));
        return result;
    }

    /**
     *  注册普通用户的方法
     * @param name
     * @param password
     * @return com.lgc.demo3.util.NoteResult<java.lang.Object>
     */
    public NoteResult<User> add(String name, String password) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(name);
        List<User> users = userMapper.selectByExample(example);
        NoteResult<User> result = new NoteResult<User>();
        if (users.size() != 0) {//检查用户名是否存在
            result.setMessage("用户名已存在");
            result.setType(1);
            return result;
        }
//		添加用户
        User newUser = new User();
        newUser.setAccountId(NoteUtil.createId());
        newUser.setName(name);//设置用户名
        newUser.setPassword(NoteUtil.md5(password));//加密用户密码
        newUser.setGmtCreate(System.currentTimeMillis());
        newUser.setGmtModified(newUser.getGmtCreate());
        String token = String.valueOf(UUID.randomUUID());
        newUser.setToken(token);
        userMapper.insert(newUser);//存入数据库
        result.setMessage("注册成功");
        result.setType(0);
        result.setData(newUser);
        return result;
    }
}
