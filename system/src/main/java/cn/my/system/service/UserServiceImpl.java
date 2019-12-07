package cn.my.system.service;

import cn.my.system.dao.UserRepository;
import cn.my.system.entity.User;
import cn.my.system.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public User checkUser(String username, String password) {
        User user =userRepository.findByUsernameAndPassword(username, password);
        return user;

    }
}
