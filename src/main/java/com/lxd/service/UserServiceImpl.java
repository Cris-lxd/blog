package com.lxd.service;

import com.lxd.dao.UserRepository;
import com.lxd.po.User;
import com.lxd.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Cris on 2020/3/23
 */
@Service()
public class UserServiceImpl implements UserService{

    @Autowired   //自动注入
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user=userRepository.findByUsernameAndPassword(username,MD5Utils.code(password));
        return user;
    }
}
