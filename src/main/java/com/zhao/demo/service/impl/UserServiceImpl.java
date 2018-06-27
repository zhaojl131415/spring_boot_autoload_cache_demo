package com.zhao.demo.service.impl;

import com.jarvis.cache.annotation.Cache;
import com.jarvis.cache.annotation.CacheDeleteTransactional;
import com.zhao.demo.entity.UserBean;
import com.zhao.demo.mapper.UserMapper;
import com.zhao.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserBean getUserById(Long id) {
        return userMapper.getById(id);
    }

    @Override
    @Cache(expire = 600, key = "'userid-list-' + @@hash(#args[0])")
    public List<UserBean> listByCondition(UserBean user) {
        List<Long> ids = userMapper.listIdsByCondition(user);
        List<UserBean> list = null;
        if (null != ids && ids.size() > 0) {
            list = new ArrayList<>(ids.size());
            UserBean UserBean = null;
            for (Long id : ids) {
                UserBean = userMapper.getUserById(id);
                if (null != UserBean) {
                    list.add(UserBean);
                }
            }
        }
        return list;
    }

    @Override
    @CacheDeleteTransactional
    @Transactional(rollbackFor = Throwable.class)
    public Long register(UserBean user) {
        Long userId = userMapper.getUserIdByName(user.getName());
        if (null != userId) {
            throw new RuntimeException("用户名已被占用！");
        }
        userMapper.addUser(user);
        return user.getId();
    }

    @Override
    public UserBean doLogin(String name, String password) {
        Long userId = userMapper.getUserIdByName(name);
        if (null == userId) {
            throw new RuntimeException("用户不存在！");
        }
        UserBean user = userMapper.getUserById(userId);
        if (null == user) {
            throw new RuntimeException("用户不存在！");
        }
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("密码不正确！");
        }
        return user;
    }

    @Override
    @CacheDeleteTransactional
    @Transactional(rollbackFor = Throwable.class)
    public void updateUser(UserBean user) {
        userMapper.updateUser(user);
    }

    @Override
    @CacheDeleteTransactional
    @Transactional(rollbackFor = Throwable.class)
    public void deleteUserById(Long userId) {
        userMapper.deleteUserById(userId);
    }

}
