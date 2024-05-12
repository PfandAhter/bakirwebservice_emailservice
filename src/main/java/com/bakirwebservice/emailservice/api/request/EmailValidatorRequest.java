package com.bakirwebservice.emailservice.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class EmailValidatorRequest{

    private String email;

    private String username;
}
