package com.bakirwebservice.emailservice.controller;

import com.bakirwebservice.emailservice.api.request.*;
import com.bakirwebservice.emailservice.api.response.BaseResponse;
import com.bakirwebservice.emailservice.api.EmailServiceApi;
import com.bakirwebservice.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
@RequiredArgsConstructor
@Slf4j

public class EmailServiceController implements EmailServiceApi {

    private final EmailService emailService;

    @Override
    public BaseResponse sendPasswordResetEmail(VerifyUserRequest verifyUserRequest) {
        emailService.sendPasswordResetEmail(verifyUserRequest);
        return new BaseResponse("Password reset email sent successfully");
    }


    @Override
    public BaseResponse verifyUser(VerifyUserRequest verifyUserRequest) {
        emailService.sendVerificationEmail(verifyUserRequest);
        return new BaseResponse("Verification email sent successfully");
    }

    @Override
    public BaseResponse resendOtp(ResendOtpRequest resendOtpRequest) {
        return null;
    }

    @Override
    public BaseResponse sendEmailByAdmin(SendEmailByAdminRequest sendEmailByAdminRequest) {
        emailService.sendCustomEmailByAdmin(sendEmailByAdminRequest);
        return new BaseResponse("Email sent by admin successfully");
    }
}