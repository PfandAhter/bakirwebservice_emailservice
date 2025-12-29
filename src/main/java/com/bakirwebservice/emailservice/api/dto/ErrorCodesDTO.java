package com.bakirwebservice.emailservice.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorCodesDTO {

    private String id;

    private String error;

    private String description;
}