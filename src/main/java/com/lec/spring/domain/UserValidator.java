package com.lec.spring.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

// Validator 는 Controller 에 등록하여 동작하게 해야함(UserController 최하단에 initBinder 로 등록함)
public class UserValidator implements Validator {
    // 유효성 가능여부 우선 확인 후 아래의 validate 실행
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

//        String username = user.getUser_id();
//        if(username == null || username.trim().isEmpty()) {
//            errors.rejectValue("user_id", "아이디 입력은 필수입니다");
//        }

        String regexId;
        regexId = "^[a-zA-Z0-9]{5,20}$";
        String sno = user.getUser_id();
        if(!Pattern.matches(regexId, sno)){
            errors.rejectValue("user_id", "아이디는 영어와 숫자만 사용하여 5~20자리여야 합니다");
        }

        String regexName;
        regexName = "^[가-힣]*$";
        String snoc = user.getUser_name();
        if(!Pattern.matches(regexName, snoc)){
            errors.rejectValue("user_name", "이름은 한글만 허용합니다");
        }

        String regexPw;
        regexPw = "^[0-9]{5,20}$";
        String snoa = user.getUser_pw();
        if(!Pattern.matches(regexPw, snoa)){
            errors.rejectValue("user_pw", "패스워드는 숫자만 사용하여 5~20자리여야 합니다");
        }

        String regexPhone;
        regexPhone = "^\\d{3}-\\d{3,4}-\\d{4}$";
        String snob = user.getUser_phone();
        if(!Pattern.matches(regexPhone, snob)){
            errors.rejectValue("user_phone", "연락처는 '-' 포함, 숫자 '000-0000-0000' 형식을 허용합니다");
        }

        String regexEmail;
        regexEmail = "[0-9a-zA-Z]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        String snod = user.getUser_email();
        if(!Pattern.matches(regexEmail, snod)){
            errors.rejectValue("user_email", "이메일은 아이디@도메인 형식을 허용합니다");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "user_addr3", "주소 입력은 필수입니다");

        if(!user.getUser_pw().equals(user.getUser_repw())){
            errors.rejectValue("user_repw", "패스워드와 패스워드 확인 입력 값은 같아야 합니다.");
        }
    }
}
