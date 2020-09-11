package com.lxd.mapper;/*
 *  create by 20224
 *  2020/9/8
 * */

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface UserMapper {
    public List<Map<String,String>> findA();
}
