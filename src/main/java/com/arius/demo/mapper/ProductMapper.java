package com.arius.demo.mapper;

import com.arius.demo.dto.EProduct;
import com.arius.demo.entity.Product;

public class ProductMapper {

    public static Product toProduct(EProduct eProduct) {
        if (eProduct == null) {
            return null;
        }

        Product product = new Product();
        product.setId(eProduct.getId());
        product.setProductName(eProduct.getProductName());
        product.setPrice(eProduct.getPrice());
        product.setCountry(eProduct.getCountry());
        product.setImage(eProduct.getImage());
        product.setCategory(eProduct.getCategory());

        return product;
    }

    public static EProduct toEProduct(Product product) {
        if (product == null) {
            return null;
        }

        return new EProduct(
                product.getId(),
                product.getProductName(),
                product.getPrice(),
                product.getCountry(),
                product.getImage(),
                product.getCategory()
        );
    }
}