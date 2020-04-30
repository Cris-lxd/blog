package com.lxd.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cris on 2020/3/22
 */
@Entity(name = "t_tag")
public class Tag {
    @Id
    @GeneratedValue
    private Long id ;

    @NotBlank(message = "不能为空")
    private String name;


    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs=new ArrayList<>();
    public Tag(){}

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

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
