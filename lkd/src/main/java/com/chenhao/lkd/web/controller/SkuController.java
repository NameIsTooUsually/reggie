package com.chenhao.lkd.web.controller;

import com.alibaba.druid.sql.visitor.functions.If;
import com.chenhao.lkd.pojo.Sku;
import com.chenhao.lkd.pojo.vo.PageVo;
import com.chenhao.lkd.service.SkuService;
import com.chenhao.lkd.utils.FileUtil;
import jdk.nashorn.internal.objects.annotations.Where;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

/**
 * @author ChenHao
 * @version 1.0
 * @description:
 * @date 2022/6/10 15:19
 */
@Slf4j
@RestController
@RequestMapping("/api/vm-service/sku")
public class SkuController {

    @Autowired
    SkuService skuService;
    @Autowired
    HttpServletRequest request;

    @Value("${lkd.path}")
    String basePath;

    @GetMapping("/search")
    public PageVo searchByPage(@RequestParam Integer pageIndex, Integer pageSize) {
        //查询页面
        PageVo pageVo = skuService.searchByPage(pageIndex, pageSize);

        return pageVo;

    }

    //上传照片
    @PostMapping("/fileUpload")
    public String fileUpload(MultipartFile fileName) throws IOException {
        //1.访问前缀
        String preFile = "http://localhost:9999/api/vm-service/sku/download?name=";
        //2.获取文件的名称
        String imagName = fileName.getOriginalFilename();
        //3.1获取文件的后缀名
        String extName = imagName.substring(imagName.lastIndexOf("."));
        //3.2使用工具类生成带三层目录文件名
        String fileNameWithPath = FileUtil.getFileNameWithPath();
        //判断该文件路径是否存在
        FileUtil.makeDirs(fileNameWithPath,basePath);
        //4.将文件进行拷贝
        fileName.transferTo(new File(basePath+fileNameWithPath+extName));
        //5.返回文件路径
        return preFile+basePath+fileNameWithPath+extName;
    }

    //下载图片
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws IOException {
        System.out.println(name);
        //设置响应数据格式
        response.setContentType("image/jpeg");

        //判断该文件位置文件是否存在
        //判断文件路径名是否为空
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(new File(name)));
        } catch (FileNotFoundException e) {
            return;
        }
        //获取服务器输出流
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());

        //响应数据
        byte[] bytes = new byte[1024];
        int len = 0;

        while ((len = bis.read(bytes))>0){
            //响应数据
            bos.write(bytes,0,len);
        }
        bos.flush();
        //释放资源
        bis.close();
    }

    //添加sku
    @PostMapping
    public boolean addSku(@RequestBody Sku sku) {
        //调用方法，添加sku
        boolean resule = skuService.addSku(sku);
        return resule;
    }

    //修改sku
    @PutMapping("/{skuId}")
    public boolean updateSkuById(@PathVariable Long skuId, @RequestBody Sku sku) {
        //调用方法，修改sku
        return skuService.updateSkuById(skuId, sku);

    }

}

