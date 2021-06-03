package com.lagou.controller;

import com.github.pagehelper.PageInfo;
import com.lagou.domain.PromotionAd;
import com.lagou.domain.PromotionAdVo;
import com.lagou.domain.ResponseResult;
import com.lagou.service.PromotionAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/PromotionAd")
public class PromotionAdController {
    @Autowired
    private PromotionAdService promotionAdService;

    @RequestMapping("/findAllPromotionAdByPage")
    public ResponseResult findAllPromotionAdByPage(PromotionAdVo promotionAdVo){
        PageInfo<PromotionAd> allPromotionAdByPage = promotionAdService.findAllPromotionAdByPage(promotionAdVo);
        ResponseResult responseResult = new ResponseResult(true, 200, "广告分页查询成功", allPromotionAdByPage);
        return responseResult;
    }

    /*
    课程图片上传
     */
    @RequestMapping("/PromotionAdUpload")
    public ResponseResult fileUpload(@RequestParam MultipartFile file, HttpServletRequest request) throws IOException {
        //1.判断接受的上传文件是否为空
        if(file.isEmpty()){
            throw new RuntimeException();
        }

        //2.获取项目部署路径
        String realPath = request.getServletContext().getRealPath("/");
        String substring = realPath.substring(0, realPath.indexOf("ssm_web"));
        //3.获取原文件名
        String originalFilename = file.getOriginalFilename();
        //4.生成新的文件名
        String newFileName = System.currentTimeMillis() + originalFilename.substring(originalFilename.lastIndexOf("."));
        //5.文件上传
        String uploadPath=substring+"upload\\";
        File filePath = new File(uploadPath, newFileName);
        //如果目录不存在或创建目录
        if(!filePath.getParentFile().exists()){
            filePath.getParentFile().mkdirs();
            System.out.println("创建目录:"+filePath);
        }
        //图片就可以真正的上传
        file.transferTo(filePath);

        //6.将文件名和文件路径返回，进行响应
        HashMap<String, String> map = new HashMap<>();
        map.put("fileName",newFileName);
        map.put("filePath","http://localhost:8080/upload/"+newFileName);

        ResponseResult responseResult = new ResponseResult(true, 200, "上传成功", map);

        return responseResult;
    }

    @RequestMapping("/updatePromotionAdStatus")
    public ResponseResult updatePromotionAdStatus(int id,int status){
        promotionAdService.updatePromotionAdStatus(id,status);
        ResponseResult responseResult = new ResponseResult(true, 200, "广告上下线更新成功", null);
        return responseResult;
    }
}
