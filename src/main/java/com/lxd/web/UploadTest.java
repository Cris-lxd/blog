package com.lxd.web;/*
 *  create by 20224
 *  2020/9/30
 * */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lxd.mapper.FileMapper;
import com.lxd.po.User;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
        for (int i = 0; i < maps.size(); i++) {
            maps.get(i).put("num",i+1);
        }
        model.addAttribute("file",maps);
        return "fileUploadTest";
    }

    @RequestMapping("/fileUpload")
    public String fileUpload(HttpServletRequest request, HttpSession session, @RequestParam("file")MultipartFile file,RedirectAttributes attributes)
    {
        int num=0;
        try {
            String s = "";
            String filename = file.getOriginalFilename();
            if(filename == null || filename.equals("")){
                attributes.addFlashAttribute("message","无效文件");
            }
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
            int size = stream.available();
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

            //处理大小
            String MB = "";
            String KB = "";
            if(size/1048576 >= 1){
                double ceil = Math.ceil(size/1024);
                MB = String.valueOf(ceil) + "mb";
                map.put("size",MB);
            }else{
                double ceil = Math.ceil(size/1024);
                KB = String.valueOf(ceil) + "kb";
                map.put("size",KB);
            }

            num = fileMapper.insertByFileName(map);


        }
        catch (Exception ex){

        }
        Object success = null;
        if(num == 1){
            attributes.addFlashAttribute("message","上传成功，位于服务器 /usr/uploadFile文件夹下");
        //success = JSONObject.toJSON("上传成功，位于服务器 /usr/uploadFile文件夹下");
        }
        return "redirect:/test/tofileUpload";
    }

    @RequestMapping("/download/{id}")
    public String download(HttpServletResponse response, @PathVariable("id") String id,RedirectAttributes attributes){
        try {
            Long lid = Long.valueOf(id);
            String filename = fileMapper.selectById(lid);
            String path = "/usr/uploadFile/" + filename;
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            downloadByInputstream(response,fis,filename);
        }
        catch (FileNotFoundException ex){
            attributes.addFlashAttribute("message","没有该文件，或已被删除");
        }catch (IOException ex){
            attributes.addFlashAttribute("message","下载失败");
        }
        return "redirect:/test/tofileUpload";
    }

    /**
     * 下载方法
     * @param response
     * @param stream
     * @param filename
     * @throws IOException
     */
    public void downloadByInputstream(HttpServletResponse response,FileInputStream stream,String filename) throws IOException {
        InputStream is = new BufferedInputStream(stream);
        byte[] bytes = new byte[is.available()];
        /*is.read(bytes);
        is.close();*/
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("utf-8"),"ISO8859-1"));
        response.setContentType("application/octet-stream");
        OutputStream os = response.getOutputStream();

        int lengthOfRead = 0;
        int lengthOfNum = 0;
        while((lengthOfRead = is.read(bytes))>0){
            lengthOfNum += lengthOfRead;
            os.write(bytes,0,lengthOfRead);
        }
        //os.write(bytes);
        is.close();
        os.flush();
        os.close();
    }

    //删除
    @RequestMapping("/delete/{id}")
    public String delete(HttpServletResponse response, @PathVariable("id") String id, RedirectAttributes attributes){
        Long lid = Long.valueOf(Integer.parseInt(id));
        String filename = fileMapper.selectById(lid);
        File file = new File("/usr/uploadFile/" + filename);
        if(file.exists()){
            if(file.delete()){
                int num =fileMapper.DeleteById(lid);
                if(num>0){
                    attributes.addFlashAttribute("message","删除文件及记录成功");
                }
            }else{
                attributes.addFlashAttribute("message","删除失败");
            }
        }else{
            attributes.addFlashAttribute("message","该文件不存在，无法删除");
        }
        return "redirect:/test/tofileUpload";
    }
}
