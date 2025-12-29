package com.bakirwebservice.emailservice.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class VerifyUserRequest {
    private String userEmail;
    private String otp;
    private String type;
}