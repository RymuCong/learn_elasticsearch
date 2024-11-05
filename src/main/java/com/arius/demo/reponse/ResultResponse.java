package com.arius.demo.reponse;

import lombok.Getter;

@Getter
public class ResultResponse {

    private MetadataResponse metadataResponse;
    private Object result;

    public void setMetadataResponse(MetadataResponse metadataResponse) {
        this.metadataResponse = metadataResponse;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
