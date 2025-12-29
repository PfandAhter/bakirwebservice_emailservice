package com.bakirwebservice.emailservice.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class SendEmailByAdminRequest {
    private String email;

    private String subject;

    private String message;
}