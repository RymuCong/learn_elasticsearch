package com.arius.demo.dto;

import com.arius.demo.entity.Category;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@Data
@Document(indexName = "product")
public class EProduct implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field(name = "name", type = FieldType.Text)
    private String productName;

    @Field(name = "price", type = FieldType.Integer)
    private Integer price;

    @Field(name = "country", type = FieldType.Text)
    private String country;

    @Field(name = "image", type = FieldType.Text)
    private String image;

    @Field(name = "category", type = FieldType.Nested)
    private Category category;

}
