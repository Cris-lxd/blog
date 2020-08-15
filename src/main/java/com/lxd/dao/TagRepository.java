package com.lxd.dao;

import com.lxd.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * Created by Cris on 2020/3/28
 */
public interface TagRepository  extends JpaRepository<Tag,Long> {
    Tag findByName(String name);

    @Query("select t from t_tag t")
    List<Tag> findTop(Pageable pageable);

    @Query("select count(id) from t_tag ")
    int countTag();

}
