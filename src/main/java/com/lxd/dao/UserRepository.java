package com.lxd.dao;

import com.lxd.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Cris on 2020/3/23
 */
public interface UserRepository extends JpaRepository<User,Long> {     //第二个是主键类型

    User findByUsernameAndPassword(String username,String password);

}
