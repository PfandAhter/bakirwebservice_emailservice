package com.bakirwebservice.emailservice.api.response;


import com.bakirwebservice.emailservice.api.dto.ErrorCodesDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class GetErrorCodeResponse {
    private ErrorCodesDTO errorCode;
}