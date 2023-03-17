package com.qi.rpc.api.service;

import com.qi.rpc.api.pojo.User;

import java.util.List;

/**
 * @author CreateQi
 * @version 1.0
 * @ClassName UserService
 * @Date 2023/1/8 23:43
 */
public interface UserService {

    User queryUser();

    List<User> getAllUsers();

}
