package com.lxd.web;/*
 *  create by 20224
 *  2020/9/29
 * */


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
//import org.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

@Controller
@RequestMapping("/md")
public class MdController {

    @RequestMapping("/uploadImg")
    @ResponseBody
    public JSONObject uploadImg(HttpServletRequest request, HttpServletResponse response,
                                @RequestParam(value = "editormd-image-file", required = false) MultipartFile attach)
    {
        JSONObject jsonObject=new JSONObject();
        try {
            request.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type", "text/html");
            String path = request.getSession().getServletContext().getRealPath("/static/img/");
            System.out.println("上传图片到"+path);
            File rootpath = new File(path);
            if(!rootpath.exists()){
                rootpath.mkdirs();
            }
            File realFile = new File(rootpath + File.separator + attach.getOriginalFilename());
            FileUtils.copyInputStreamToFile(attach.getInputStream(),realFile);
            jsonObject.put("success", 1);
            jsonObject.put("message", "上传成功");
            jsonObject.put("url", "/static/img/"+attach.getOriginalFilename());
        }
        catch (Exception e) {
            jsonObject.put("success", 0);
        }
        return jsonObject;
    }

}
