package com.arius.demo.service;

import com.arius.demo.dto.EProduct;
import com.arius.demo.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    EProduct findById(String id);
    void save(Product product);
    void save(Product product, MultipartFile file);
    void deleteById(String id);
    List<EProduct> findByProductNameContaining(String name);
}