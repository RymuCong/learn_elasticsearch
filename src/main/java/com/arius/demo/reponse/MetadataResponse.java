package com.arius.demo.reponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetadataResponse {

    private String code;
    private String message;
    private String noOfRecords;
    private String data;
}
