package com.lxd.service;

import com.lxd.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Cris on 2020/3/25
 */
public interface TypeService {
    Type saveType(Type type);

    Type getType(Long id);
    Type getTypeByName(String name);

    /*
    * Pageable 是Spring Data库中定义的一个接口，用于构造翻页查询，是所有分页相关信息的一个抽象，
    * 通过该接口，我们可以得到和分页相关所有信息（例如pageNumber、pageSize等），这样，Jpa就能够通过pageable参数来得到一个带分页信息的Sql语句。
    * */
    Page<Type> listType(Pageable pageable);

    List<Type> listTypeTop(Integer size);

    List<Type> listType();

    Type updateType(Long id,Type type);

    void deleteType(Long id);

    //前台
    int  countType();
}
