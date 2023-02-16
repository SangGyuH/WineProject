package com.lec.spring.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class NoticeValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return Notice.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Notice notice = (Notice)target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "notice_title", "글 제목은 필수입니다.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "notice_content", "글 내용은 필수입니다.");

    }
}
