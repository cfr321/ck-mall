package com.cako.uplaod.service;

import com.cako.enums.ExceptionEnum;
import com.cako.exception.CkException;
import com.cako.uplaod.config.UploadProperties;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.util.logging.Log;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@EnableConfigurationProperties(UploadProperties.class)
public class UploadService {
    @Autowired
    private UploadProperties prop;
    @Autowired
    private FastFileStorageClient storageClient;
    /**
     * 上传到fdns
     */
    //图片上传
    public String uploadImage(MultipartFile file) {
        try {
            //检验文件类型
             String contentType=file.getContentType();
             if(!prop.getAllowType().contains(contentType)){
                 throw new CkException(ExceptionEnum.FILE_TYPE_INVALID);
             }
             //校验文件的内容
             BufferedImage image=ImageIO.read(file.getInputStream());
             if(image==null){
                 throw new CkException(ExceptionEnum.FILE_TYPE_INVALID);
             }
             String extension= StringUtils.substringAfterLast(file.getOriginalFilename(),".");
             //上传到FastDfs
             StorePath storePath = storageClient.uploadFile(
                    file.getInputStream(), file.getSize(), extension, null);
             //返回路径
             return prop.getBaseUrl()+storePath.getFullPath();
        } catch (IOException e) {
            //上传失败
            log.error("[文件上传] 文件上传失败",e);
            throw new CkException(ExceptionEnum.FILE_UPLOAD_ERROR);
        }
    }
}
