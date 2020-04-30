package com.lxd.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Cris on 2020/3/22
 */
@Entity(name = "t_comment")
public class Comment {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String content;    //留言内容
    private String avatar;    //头像
    @Temporal(TemporalType.TIMESTAMP)   //对应数据库时间的类型
    private Date createTime;

    @ManyToOne
    private Blog blogs;

    /*
    * 内部关联关系
    * */
    @OneToMany(mappedBy = "parentComment")   //一个父类可以有多个回复
    private List<Comment> replyComment=new ArrayList<>();
    @ManyToOne    //一个回复只有一个上层父类
    private Comment parentComment;

    public Comment(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Blog getBlog() {
        return blogs;
    }

    public void setBlog(Blog blog) {
        this.blogs = blog;
    }

    public List<Comment> getReplyComment() {
        return replyComment;
    }

    public void setReplyComment(List<Comment> replyComment) {
        this.replyComment = replyComment;
    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
