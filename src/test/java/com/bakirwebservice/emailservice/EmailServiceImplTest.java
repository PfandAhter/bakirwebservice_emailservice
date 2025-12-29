package com.bakirwebservice.emailservice;

import com.bakirwebservice.emailservice.api.request.UserActivateRequest;
import com.bakirwebservice.emailservice.entity.EmailValidator;
import com.bakirwebservice.emailservice.repository.EmailValidatorRepository;
import com.bakirwebservice.emailservice.service.impl.EmailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class EmailServiceImplTest {

    @Mock
    private EmailValidatorRepository validatorRepository;

    @InjectMocks
    private EmailServiceImpl emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void isActivateCodeMatchedReturnsTrueWhenCodeMatches() {
        UserActivateRequest request = new UserActivateRequest();
        request.setEmailValidatorCode("validCode");
        request.setUsername("validUser");

        EmailValidator validator = new EmailValidator();
        when(validatorRepository.findEmailValidatorByEmailValidatorIdAndUsername(request.getEmailValidatorCode(), request.getUsername())).thenReturn(validator);

        assertTrue(emailService.isActivateCodeMatched(request));

        verify(validatorRepository, times(1)).delete(validator);
    }

    @Test
    void isActivateCodeMatchedReturnsFalseWhenCodeDoesNotMatch() {
        UserActivateRequest request = new UserActivateRequest();
        request.setEmailValidatorCode("invalidCode");
        request.setUsername("validUser");

        when(validatorRepository.findEmailValidatorByEmailValidatorIdAndUsername(request.getEmailValidatorCode(), request.getUsername())).thenReturn(null);

        assertFalse(emailService.isActivateCodeMatched(request));

        verify(validatorRepository, times(0)).delete(any());
    }
}
