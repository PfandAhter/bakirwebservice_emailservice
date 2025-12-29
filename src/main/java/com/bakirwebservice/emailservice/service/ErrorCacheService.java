package com.bakirwebservice.emailservice.service;

import com.bakirwebservice.emailservice.entity.ErrorCodes;

public interface ErrorCacheService {

    ErrorCodes getErrorCodeByErrorId(String code);

//    void refreshAllErrorCodesCache();
}