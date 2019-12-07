package com.webstore.service.impl;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.webstore.service.IUploadService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

@Service

public class UploadServiceImpl implements IUploadService {
    @Autowired
    private FastFileStorageClient storageClient;
//    定义文件类型
    private static final List<String> CONTENT_TYPES = Arrays.asList("image/jpeg", "image/gif","application/x-jpg");
//    定义日志
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadServiceImpl.class);
    @Override
    public String upload(MultipartFile file) {
//        获取文件名
        String originalFilename = file.getOriginalFilename();
//        获取文件类型
        String contentType = file.getContentType();
        System.out.println("contentType--->"+contentType);
//        判断是否符合文件类型
        if (!CONTENT_TYPES.contains(contentType)){
//            文件不符合 返回null
            LOGGER.info("文件类型不合法：{}", originalFilename);
            return null;
        }
//        读取文件

        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage==null){
                LOGGER.info("文件内容不合法：{}", originalFilename);
                return null;
            }
            System.out.println("bufferedImage--->"+bufferedImage);

//            保存到本地
            /*file.transferTo(new File("D:\\BlockChain\\ideajetbrains\\workspace\\plantasunstore\\uploadimg\\"+originalFilename));
            String url = "http://image.plantasun.com/"+originalFilename;
            System.out.println("url-->"+url);
//            生成url*/

            //获取文件后缀
            String ext = StringUtils.substringAfterLast(originalFilename, ".");
            StorePath storePath = this.storageClient.uploadFile(file.getInputStream(), file.getSize(), ext, null);
            // 生成url地址，返回
            String url =  "http://image.plantasun.com/" + storePath.getFullPath();
            return url;
        } catch (Exception e) {
            LOGGER.info("服务器内部错误：{}", originalFilename);
            e.printStackTrace();
        }
        return null;

    }
}
