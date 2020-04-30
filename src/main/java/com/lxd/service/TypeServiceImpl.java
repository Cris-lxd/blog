package com.lxd.service;

import com.lxd.NotFoundException;
import com.lxd.dao.TypeRepository;
import com.lxd.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Cris on 2020/3/25
 */
@Service
public class TypeServiceImpl  implements TypeService{

    @Autowired
    private TypeRepository typeRepository;

    @Transactional     //放入事务中
    @Override
    public Type saveType(Type type) {                                                            //保存

        return typeRepository.save(type);
    }

    @Transactional     //放入事务中
    @Override
    public Type getType(Long id) {

        return typeRepository.getOne(id);
    }                                                            //查询


    @Transactional     //放入事务中
    @Override
    public Page<Type> listType(Pageable pageable) {                              //新增
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
//        Sort.by(Sort.Direction.DESC, "blogs.size");
//        Pageable pageable=new PageRequest(0,size);   Springboot  2.0以下写法
        Sort sort = Sort.by(Sort.Order.desc("blogs.size"));
        Pageable pageable =PageRequest.of(0, size, sort);

        return typeRepository.findTop(pageable);
    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();    //返回全部
    }


    @Transactional     //放入事务中
    @Override
    public Type updateType(Long id, Type type) {                                //删除
        Type t=typeRepository.getOne(id);
        if(t==null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type, t);   //type里面的值复制到t对象

        return typeRepository.save(t);     //应为t里面包含id，保存即为对应id的更新
    }


    @Transactional     //放入事务中
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }


    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);     //返回根据名字查询的内容
    }
}
