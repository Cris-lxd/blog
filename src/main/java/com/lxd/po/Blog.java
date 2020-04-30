package com.lxd.po;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Cris on 2020/3/22
 */
@Entity(name = "t_blog")       //Java Persistence API(JPA)定义了一种定义，可以将常规的普通Java对象（有时被称作POJO）映射到数据库。
public class Blog {
    @Id
    @GeneratedValue    //表示生成一个唯一标识的主键
    private Long id;
    private String title;
    private String content;
    private String firstPictures;
    private String flag;      //
    private Integer views;     //观看次数
    private boolean appreciation;     //是否赞赏
    private boolean shareStation;    //是否允许分享
    private boolean commentabled;    //是否开启评论
    private boolean published;     //是否发布
    private boolean recommend;     //是否推荐
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)   //对应数据库时间的类型
    private Date updateTime;

    /*
    * 实体间关系建立
    *
    * */
    @ManyToOne   //这是多的一端
    private Type type;
    /*
    * 需要一个空的构造函数来通过持久性框架的反射来创建一个新实例。如果不为类提供任何带有参数的附加构造函数，则不需要提供空构造函数，因为默认情况下可以得到一个构造函数。
    * */
    @ManyToMany(cascade = {CascadeType.PERSIST})   //级联新增
    private List<Tag> tags=new ArrayList<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "blogs")
    private List<Comment> comments=new ArrayList<>();

    @Transient    //不保存到数据库
    private String tagIds;

    private String description;

    public Blog() {
    }
    /*
    setter和geeter方法
    *
    * */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPictures() {
        return firstPictures;
    }

    public void setFirstPictures(String firstPictures) {
        this.firstPictures = firstPictures;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStation() {
        return shareStation;
    }

    public void setShareStation(boolean shareStation) {
        this.shareStation = shareStation;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getTagIds() {
        return tagIds;
    }

    public void setTagIds(String tagIds) {
        this.tagIds = tagIds;
    }

    public void init(){
        this.tagIds=tagsToIds(this.getTags());    //初始化，为了变成相应的格式
    }
    private String tagsToIds(List<Tag> tags){
        if(!tags.isEmpty()){
            StringBuffer ids=new StringBuffer();
            boolean flag=false;
            for (Tag tag :tags){
                if(flag){
                    ids.append(",");
                }else{
                    flag=true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        }else{
            return tagIds;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPictures='" + firstPictures + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStation=" + shareStation +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type=" + type +
                ", tags=" + tags +
                ", user=" + user +
                ", comments=" + comments +
                ", tagIds='" + tagIds + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
