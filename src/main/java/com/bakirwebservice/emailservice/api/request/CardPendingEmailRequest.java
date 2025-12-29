package com.bakirwebservice.emailservice.api.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardPendingEmailRequest {
    private String userEmail;
    private String cardHolderName;
    private String cardType;
    private String applicationDate;
}