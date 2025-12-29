package com.bakirwebservice.emailservice.service;

import com.bakirwebservice.emailservice.api.request.*;

public interface EmailService {

    void sendVerificationEmail(VerifyUserRequest verifyUserRequest);

    void sendPasswordResetEmail(VerifyUserRequest verifyUserRequest);

    void sendResenVerificationEmail(String email, String code);

    void sendCustomEmailByAdmin(SendEmailByAdminRequest request);

    void sendCardApprovalNotification(CardApprovalEmailRequest request);

    void sendCardPendingNotification(CardPendingEmailRequest request);

    void sendCardRejectionNotification(CardRejectionEmailRequest request);
}