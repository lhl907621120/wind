package cn.my.system.service;

import cn.my.system.NoFoundException;
import cn.my.system.dao.UserRepository;
import cn.my.system.entity.User;
import cn.my.system.util.MD5Util;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository)  {
        this.userRepository = userRepository;
    }

    @Override
    public User checkUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);

    }

    @Override
    public User getUser(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public User updateUser(Long id, User user) {
        User one = userRepository.getOne(id);
        if (one == null){
            throw new NoFoundException("不存在该管理员!");
        }
        BeanUtils.copyProperties(user,one);
        return userRepository.save(one);
    }
}
