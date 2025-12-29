package com.bakirwebservice.emailservice.service.impl;

import com.bakirwebservice.emailservice.constant.ErrorCodeConstants;
import com.bakirwebservice.emailservice.entity.ErrorCodes;
import com.bakirwebservice.emailservice.service.ErrorCacheService;
import org.springframework.stereotype.Service;

import static com.bakirwebservice.emailservice.constant.ErrorCodeConstants.SERVICE_UNAVAILABLE;

@Service
public class ErrorCacheServiceImpl implements ErrorCacheService {

    @Override
    public ErrorCodes getErrorCodeByErrorId(String code){
        if(code.equals(SERVICE_UNAVAILABLE)){
            return ErrorCodes.builder()
                    .id(SERVICE_UNAVAILABLE)
                    .error("Hizmet Şuan Mevcut Değil")
                    .description("İlgili bankacılık servisine şu an ulaşılamıyor. Lütfen kısa bir süre sonra tekrar deneyiniz.")
                    .httpStatus(500)
                    .build();
        }else if (code.equals(ErrorCodeConstants.SYSTEM_ERROR)){
            return ErrorCodes.builder()
                    .id(ErrorCodeConstants.SYSTEM_ERROR)
                    .error("Sistem Hatası")
                    .description("İşleminiz sırasında beklenmedik bir hata oluştu. Lütfen daha sonra tekrar deneyiniz.")
                    .httpStatus(500)
                    .build();
        }else {
            return ErrorCodes.builder()
                    .id("UNKNOWN_ERROR")
                    .error("Bilinmeyen Hata")
                    .description("Bilinmeyen bir hata oluştu.")
                    .httpStatus(500)
                    .build();
        }
    }
}