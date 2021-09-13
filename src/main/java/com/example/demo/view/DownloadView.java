package com.example.demo.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

@Slf4j
@Component
public class DownloadView extends AbstractView {

    public DownloadView(){
        this.setContentType("application/download;charset=utf-8");
    }

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        OutputStream out = null;
        FileInputStream in = null;

        try {
            File downloadFile = (File) model.get("downloadFile");
            String filename = (String) model.get("filename");
            String username = (String) model.get("username");
            log.debug("<{}>, downloadFile <{}> filename <{}>", username, downloadFile, filename);

            this.setDownloadFileName(filename, response);
            this.setResponseContentType(request, response);
            response.setContentLength((int) downloadFile.length());

            out = response.getOutputStream();
            in = new FileInputStream(downloadFile);
            FileCopyUtils.copy(in, out);

        } catch (IOException e) {
            e.getMessage();
        } finally {
            if( out != null ) {out.flush(); out.close();}
            if( in != null ) in.close();
        }
    }

    private void setDownloadFileName(String filename, HttpServletResponse response) throws UnsupportedEncodingException {
        String fileType = (filename.contains(".xls") || filename.contains(".xlsx")) ? "excel" :  "zip";
        if (fileType.equals("excel")) {
            response.setHeader("Content-Description", "Excel Data");
        } else if(fileType.equals("zip")){
            response.setHeader("Content-Description", "Zip Data");
        }

        response.setHeader("Content-Disposition", filename);
        response.setContentType(getContentType(fileType));
        response.setHeader("Content-Transfer-Encoding", "binary;");
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");
    }

    private String getContentType(String fileType) throws UnsupportedEncodingException {
        String contentType = "application/octet-stream";
        if (fileType.equals("excel"))
            contentType = "application/vnd.ms-excel; charset=utf-8";
        else if (fileType.equals("zip"))
            contentType = "application/zip";
        //추후 생겨나는 다운로드 파일이 있다면 추가할 것.

        return contentType;
    }
}