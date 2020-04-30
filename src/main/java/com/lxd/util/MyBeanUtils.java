package com.lxd.util;

import org.apache.catalina.LifecycleState;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cris on 2020/4/5
 */
public class MyBeanUtils {

    /*
    *  获取所有的属性值为空的属性的数组
    *  @param source
    *  @return
    * */

    public static String[] getNullPropertyNames(Object source){
        BeanWrapper beanWrapper=new BeanWrapperImpl(source);
        PropertyDescriptor[] pds=beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames=new ArrayList<>();
        for (PropertyDescriptor pd : pds){
            String propertyName = pd.getName();
            if(beanWrapper.getPropertyValue(propertyName)==null){
                nullPropertyNames.add(propertyName);
            }
        }
        return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);
    }
}
