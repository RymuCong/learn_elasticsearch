package com.arius.demo.repository;

import com.arius.demo.dto.EProduct;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EProductRepository extends ElasticsearchRepository<EProduct, String> {

//    List<Product> findAll();

//    List<EProduct> findByProductName(String name);

    List<EProduct> findByCategoryId(int categoryId);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"product.productName\": \"?0\"}}]}}")
    List<EProduct> findByProductName(String name);
}