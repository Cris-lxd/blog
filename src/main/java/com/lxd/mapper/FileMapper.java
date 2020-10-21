package com.lxd.mapper;/*
 *  create by 20224
 *  2020/10/9
 * */

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
public interface FileMapper {

    public Long getUserId(String username);

    public int insertByFileName(Map map);

    public List<Map> selectAll();

    public String selectById(Long id);

    public int DeleteById(Long id);
}
