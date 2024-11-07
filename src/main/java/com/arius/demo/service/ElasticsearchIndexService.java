package com.arius.demo.service;

import com.arius.demo.dto.EProduct;
import com.arius.demo.entity.Product;
import com.arius.demo.mapper.ProductMapper;
import com.arius.demo.repository.EProductRepository;
import com.arius.demo.repository.ProductRepository;
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

    private final ProductRepository productRepository;
    private final ElasticsearchOperations elasticsearchOperations;

    @Autowired
    public ElasticsearchIndexService(ProductRepository productRepository, ElasticsearchOperations elasticsearchOperations) {
        this.productRepository = productRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public void pushDataToElasticsearch() {
        List<Product> products = productRepository.findAll();
        List<EProduct> eProducts = products.stream()
                .map(ProductMapper::toEProduct)
                .toList();

        IndexCoordinates indexCoordinates = IndexCoordinates.of("product");
        IndexOperations indexOperations = elasticsearchOperations.indexOps(indexCoordinates);

        for (EProduct eProduct : eProducts) {
            EProductToDocument(indexCoordinates, eProduct);
        }
    }

    private void EProductToDocument(IndexCoordinates indexCoordinates, EProduct eProduct) {
        Document document = Document.create();
        document.put("id", eProduct.getId());
        document.put("name", eProduct.getProductName());
        document.put("price", eProduct.getPrice());
        document.put("country", eProduct.getCountry());
        document.put("image", eProduct.getImage());
        document.put("category", eProduct.getCategory());

        elasticsearchOperations.save(document, indexCoordinates);
    }

    public List<EProduct> searchProductsByName(String name) {
        Criteria criteria = Criteria.where("name").contains(name);
        CriteriaQuery query = new CriteriaQuery(criteria);
        SearchHits<EProduct> searchHits = elasticsearchOperations.search(query, EProduct.class, IndexCoordinates.of("product"));
        return searchHits.getSearchHits().stream()
                .map(hit -> hit.getContent())
                .collect(Collectors.toList());
    }

    public void save(EProduct eProduct) {
        IndexCoordinates indexCoordinates = IndexCoordinates.of("product");
        EProductToDocument(indexCoordinates, eProduct);
    }
}