package com.lec.spring.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        System.out.println("supports(" + clazz.getName() + ")");
        boolean result = User.class.isAssignableFrom(clazz);
        System.out.println(result);
        return result;
    }

    @Override
    public void validate(Object target, Errors errors) {
        System.out.println("### LOG: User Validator 검증 호출 ###");

        User user = (User)target;

        String username = user.getUser_id();
        if(username == null || username.trim().isEmpty()) {
            errors.rejectValue("user_id", "아이디 입력은 필수입니다");
        }

        // TODO 이메일, 주소, 연락처 들어가야함
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user_name", "이름 입력은 필수입니다");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user_pw", "패스워드 입력은 필수입니다");

        if(!user.getUser_pw().equals(user.getUser_repw())){
            errors.rejectValue("user_repw", "패스워드와 패스워드 확인 입력 값은 같아야 합니다.");
        }
    }
}
