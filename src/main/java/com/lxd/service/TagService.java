package com.lxd.service;

import com.lxd.po.Tag;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Cris on 2020/3/28
 */
public interface TagService {
    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTag(String ids);

    List<Tag> listTagTop(Integer size);

    Tag updateTag(Long id,Tag tag);

    void deleteTag(Long id);

    //前台
    int  countTag();
}
