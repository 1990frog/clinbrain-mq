package com.clinbrain.mq.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    public static boolean upload(MultipartFile file){
        if (file.isEmpty()) {
            return false;
        }
        String fileName = file.getOriginalFilename();
        File dest = new File(uploadPath() + fileName);
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取上传文件路径，默认为： 当前用户工作目录/upload/
     */
    public static String uploadPath(){
        String dirPath = System.getProperty("user.home") +File.separator+ "upload" + File.separator;
        File baseDir = new File(dirPath);
        if(!baseDir.exists()){
            baseDir.mkdirs();
        }
        return dirPath;
    }

}
