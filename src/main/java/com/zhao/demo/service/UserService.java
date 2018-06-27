package com.zhao.demo.service;

import com.zhao.demo.entity.UserBean;

import java.util.List;

public interface UserService {

    UserBean getUserById(Long userId);

    // @Cache(expire = 600, key = "'userid-list-' + @@hash(#args[0])")
    List<UserBean> listByCondition(UserBean user);

    // @CacheDeleteTransactional
    Long register(UserBean user);

    UserBean doLogin(String name, String password);

    void updateUser(UserBean user);

    void deleteUserById(Long userId);
}
