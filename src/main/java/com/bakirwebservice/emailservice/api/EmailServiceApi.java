package com.bakirwebservice.emailservice.api;

import com.bakirwebservice.emailservice.api.request.*;
import com.bakirwebservice.emailservice.api.response.BaseResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface EmailServiceApi {

    @PostMapping(path = "/user/password-reset")
    BaseResponse sendPasswordResetEmail(@RequestBody VerifyUserRequest verifyUserRequest);

    @PostMapping(path = "/user/verify")
    BaseResponse verifyUser(@RequestBody VerifyUserRequest verifyUserRequest);

    @PostMapping(path = "/user/resend-otp")
    BaseResponse resendOtp(@RequestBody ResendOtpRequest resendOtpRequest);

    @PostMapping(path = "/send-email-by-admin")
    BaseResponse sendEmailByAdmin(@RequestBody SendEmailByAdminRequest sendEmailByAdminRequest);
}