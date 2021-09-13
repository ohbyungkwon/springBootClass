package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    private String fileName;          // 원래 파일명
    private String fileUploadName;        // 업로드파일명 : 업로드 되는 파일명
    private Long fileSize;            // 파일크기
    private String filePath;          // 파일경로

    private String typeCode;
    //추후 EXCEL 업로드 및 다운로드 로직이 많아지면 해당 코드로 나눌것
}
