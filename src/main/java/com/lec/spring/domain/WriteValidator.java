package com.lec.spring.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class WriteValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Write.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        System.out.println("### LOG: Write Validator 검증 호출 ###");

        Write write = (Write) target;

        //TODO
    }
}
