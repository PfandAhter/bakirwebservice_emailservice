package com.bakirwebservice.emailservice.rest.controller.interfaces;

import com.bakirwebservice.emailservice.api.request.ChangePwByCodeRequest;
import com.bakirwebservice.emailservice.api.request.EmailValidatorRequest;
import com.bakirwebservice.emailservice.api.request.ForgetPasswordRequest;
import com.bakirwebservice.emailservice.api.request.UserActivateRequest;
import com.bakirwebservice.emailservice.api.response.BaseResponse;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface EmailRestServiceApi {

    @PostMapping(path = "validator" , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse> emailValidator (@RequestBody EmailValidatorRequest emailValidatorRequest , HttpServletRequest request) throws MessagingException;

    @PostMapping(path = "activate" , produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Boolean> activateCodeMatched (@RequestBody UserActivateRequest userActivateRequest , HttpServletRequest request);

    @PostMapping(path = "password/forget" ,produces = MediaType.APPLICATION_JSON_VALUE , consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<BaseResponse> forgetPasswordCreateCode (@RequestBody ForgetPasswordRequest forgetPasswordRequest, HttpServletRequest request) throws MessagingException;

    @PostMapping(path = "password/change/code",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Boolean> changePasswordCodeIsMatched (@RequestBody ChangePwByCodeRequest changePwByCodeRequest , HttpServletRequest request) throws MessagingException;

}
