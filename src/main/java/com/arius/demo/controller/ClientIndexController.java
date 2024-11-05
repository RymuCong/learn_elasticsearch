package com.arius.demo.controller;

import com.arius.demo.dto.EProduct;
import com.arius.demo.entity.Product;
import com.arius.demo.service.ProductService;
import com.arius.demo.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.regexpQuery;

@RestController
public class ClientIndexController {

    private final ElasticsearchOperations elasticsearchOperations;

    private final S3Service s3Service;

    private final ProductService productService;

    @Autowired
    public ClientIndexController(ElasticsearchOperations elasticsearchOperations, S3Service s3Service, ProductService productService) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.s3Service = s3Service;
        this.productService = productService;
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

    @GetMapping("/find/{name}")
    public ResponseEntity<?> getProducts(@PathVariable String name) {
//        String nameToFind = ".*" + name + ".*";
        List<EProduct> productSearchHits
                = productService.findByProductNameContaining(name);
        return new ResponseEntity<>(productSearchHits, HttpStatus.OK);
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> getAllProducts() {
        List<EProduct> productSearchHits = productService.findAll();
        return new ResponseEntity<>(productSearchHits, HttpStatus.OK);
    }

//    @GetMapping("find/{name}")
//    public ResponseEntity<?> getProducts(@PathVariable String name) {
//        Query searchQuery = new NativeSearchQueryBuilder()
//                .withFilter(regexpQuery("title", ".*data.*"))
//                .build();
//        SearchHits<Product> productSearchHits =
//                elasticsearchOperations.search(searchQuery, Product.class, IndexCoordinates.of("product"));
//        return new ResponseEntity<>(productSearchHits, HttpStatus.OK);
//    }

}