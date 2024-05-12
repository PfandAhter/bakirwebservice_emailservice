package com.bakirwebservice.emailservice.rest.service;

import com.bakirwebservice.emailservice.api.request.ChangePwByCodeRequest;
import com.bakirwebservice.emailservice.api.request.EmailValidatorRequest;
import com.bakirwebservice.emailservice.api.request.ForgetPasswordRequest;
import com.bakirwebservice.emailservice.api.request.UserActivateRequest;
import com.bakirwebservice.emailservice.api.response.BaseResponse;
import com.bakirwebservice.emailservice.model.entity.EmailValidator;
import com.bakirwebservice.emailservice.rest.repository.EmailValidatorRepository;
import com.bakirwebservice.emailservice.rest.util.Util;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor

public class EmailServiceImpl {

    private final JavaMailSender javaMailSender;

    private final EmailValidatorRepository validatorRepository;

    @Value("${spring.mail.username}")
    private String sender;

    public BaseResponse emailValidator(EmailValidatorRequest request) throws MessagingException{
        String emailValidateCode = Util.generateCode();
        validatorRepository.save(EmailValidator.builder().username(request.getUsername()).emailValidatorId(emailValidateCode).build());
        sendEmailWithAttachment(request.getEmail() , validatorRepository.findUserValidateCodeByUsername(request.getUsername()).getEmailValidatorId());
        return new BaseResponse();
    }


    public BaseResponse sendEmailWithAttachment(String email , String activateCode) throws MessagingException{

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);


        try {
            mimeMessageHelper.setFrom(sender);

            FileSystemResource fileSystemResource = new FileSystemResource(new File("C:\\DEMOS\\bakirwebservice\\bwslogo.png"));
//            mimeMessageHelper.addAttachment(Objects.requireNonNull(fileSystemResource.getFilename()),fileSystemResource);

            mimeMessageHelper.setTo(email);
            String text = String.format("BakirWebService'e kayit oldugunuz icin tesekkurler ! .\n\n\nAktivasyon kodu : %s \n\n\n http://localhost:8080/user/activate ucuna username ile aktivasyon kodunu gonderiniz..." , activateCode);
            mimeMessageHelper.setText(text, false);
            mimeMessageHelper.setSubject("BakirWebService Activation User");
            mimeMessageHelper.addInline("bwslogo.png",fileSystemResource);

//            javaMailSender.send(mimeMessage);
            log.info("Mail sended to : " + email);
        } catch (MessagingException e) {
            log.error("Error while sending mail to : " + email);
        }

        return new BaseResponse();
    }

    public Boolean isActivateCodeMatched (UserActivateRequest request){
        if(validatorRepository.findEmailValidatorByEmailValidatorIdAndUsername(request.getEmailValidatorCode(),request.getUsername()) != null){
            validatorRepository.delete(validatorRepository.findEmailValidatorByEmailValidatorIdAndUsername(request.getEmailValidatorCode(), request.getUsername()));
            return true;
        }else{
            return false;
        }
    }

    public Boolean changePasswordCodeIsMatched (ChangePwByCodeRequest request){
        if(validatorRepository.findEmailValidatorByEmailValidatorIdAndUsername(request.getEmailCode(),request.getEmail()) != null){
            validatorRepository.delete(validatorRepository.findEmailValidatorByEmailValidatorIdAndUsername(request.getEmailCode(),request.getEmail()));
            return true;
        }else{
            return false;
        }
    }

    public BaseResponse createForgetPasswordCodeSendEmail (ForgetPasswordRequest request) throws MessagingException{
        EmailValidator emailValidator = new EmailValidator();
        emailValidator.setUsername(request.getEmail());
        validatorRepository.save(emailValidator);
        BaseResponse baseResponse = new BaseResponse();

        sendPasswordResetEmail(request.getEmail(),emailValidator.getEmailValidatorId());

        baseResponse.setErrorDescription("PASSWORD RESET CODE SENDED TO EMAIL");
        return baseResponse;
    }


    public BaseResponse sendPasswordResetEmail(String email , String activateCode) throws MessagingException{

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);


        try {
            mimeMessageHelper.setFrom(sender);

            FileSystemResource fileSystemResource = new FileSystemResource(new File("C:\\DEMOS\\bakirwebservice\\bwslogo.png"));
//            mimeMessageHelper.addAttachment(Objects.requireNonNull(fileSystemResource.getFilename()),fileSystemResource);

            mimeMessageHelper.setTo(email);
            String text = String.format("Merhabalar sifre sifirlama talebin uzerine mailine aktivasyon kodu gonderilmistir. Bu aktivasyon kodunu lutfen kimseyle paylasma" +
                    " .\n\n\nAktivasyon kodu : %s \n\n\n\n http://localhost:8080// ucuna username ile aktivasyon kodunu gonderiniz..." , activateCode);
            mimeMessageHelper.setText(text, false);
            mimeMessageHelper.setSubject("BakirWebService Activation User");
            mimeMessageHelper.addInline("bwslogo.png",fileSystemResource);

//            javaMailSender.send(mimeMessage);
            log.info("Mail sended to : " + email);
        } catch (MessagingException e) {
            log.error("Error while sending mail to : " + email);
        }

        return new BaseResponse();
    }
}
