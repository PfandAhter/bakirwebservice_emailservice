package com.bakirwebservice.emailservice.controller;

import com.bakirwebservice.emailservice.api.CardEmailControllerApi;
import com.bakirwebservice.emailservice.api.request.CardApprovalEmailRequest;
import com.bakirwebservice.emailservice.api.request.CardPendingEmailRequest;
import com.bakirwebservice.emailservice.api.request.CardRejectionEmailRequest;
import com.bakirwebservice.emailservice.api.response.BaseResponse;
import com.bakirwebservice.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email/card")
@RequiredArgsConstructor
public class CardEmailController implements CardEmailControllerApi {

    private final EmailService emailService;

    @Override
    public ResponseEntity<BaseResponse> sendCardApprovalEmail(CardApprovalEmailRequest request) {
        emailService.sendCardApprovalNotification(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new BaseResponse("E-posta başarıyla gönderildi"));
    }

    @Override
    public ResponseEntity<BaseResponse> sendCardPendingEmail(CardPendingEmailRequest request) {
        emailService.sendCardPendingNotification(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new BaseResponse("E-posta başarıyla gönderildi"));
    }

    @Override
    public ResponseEntity<BaseResponse> sendCardRejectionEmail(CardRejectionEmailRequest request) {
        emailService.sendCardRejectionNotification(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new BaseResponse("E-posta başarıyla gönderildi"));
    }
}