package com.taotao.controller;

import com.taotao.common.utils.FastDFSClient;
import com.taotao.common.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
public class UploadController {

    @Value("${TAOTAO_IMAGE_SERVER_URL}")
    private String TAOTAO_IMAGE_SERVER_URL;

    @RequestMapping(value = "/pic/upload", produces = MediaType.TEXT_PLAIN_VALUE + ";charset=utf-8")
    @ResponseBody
    public String uploadFile(MultipartFile uploadFile) {

        try {
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:resource/fastdfs.conf");
            //获取扩展名
            String filename = uploadFile.getOriginalFilename();
            String extName = filename.substring(filename.lastIndexOf(".") + 1);
            //文件路径
            String uri = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
            //拼接完整的url路径
            String url = TAOTAO_IMAGE_SERVER_URL + uri;
            Map<String, Object> map = new HashMap<>();
            map.put("error", 0);
            map.put("url", url);
            return JsonUtils.objectToJson(map);
        } catch (Exception e) {
            e.printStackTrace();
            Map<String, Object> map = new HashMap<>();
            map.put("error", 1);
            map.put("message", "图片上传失败");
            return JsonUtils.objectToJson(map);
        }
    }
}
