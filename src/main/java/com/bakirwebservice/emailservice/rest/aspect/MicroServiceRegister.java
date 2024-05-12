package com.bakirwebservice.emailservice.rest.aspect;

import com.bakirwebservice.emailservice.api.client.MicroServiceRegisterClient;
import com.bakirwebservice.emailservice.api.request.MicroServiceReadyRequest;
import com.bakirwebservice.emailservice.api.request.MicroServiceStoppedRequest;
import com.bakirwebservice.emailservice.rest.util.Util;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;

@Service
@Component
@Slf4j
@RequiredArgsConstructor
public class MicroServiceRegister {

    private final MicroServiceRegisterClient microServiceRegisterClient;

    private String microServiceCode;

    private static final String microServiceName = "EMAIL-SERVICE";

    @EventListener(ApplicationReadyEvent.class)
    public void logToDataBaseServiceReady(){
        microServiceCode = Util.generateCode();
        MicroServiceReadyRequest microServiceReadyRequest = new MicroServiceReadyRequest();
        microServiceReadyRequest.setMicroServiceCode(microServiceCode);
        microServiceReadyRequest.setMicroServiceStatus("UP");
        microServiceReadyRequest.setMicroServiceErrorCode("85000");
        microServiceReadyRequest.setMicroServiceReadyDate(Timestamp.from(Instant.now()));
        microServiceReadyRequest.setMicroServiceName(microServiceName);

        microServiceRegisterClient.microServiceReady(microServiceReadyRequest);
    }


    @PreDestroy
    public void testLogToDatabaseStopped(){
        MicroServiceStoppedRequest microServiceStoppedRequest = new MicroServiceStoppedRequest();
        microServiceStoppedRequest.setMicroServiceStoppedDate(Timestamp.from(Instant.now()));
        microServiceStoppedRequest.setMicroServiceName(microServiceName);
        microServiceStoppedRequest.setMicroServiceErrorCode("85000");
        microServiceStoppedRequest.setMicroServiceStatus("DOWN");
        microServiceStoppedRequest.setMicroServiceCode(microServiceCode);

        microServiceRegisterClient.microServiceStopped(microServiceStoppedRequest);
    }
}
