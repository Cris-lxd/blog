package com.lxd.service;

import com.lxd.po.Blog;
import com.lxd.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Cris on 2020/3/28
 */
public interface BlogService {

    Blog getBlog(Long id);    //根据主键查询blog

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);    //blog对象里面的一些属性，封装传递过来

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(String querys,Pageable pageable);

    List<Blog> listRecommendBlogTop(Integer size);    //推荐的博客列表

    Blog saveBlog(Blog blog);   //新增一个blog，传递过来一个blog对象

    Blog updateBlog(Long id,Blog blog);   //根据主键来选择进行哪一个blog的更新

    void deleteBlog(Long id);

    //前台
    /**
     * 根据typeid查询blog列表
     */
    List<Blog> getBlogByType(Long id);


}
