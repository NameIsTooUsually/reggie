package com.chenhao.reggie.web.controller;

import com.chenhao.reggie.utils.FileUtil;
import com.chenhao.reggie.web.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/11 16:58
 */
@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    //注入文件前缀
    @Value("${reggie.path}")
    String basePath;

    @PostMapping("/upload")
    public R upload(MultipartFile file) throws IOException {
        //获取文件名，根据文件名获取后缀
        String originalFilename = file.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
        String suffixName = originalFilename.substring(i);

        //生成文件名
        String nameWithPath = FileUtil.getFileNameWithPath();

        //判断生成的文件带三级文件夹是否存在,如果不存在就创建一个
        FileUtil.makeDirs(nameWithPath, basePath);

        //写入文件
        file.transferTo(new File(basePath + nameWithPath + suffixName));

        //返回文件名
        return R.success("文件上传成功", nameWithPath + suffixName);
    }


    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException {
        //判断文件路径名是否为空
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(new File(basePath,
                    name)));
        } catch (FileNotFoundException e) {
            return;
        }
        //设置响应格式
        response.setContentType("image/jpeg");

        //获取服务器输出流
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
        //开始传输文件

        byte[] bytes = new byte[1024];
        int len = 0;
        while ((len = bis.read(bytes)) > 0) {
            bos.write(bytes, 0, len);
        }
        bos.flush();
        //文件传输完成，关闭资源
        bis.close();
    }

}
