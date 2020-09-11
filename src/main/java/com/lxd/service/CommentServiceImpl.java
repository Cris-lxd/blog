package com.lxd.service;/*
 *  create by 20224
 *  2020/8/21
 * */

import com.lxd.dao.BlogRepository;
import com.lxd.dao.CommentRepository;
import com.lxd.po.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements  CommentService{
    @Autowired
    private CommentRepository commentRepository;

    @Override
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> findCommentByBlogId(Long id) {
        return commentRepository.findCommentByBlogId(id);
    }
}
