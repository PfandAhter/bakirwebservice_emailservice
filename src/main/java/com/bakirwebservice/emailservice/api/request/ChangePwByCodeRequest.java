package com.bakirwebservice.emailservice.api.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ChangePwByCodeRequest {

    private String email;
    private String emailCode;
    private String newPassword;
    private String newPasswordAgain;
}
