package com.lec.spring.controller;

import com.lec.spring.config.PrincipalDetails;
import com.lec.spring.domain.QryResult;
import com.lec.spring.service.WineService;
import com.lec.spring.util.Util;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.kerberos.KerberosKey;

@Controller
@RequestMapping("/wine")
public class WineController {

    @Autowired
    private WineService wineService;

    @GetMapping("/search")
    public String search(Model model){
        System.out.println(Util.getRequest().getContextPath());
        model.addAttribute("conPath", Util.getRequest().getContextPath());
        return "wine/wineSearch";
    }

    @PostMapping("/detail")
    public String detail(
            HttpServletRequest request,
            @RequestParam("serialkey") Integer wine_serialkey,
            @RequestParam("type") String wine_type,
            @RequestParam("img") String wine_img,
            @RequestParam("name") String wine_name,
            @RequestParam("location") String wine_location,
            Model model){
        model.addAttribute("user_uid", (Long) request.getSession().getAttribute("user_uid"));
        model.addAttribute("conPath", Util.getRequest().getContextPath());
        model.addAttribute("dto", wineService.wineDetail(wine_type, wine_serialkey, wine_img, wine_name, wine_location));   //와인 정보
        model.addAttribute("commentList", wineService.wineReviews(wine_type, wine_serialkey));   //와인 리뷰
        return "wine/wineDetail";
    }


    @PostMapping("/stockCheck")
    @ResponseBody
    public QryResult stockCheck(HttpServletRequest request,
                                @RequestParam("serialkey") Integer wine_serialkey,
                                @RequestParam("type") String wine_type,
                                @RequestParam("quantity") Integer quantity
    ){
        Long user_uid = (Long) request.getSession().getAttribute("user_uid");
        int count = wineService.stockCheck(wine_type, wine_serialkey);
//        System.out.println("\t*** user_uid: " + user_uid);

        return  (count >= quantity)?
                        QryResult.builder().status("OK").count(count).build() :
                        QryResult.builder().status("SHORTAGE").count(count).build();
    }
    @PostMapping("/buy")
    @ResponseBody
    public QryResult buy(@RequestParam("wine_id") Integer wine_id,
                         @RequestParam("quantity") Integer quantity
    ){
//        if(wineService.wineBuy(wine_id, quantity) > 0)
        return QryResult.builder().count(wineService.wineBuy(wine_id, quantity)).status("OK").build();
    }

    @GetMapping("/list")
    public String list(String type,Model model){
        model.addAttribute("wine_type", type);
        return "wine/list";
    }

    @GetMapping("/test")
    public void test(){}
//    @PostMapping("/test")
//    public String test2(){
//        return "/wine/test";
//    }


}
