package com.arius.demo.controller;

import com.arius.demo.entity.Product;
import com.arius.demo.service.ElasticsearchIndexService;
import com.arius.demo.service.ProductService;
import com.arius.demo.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClientIndexController {

    private final S3Service s3Service;
    private final ProductService productService;
    private final ElasticsearchIndexService elasticsearchIndexService;

    @Autowired
    public ClientIndexController(S3Service s3Service, ProductService productService, ElasticsearchIndexService elasticsearchIndexService) {
        this.s3Service = s3Service;
        this.productService = productService;
        this.elasticsearchIndexService = elasticsearchIndexService;
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<?> deleteImage(@PathVariable String key) {
        s3Service.deleteImage(key);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/download/{file}")
    public ResponseEntity<?> download(@PathVariable String file) {
        ResponseEntity<?> data = s3Service.download(file);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", file);
        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }


    @PostMapping("/loadData")
    public ResponseEntity<?> indexData() {
        elasticsearchIndexService.pushDataToElasticsearch();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> getAllProducts() {
        List<Product> productSearchHits = productService.findAll();
        return new ResponseEntity<>(productSearchHits, HttpStatus.OK);
    }

    @GetMapping("find/{name}")
    public ResponseEntity<?> getProducts(@PathVariable String name) {
        return new ResponseEntity<>(elasticsearchIndexService.searchProductsByName(name), HttpStatus.OK);
    }
}