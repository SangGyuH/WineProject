package com.lec.spring.controller;

import com.lec.spring.domain.User;
import com.lec.spring.domain.UserValidator;
import com.lec.spring.domain.Wine;
import com.lec.spring.service.UserService;
import com.lec.spring.service.PurchaseService;
import com.lec.spring.util.Util;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    private PurchaseService purchaseService;

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
            , BindingResult result              // UserValidator 가 유효성 검증한 결과가 담기는 객체(UserValidator 의 Errors errors)
            , Model model                       // (규칙)매개변수 선언 시 BindingResult 보다 Model 을 뒤에 두어야한다.
            , RedirectAttributes redirectAttrs  // 에러가 발생하면 redirect 할 것이고, 이에따라 redirect: 시 넘겨줄 값들
    ){

        // validation(검증) 에러가 있었다면 redirect 할 것임
        // 이미 등록된 아이디가 들어오면 에러발생
        if(!result.hasFieldErrors("user_id") && userService.isExist(user.getUser_id())){
            result.rejectValue("user_id", "이미 존재하는 아이디 입니다.");
        }

        // 검증 에러가 있었다면 redirect 한다
        if(result.hasErrors()){
            redirectAttrs.addFlashAttribute("user_id", user.getUser_id());
            redirectAttrs.addFlashAttribute("user_name", user.getUser_name());
            redirectAttrs.addFlashAttribute("user_phone", user.getUser_phone());
            redirectAttrs.addFlashAttribute("user_email", user.getUser_email());

//            redirectAttrs.addFlashAttribute("errors", result);

            List <FieldError> errList = result.getFieldErrors();
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

    @GetMapping("/mypage")
    public void mypage(HttpServletRequest request
            , Model model
    ){
//        User user = Util.getLoggedUser();
        Long user_uid = (Long) request.getSession().getAttribute("user_uid");
        User user = userService.findById(user_uid);
        System.out.println("####################### user_uid ##" + user_uid);
        model.addAttribute("user", user);
        model.addAttribute("authorities", user);
        System.out.println("##### user:" + user);
    }

    @GetMapping("/changeInfo")
    public void changeInfo(Model model){
        User user = Util.getLoggedUser();
        User user2 = userService.findByUsername(user.getUser_id());
        model.addAttribute("user", user2);
    }
    @PostMapping("/changeInfo")
    public String changeInfoOk(@Valid @ModelAttribute("dto") User user
            , BindingResult bindingResult
            , Model model
            , RedirectAttributes redirectAttrs
    ){
        if(bindingResult.hasErrors()){
            List<FieldError> errList = bindingResult.getFieldErrors();
            for(FieldError err : errList){
                //addFlashAttribute 는 post 방식으로 redirect 발생 <-> redirectAttrs.addAttribute 는 Get 방식으로 갈때 사용(RequestParam에서 다루는 내용)
                redirectAttrs.addFlashAttribute("error", err.getCode());
                break;
            }
            return "redirect:/user/changeInfo";
        }
        model.addAttribute("result", userService.update(user));
        model.addAttribute("dto", user);

        return "user/changeInfoOk";
    }

    @RequestMapping("/purchaseList")
    public void purchaseList(Model model){
        User user = Util.getLoggedUser();
        List<Wine> wineList = purchaseService.getWineListByUseruid(user.getUser_uid());
        model.addAttribute("user", user);
        model.addAttribute("winelist", wineList);
    }

    @GetMapping("/unregister")
    public void unregister(Model model){
        User user = Util.getLoggedUser();
        model.addAttribute("user", user);
    }

    @PostMapping("/unregister")
    public String unregisterOk(@RequestParam String id, @RequestParam String password, Model model){
        User user = userService.findByUsername(id);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isMatch = passwordEncoder.matches(password, user.getUser_pw());
        if(isMatch) {
            int result = userService.deleteById(id); // 삭제. result : 삭제한 갯수
            model.addAttribute("result", result);
        }
        else
            model.addAttribute("result", 0);
        Util.getSession().invalidate();
        return "user/unregisterOk";
    }

//     UserValidator 를 바인딩 검증하는 용도로 사용 등록
    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setValidator(new UserValidator());
    }

}














