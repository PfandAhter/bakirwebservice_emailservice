package com.bakirwebservice.emailservice.api;

import com.bakirwebservice.emailservice.api.request.CardApprovalEmailRequest;
import com.bakirwebservice.emailservice.api.request.CardPendingEmailRequest;
import com.bakirwebservice.emailservice.api.request.CardRejectionEmailRequest;
import com.bakirwebservice.emailservice.api.response.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface CardEmailControllerApi {

    @PostMapping("/approval")
    ResponseEntity<BaseResponse> sendCardApprovalEmail(@RequestBody CardApprovalEmailRequest request);

    @PostMapping("/pending")
    ResponseEntity<BaseResponse> sendCardPendingEmail(@RequestBody CardPendingEmailRequest request);

    @PostMapping("/rejection")
    ResponseEntity<BaseResponse> sendCardRejectionEmail(@RequestBody CardRejectionEmailRequest request);
}
