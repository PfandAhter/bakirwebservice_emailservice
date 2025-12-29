package com.bakirwebservice.emailservice.api.response;

import com.bakirwebservice.emailservice.api.dto.ErrorCodesDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter

public class GetAllErrorCodesResponse {
    private List<ErrorCodesDTO> errorCodes;
}