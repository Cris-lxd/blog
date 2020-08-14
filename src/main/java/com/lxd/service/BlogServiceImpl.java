package com.lxd.service;

import com.lxd.NotFoundException;
import com.lxd.dao.BlogRepository;
import com.lxd.po.Blog;
import com.lxd.po.Type;
import com.lxd.util.MyBeanUtils;
import com.lxd.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import sun.security.krb5.internal.ccache.Tag;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Cris on 2020/3/28
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogRepository blogRepository;

    /*
    * 查询
    * */
    @Transactional
    @Override
    public Blog getBlog(Long id) {
        return blogRepository.getOne(id);
    }

    /*
     * 分页
     * */
    @Transactional
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {

        return blogRepository.findAll(new Specification<Blog>() {

            /*
            * 自动给的方法，提供动态组合查询
            * */
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
                List<Predicate> predicates=new ArrayList<>();   //动态查询放在list里面
                /*
                * 判断是否输入标题
                * */
                if(!"".equals(blog.getTitle()) && blog.getTitle()!=null){      //判断标题若不为空,前面为内容，后面为占用地址
                    /*
                    * 前面为拿到的对象属性的名字，第二个拿到值
                    * 使用predicates聚合criteriaBuilder创建的条件，再用CriteriaQuery判断使用条件，相当于where。
                    * 例如： Select * From t-user where title like -w%;
                    * */
                    predicates.add(cb.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }

                /*  盘点是否选择对应类型
                *用类型的id来判断用户选的什么类型
                * */
                if(blog.getTypeId() != null){
                    predicates.add(cb.equal(root.<Type>get("type").get("id"), blog.getTypeId()));   //equal相当于sql的“=”
                }
                /*判断是否推荐
                * isRecommend()返回一个布尔类型的值
                * */
                if(blog.isRecommend()){
                    predicates.add(cb.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                cq.where(predicates.toArray(new Predicate[predicates.size()]));    //查询语句   ,转化为数组
                return null;     //root为查询的对象，criteriaquery表示具体两个条件的表达式，相当于where后面的字段
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String querys, Pageable pageable) {
        return blogRepository.findByQuerys(querys, pageable);
    }

    @Override
    public List<Blog> listRecommendBlogTop(Integer size) {
        Sort sort = Sort.by(Sort.Order.desc("updateTime"));
        Pageable pageable = PageRequest.of(0, size, sort);

        return blogRepository.findTop(pageable);
    }

    /*
     * 新增
     * */
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId()==null){
            blog.setCreateTime(new Date());     //初始化
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else{
            blog.setUpdateTime(new Date());
        }

        return blogRepository.save(blog);

    }

    /*
     * 更新
     * */
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog b=blogRepository.getOne(id);             //获取到对应id所有的blog
        if(b == null){                            //若没有则抛出异常
            throw new NotFoundException("该博客不存在");
        }
        /*
        若有，则将新的blog对象包含的内容复制到旧的里面，保存，最后面的为了过滤掉空的数组，只copy有值的属性
        */
        BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogRepository.save(b);
    }

    /*
     * 删除
     * */
    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogRepository.deleteById(id);
    }

    @Override
    public List<Blog> getBlogByType(Long id) {
        return blogRepository.getBlogByType(id);
    }


}
