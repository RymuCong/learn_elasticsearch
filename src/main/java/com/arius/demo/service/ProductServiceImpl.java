package com.arius.demo.service;

import com.arius.demo.dto.EProduct;
import com.arius.demo.entity.Product;
import com.arius.demo.mapper.ProductMapper;
import com.arius.demo.repository.EProductRepository;
import com.arius.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final EProductRepository eProductRepository;
    private final S3Service s3Service;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, EProductRepository eProductRepository, S3Service s3Service) {
        this.productRepository = productRepository;
        this.eProductRepository = eProductRepository;
        this.s3Service = s3Service;
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public EProduct findById(String id) {
        return productRepository.findById(id)
                .map(ProductMapper::toEProduct)
                .orElse(null);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
        EProduct eProduct = ProductMapper.toEProduct(product);
        eProductRepository.save(eProduct);
    }

    @Override
    public void save(Product product, MultipartFile file) {
        String imageUrl = s3Service.uploadFile(file);
        if (imageUrl != null) {
            product.setImage(imageUrl);
            productRepository.save(product);
            EProduct eProduct = ProductMapper.toEProduct(product);
            eProductRepository.save(eProduct);
        } else {
            System.err.println("File upload failed: " + file.getOriginalFilename());
        }
    }

    @Override
    public void deleteById(String id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            if (product.getImage() != null) {
                String imageUrl = product.getImage();
                String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                s3Service.deleteImage(fileName);
            }
            productRepository.deleteById(id);
            eProductRepository.deleteById(id);
        }
    }

    @Override
    public List<EProduct> findByProductNameContaining(String name) {
        return eProductRepository.findByProductName(name);
    }
}