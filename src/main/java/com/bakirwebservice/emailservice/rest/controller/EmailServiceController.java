package com.bakirwebservice.emailservice.rest.controller;

import com.bakirwebservice.emailservice.api.request.ChangePwByCodeRequest;
import com.bakirwebservice.emailservice.api.request.EmailValidatorRequest;
import com.bakirwebservice.emailservice.api.request.ForgetPasswordRequest;
import com.bakirwebservice.emailservice.api.request.UserActivateRequest;
import com.bakirwebservice.emailservice.api.response.BaseResponse;
import com.bakirwebservice.emailservice.rest.controller.interfaces.EmailRestServiceApi;
import com.bakirwebservice.emailservice.rest.service.EmailServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("email")
@RequiredArgsConstructor
@Slf4j

public class EmailServiceController implements EmailRestServiceApi {

    private final EmailServiceImpl emailService;

    @Override
    public ResponseEntity<BaseResponse> emailValidator(EmailValidatorRequest emailValidatorRequest, HttpServletRequest request)throws MessagingException {
        return ResponseEntity.ok(emailService.emailValidator(emailValidatorRequest));
    }

    @Override
    public ResponseEntity<Boolean> activateCodeMatched(UserActivateRequest userActivateRequest, HttpServletRequest request) {
        return ResponseEntity.ok(emailService.isActivateCodeMatched(userActivateRequest));
    }

    @Override
    public ResponseEntity<BaseResponse> forgetPasswordCreateCode(ForgetPasswordRequest forgetPasswordRequest, HttpServletRequest request)throws MessagingException {
        return ResponseEntity.ok(emailService.createForgetPasswordCodeSendEmail(forgetPasswordRequest));
    }

    @Override
    public ResponseEntity<Boolean> changePasswordCodeIsMatched(ChangePwByCodeRequest changePwByCodeRequest, HttpServletRequest request) throws MessagingException {
        return ResponseEntity.ok(emailService.changePasswordCodeIsMatched(changePwByCodeRequest));
    }


}
