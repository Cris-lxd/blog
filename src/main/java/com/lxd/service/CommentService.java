package com.lxd.service;/*
 *  create by 20224
 *  2020/8/21
 * */

import com.lxd.po.Comment;

import java.util.List;

public interface CommentService {

    void saveComment(Comment comment);
    List<Comment> findCommentByBlogId(Long id);

}
