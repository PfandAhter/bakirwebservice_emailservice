package com.bakirwebservice.emailservice.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonPropertyOrder({
        "Statu",
        "HataKodu",
        "HataMesaji"
})
public class BaseResponse {
    @JsonProperty("Statu")
    private String status = "1";

    @JsonProperty("HataKodu")
    private String errorCode = "SUCCESS";

    @JsonProperty("HataMesaji")
    private String errorDescription = "SUCCESS";
}