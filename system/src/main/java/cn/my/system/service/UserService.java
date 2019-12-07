package cn.my.system.service;

import cn.my.system.entity.User;

public interface UserService {
    User checkUser(String username,String password);
}
