package com.bakirwebservice.emailservice.rest.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Util {
    public static String generateCode(){
        return LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE) + UUID.randomUUID().toString().replace("-","");
    }
}

