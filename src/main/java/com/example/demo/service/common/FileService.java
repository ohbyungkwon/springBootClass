package com.example.demo.service.common;

import com.example.demo.exception.BadClientException;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class FileService {

    @Value("${file.download.path}")
    private String downloadPath;

    @Value("${file.upload.path}")
    private String uploadPath;

    public File getFile(String filename){
        File file = new File(downloadPath, filename);
        if(file.length() == 0)
            throw new BadClientException("파일이 존재하지 않습니다.");
        return file;
    }

    public void uploadFile(MultipartFile file) throws Exception{
        String filename = file.getOriginalFilename();
        if(filename == null)
            throw new BadClientException("파일이 존재하지 않습니다.");

        File fileDir = new File(uploadPath);
        if(!fileDir.exists()) {
            throw new BadClientException("경로가 존재하지 않습니다.");
        }

        File fileInfo = new File(uploadPath, filename + RandomString.make()).;

        file.transferTo(fileInfo);
    }
}
