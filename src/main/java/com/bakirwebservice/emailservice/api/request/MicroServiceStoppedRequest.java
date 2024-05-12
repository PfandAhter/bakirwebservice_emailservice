package com.bakirwebservice.emailservice.api.request;


import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter

public class MicroServiceStoppedRequest {

    private String microServiceCode;

    private String microServiceName;

    private String microServiceErrorCode;

    private Timestamp microServiceStoppedDate;

    private String microServiceStatus;
}
