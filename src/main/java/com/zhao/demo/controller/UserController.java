package com.zhao.demo.controller;

import com.zhao.demo.entity.UserBean;
import com.zhao.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    
    @GetMapping()
    public List<UserBean> list() {
        return userService.listByCondition(new UserBean());
    }

    @GetMapping("/{id}")
    public UserBean detail(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/add")
    public UserBean add() {
        UserBean user = new UserBean();
        user.setName("name_" + System.currentTimeMillis());
        user.setPassword("11111");
        userService.register(user);
        return user;
    }

    @GetMapping("/update/{id}")
    public void update(@PathVariable Long id) {
        UserBean user = new UserBean();
        user.setId(id);
        user.setName("name:" + id);
        userService.updateUser(user);
    }

}
