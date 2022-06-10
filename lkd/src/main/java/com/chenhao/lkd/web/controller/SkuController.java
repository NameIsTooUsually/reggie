package com.chenhao.lkd.web.controller;

import com.chenhao.lkd.pojo.vo.SkuPageVo;
import com.chenhao.lkd.service.SkuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
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
    @GetMapping("/search")
    public SkuPageVo searchByPage(@RequestParam Integer pageIndex, Integer pageSize) {
        //查询页面
        SkuPageVo skuPageVo =  skuService.searchByPage(pageIndex, pageSize);

        return skuPageVo;

    }

    //上传照片
    @PostMapping("/fileUpload")
    public String fileUpload(@RequestBody MultipartFile fileName) throws IOException {
            //1.获取项目根路径下 images的具体路径
            String path = "C:/Develop/project/reggie/lkd/src/main/resources/pages/sku";
            //2.获取文件的名称
            String imagName = fileName.getOriginalFilename();
            //3.将文件名使用UUID进行替换，避免出现重名导致的错误
            //3.1获取文件的后缀名
            String extName = imagName.substring(imagName.lastIndexOf("."));
            //3.2使用UUID生成新的文件名
            String newFileName = UUID.randomUUID()+ extName;
            //4.将文件进行拷贝
            fileName.transferTo(new File(path+"/"+newFileName));
            //5.返回文件路径
            return path+"/"+newFileName;
        }

}

