package com.example.demo.controller;

import com.example.demo.domain.Product;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.ResponseComDto;
import com.example.demo.service.ProductService;
import com.example.demo.service.common.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
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

    private final ProductService productService;
    private final FileService fileService;
    private final View downloadView;

    @Autowired
    public ProductController(ProductService productService, FileService fileService,
                             View downloadView){
        this.productService = productService;
        this.fileService = fileService;
        this.downloadView = downloadView;
    }

    @Deprecated
    @GetMapping("/products/old")
    public ResponseEntity<ResponseComDto> searchProduct(Pageable pageable, PagedResourcesAssembler<Product> assembler){
        Page<Product> products = productService.searchProducts(pageable);

        return new ResponseEntity<>(
                ResponseComDto.builder()
                        .resultMsg("상품 리스트 조회 완료")
                        .resultObj(assembler.toResource(products))
                        .build(), HttpStatus.OK);
    }

    @GetMapping("/products")
    public ResponseEntity<ResponseComDto> searchProductUsingOption(ProductDto.searchOption searchOption, Pageable pageable){
        Page<ProductDto.showSimple> products = productService.searchProductsUsingOption(searchOption, pageable);

        return new ResponseEntity<>(
                ResponseComDto.builder()
                        .resultMsg("상품 리스트 조회 완료")
                        .resultObj(products)
                        .build(), HttpStatus.OK);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ResponseComDto> searchProductDetail(@PathVariable Long productId){
        ProductDto.showDetail product = productService.searchProductsDetail(productId);

        return new ResponseEntity<>(
                ResponseComDto.builder()
                        .resultMsg("상품 조회 완료")
                        .resultObj(product)
                        .build(), HttpStatus.OK);
    }

    @PostMapping("/products")
    public ResponseEntity<ResponseComDto> createProduct(ProductDto.create productDto, Principal principal){
        String username = principal.getName();
        productService.createProduct(productDto, username);
        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                        .resultMsg("상품이 등록되었습니다.")
                        .resultObj(productDto)
                        .build(), HttpStatus.OK);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ResponseComDto> updateProduct(@PathVariable("id") Product product,
                                                        ProductDto.update productDto, Principal principal){
        String username = principal.getName();
        productService.updateProduct(product, productDto, username);
        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                        .resultMsg("상품이 수정되었습니다.")
                        .resultObj(productDto)
                        .build(), HttpStatus.OK);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<ResponseComDto> deleteProduct(@PathVariable("id") Product product){
        productService.deleteProduct(product);
        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                        .resultMsg("상품이 삭제되었습니다.")
                        .resultObj(null)
                        .build(), HttpStatus.OK);
    }

    @GetMapping("/download/excel/{filename}")
    public ModelAndView downloadExcel(@PathVariable String filename, Principal principal){
        String username = principal.getName();

        File file = fileService.getFile(filename);

        ModelAndView mav = new ModelAndView();
        mav.setView(this.downloadView);
        mav.addObject("downloadFile", file);
        mav.addObject("filename", filename);
        mav.addObject("username", username);
        return mav;
    }

    @PostMapping("/upload/excel")
    public ResponseEntity<ResponseComDto> uploadExcel(MultipartFile file) throws Exception {
        fileService.uploadFile(file);

        return new ResponseEntity<ResponseComDto>(
                ResponseComDto.builder()
                        .resultMsg("파일이 업로드되었습니다.")
                        .resultObj(null)
                        .build(), HttpStatus.OK);
    }
}
