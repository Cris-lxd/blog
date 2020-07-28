package com.lxd.service;

import com.lxd.po.User;
import org.springframework.stereotype.Service;

/**
 * Created by Cris on 2020/3/23
 */
public interface UserService {

    User checkUser(String username, String password);
}
