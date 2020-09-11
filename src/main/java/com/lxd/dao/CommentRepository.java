package com.lxd.dao;/*
 *  create by 20224
 *  2020/8/21
 * */

import com.lxd.po.Blog;
import com.lxd.po.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long>, JpaSpecificationExecutor<Blog> {

    @Query("select c from t_comment c where c.blogs.id = ?1")
    List<Comment> findCommentByBlogId(Long id);

}
