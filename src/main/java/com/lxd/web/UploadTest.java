package com.lxd.web;/*
 *  create by 20224
 *  2020/9/30
 * */

import com.alibaba.fastjson.JSONObject;
import com.lxd.mapper.FileMapper;
import com.lxd.po.User;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class UploadTest {
    @Autowired
    private FileMapper fileMapper;

    @RequestMapping("/tofileUpload")
    public String tofileUpload(Model model){

        List<Map> maps = fileMapper.selectAll();
        model.addAttribute("file",maps);
        return "fileUploadTest";
    }

    @RequestMapping("/fileUpload")
    @ResponseBody
    public Object fileUpload(HttpServletRequest request, HttpSession session, @RequestParam("file")MultipartFile file)
    {
        int num=0;
        try {
            String s = "";
            String filename = file.getOriginalFilename();
            String path = "/usr/uploadFile/";
            //String path ="E:/uploadFile/";
            File file1 = new File(path);
            if (!file1.exists()) {
                file1.mkdirs();
            }
            //加入时间字符串防止名字重复
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String date = sdf.format(d);
            String name = date+"_"+filename;
            FileOutputStream fs = new FileOutputStream(path + name);

            InputStream stream = file.getInputStream();
            byte[] b = new byte[stream.available()];
            int bytesum = 0;
            int byteread = 0;
            while((byteread=stream.read(b))!=-1){
                bytesum+=byteread;
                fs.write(b,0,byteread);
                fs.flush();
            };
            //fs.write(b);
            fs.close();
            stream.close();
            Long userId = fileMapper.getUserId("Cris");
            Map map = new HashMap<>();
            map.put("name",name);
            map.put("realName",filename);
            map.put("path",path);
            map.put("user_id",userId);
            num = fileMapper.insertByFileName(map);


        }
        catch (Exception ex){

        }
        Object success = null;
        if(num == 1){
        success = JSONObject.toJSON("上传成功，位于服务器 /usr/uploadFile文件夹下");
        }
        return success;
    }

    @RequestMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response){
        try {
            String path = "E:/yw.png";
            File file = new File(path);
            String filename = file.getName();
            FileInputStream is = new FileInputStream(path);
            downloadByPath(is,response,filename);
        }
        catch (Exception ex){

        }



    }
    public void downloadByPath(InputStream stream,HttpServletResponse response,String filename) throws IOException {
        byte[] bytes = new byte[1024];
        stream.read(bytes);     //将流读取到byte数组中，outputstream用来输出
        stream.close();
        response.reset();
        response.addHeader("Content-Disposition","attachment;filename="+new String(filename.getBytes()));
        response.setContentType("application/octet-stream");
        OutputStream os = response.getOutputStream();
        os.write(bytes);
        os.flush();
        os.close();
    }
}
