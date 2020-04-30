package com.lxd.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Cris on 2020/3/23
 */
/*   MD5加密类
*    @param str 要加密的字符串
*    @return  加密后的字符串
* */
public class MD5Utils {
    public static String code(String str){
        try{
            MessageDigest md= MessageDigest.getInstance("MD5");
            md.update(str.getBytes());    // 通过使用 update 方法处理数据,使指定的 byte数组更新摘要
            byte[] byteDiagest=md.digest();    // 获得密文完成哈希计算,产生128 位的长整数
            int i;
            StringBuffer buf=new StringBuffer("");
            for (int offset = 0; offset < byteDiagest.length; offset++) {   // 从第一个字节开始，对每一个字节转换
                i=byteDiagest[offset];
                if(i < 0)
                    i += 256;
                if(i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));     //Integer.toHexString(i)十进制转化为16进制
            }
            //32位加密
            return buf.toString();
            //16位加密
            //return buf.toString().subString(8,24);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }


    }

    public static void main(String[] args) {
        System.out.println(code("111111"));
    }
}
