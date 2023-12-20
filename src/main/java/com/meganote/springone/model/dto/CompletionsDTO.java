package com.meganote.springone.model.dto;

import lombok.Data;

@Data
public class CompletionsDTO {

    private String modelId;

    private String recordId;

    private String sourceType = "api";

    private Integer chatType = 0;

    private String input;

    private String userId;

}