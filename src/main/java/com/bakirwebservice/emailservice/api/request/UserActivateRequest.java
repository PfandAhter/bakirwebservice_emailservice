package com.bakirwebservice.emailservice.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserActivateRequest {
    private String username;
    private String emailValidatorCode;
}

