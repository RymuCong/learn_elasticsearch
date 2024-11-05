package com.arius.demo.service;

import com.arius.demo.dto.EProduct;
import com.arius.demo.entity.Product;
import com.arius.demo.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElasticsearchIndexService {

    private final ProductService productService;
    private final ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public ElasticsearchIndexService(ProductService productService, ElasticsearchOperations elasticsearchOperations) {
        this.productService = productService;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public void pushDataToElasticsearch() {
        List<Product> products = productService.findAll();
        List<EProduct> eProducts = products.stream()
                .map(ProductMapper::toEProduct)
                .toList();

        IndexCoordinates indexCoordinates = IndexCoordinates.of("product");
        IndexOperations indexOperations = elasticsearchOperations.indexOps(indexCoordinates);

        for (EProduct eProduct : eProducts) {
            Document document = Document.create();
            document.put("id", eProduct.getId());
            document.put("name", eProduct.getProductName());
            document.put("price", eProduct.getPrice());
            document.put("country", eProduct.getCountry());
            document.put("image", eProduct.getImage());
            document.put("category", eProduct.getCategory());

            elasticsearchOperations.save(document, indexCoordinates);
        }
    }

    public List<Product> searchProductsByName(String name) {
        Criteria criteria = Criteria.where("name").contains(name);
        CriteriaQuery query = new CriteriaQuery(criteria);
        SearchHits<Product> searchHits = elasticsearchOperations.search(query, Product.class, IndexCoordinates.of("product"));
        return searchHits.getSearchHits().stream()
                .map(hit -> hit.getContent())
                .collect(Collectors.toList());
    }
}