package com.example.demo.controller;

import com.example.demo.dto.ProductDto;
import com.example.demo.dto.ResponseComDto;
import com.example.demo.service.ProductService;
import com.example.demo.service.common.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import java.io.File;
import java.security.Principal;
import java.util.List;

@RestController
public class ProductController extends AbstractController {

    @Autowired private ProductService productService;
    @Autowired private FileService fileService;
    @Autowired private View downloadView;

    @GetMapping("/products")
    public ResponseEntity<?> searchProduct(){
        List<ProductDto> productDtoList = productService.showProduct();

        return ResponseEntity.ok(productDtoList);
    }

    @GetMapping("/download/excel/{filename}")
    public ModelAndView downloadExcel(@PathVariable String filename, Principal principal){
        String username = this.getUsername(principal);

        File file = fileService.getFile(filename);

        ModelAndView mav = new ModelAndView();
        mav.setView(this.downloadView);
        mav.addObject("downloadFile", file);
        mav.addObject("filename", filename);
        mav.addObject("username", username);
        return mav;
    }

    @PostMapping("/upload/excel")
    public ResponseEntity<ResponseComDto> uploadExcel(MultipartFile file, Principal principal) throws Exception{
        String username = this.getUsername(principal);

        fileService.uploadFile(file);

        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                .resultMsg("파일이 업로드되었습니다.")
                .resultObj(null)
                .build(), HttpStatus.OK);
    }

//    @PostConstruct("/upload/products")
//    public ResponseEntity<ResponseComDto> uploadProductByExcel(MultipartFile products, Principal principal){
//        String username = this.getUsername(principal);
//
//
//    }

//    @PostMapping("/products/")
//    public ResponseEntity<?> createProduct(){
//
//    }
//
//    @PutMapping("/products/{id}")
//    public ResponseEntity<?> updateProduct(@PathVariable Long id){
//
//    }
//    @DeleteMapping("/products/{id}")
//    public ResponseEntity<?> deleteProduct(@PathVariable Long id){
//
//    }
}
