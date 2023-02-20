package com.lec.spring.controller;

import com.lec.spring.domain.User;
import com.lec.spring.domain.UserValidator;
import com.lec.spring.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserController() {
        System.out.println(getClass().getName() + "() 생성");
    }

    @GetMapping("/login")
    public void login(){

    }

    // onAuthenticationFailure 에서 로그인 실패시 forwarding 용
    // ↓ 애시당초 post 로 request 가 진행된 것은 forwording도 post로
    @PostMapping("/loginError")
    public String loginError(){
        return "user/login";
    }

    @RequestMapping("/rejectAuth")
    public String rejectAuth(){
        return "common/rejectAuth";
    }

    @GetMapping("/register")
    public void register(){}

    @PostMapping("/register")
    public String registerOk(@Valid User user   // 해당 시점에 바인딩됨
            , BindingResult result              // UserValidator 가 유효성 검증한 결과가 담기는 객체
            , Model model
            , RedirectAttributes redirectAttrs
    ){

        // 이미 등록된 아이디가 들어오면 에러발생
        if(!result.hasFieldErrors("user_id") && userService.isExist(user.getUser_id())){
            result.rejectValue("user_id", "이미 존재하는 아이디 입니다.");
        }

        // 검증 에러가 있었다면 redirect 한다
        if(result.hasErrors()){
            redirectAttrs.addFlashAttribute("user_id", user.getUser_id());
            redirectAttrs.addFlashAttribute("user_name", user.getUser_name());

            List<FieldError> errList = result.getFieldErrors();
            for(FieldError err : errList) {
                redirectAttrs.addFlashAttribute("error", err.getCode());  // 가장 처음에 발견된 에러를 담아서 보낸다
                break;
            }

            return "redirect:/user/register";
        }

        String page = "/user/registerOk";
        int cnt = userService.register(user);
        model.addAttribute("result", cnt);
        return page;
    }

    // UserValidator 를 바인딩 검증하는 용도로 사용 등록
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setValidator(new UserValidator());
    }

}














