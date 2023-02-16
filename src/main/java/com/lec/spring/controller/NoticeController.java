package com.lec.spring.controller;

import com.lec.spring.domain.Notice;
import com.lec.spring.domain.NoticeValidator;
import com.lec.spring.service.NoticeService;
import com.lec.spring.service.UserService;
import com.lec.spring.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;


    public NoticeController(){
        System.out.println("### LOG : NoticeController() 생성 ###");
    }

    @GetMapping("/noticeWrite")
    public void write(){}

    @PostMapping("/noticeWrite")
    public String noticeWriteDone(@RequestParam Map<String, MultipartFile> files, @ModelAttribute("noticeVO") Notice notice, Model model){
        model.addAttribute("result", noticeService.write(notice,files));
        return "notice/noticeWriteDone";
    }


    @GetMapping("/noticeDetail")
    public void noticeDetail(long notice_id, Model model){
        model.addAttribute("list", noticeService.detail(notice_id));
        model.addAttribute("conPath", Util.getRequest().getContextPath());
    }

    @GetMapping("/noticeList")
    public void noticeList(Integer page, Model model){
        noticeService.list(page, model);
    }

    @GetMapping("/noticeUpdate")
    public void noticeUpdate(long notice_id, Model model){
        model.addAttribute("list", noticeService.detail(notice_id));
    }

    @PostMapping("/noticeUpdate")
    public String noticeUpdateDone(@ModelAttribute("notice")Notice notice, @RequestParam Map<String, MultipartFile> files, Long[] delfiles, Model model){
        model.addAttribute("result", noticeService.update(notice, files, delfiles));
        return "notice/noticeUpdateDone";
    }

    @PostMapping("/noticeDelete")
    public String noticeDeleteDone(long notice_id, Model model){
        model.addAttribute("result", noticeService.deleteById(notice_id));
        return "notice/noticeDeleteDone";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder){
        binder.setValidator(new NoticeValidator());
    }

    @PostMapping("/pageRows")
    public String pageRows(Integer page, Integer pageRows){
        Util.getSession().setAttribute("pageRows", pageRows);
        return "redirect:notice/noticeList?page="+page;
    }

}
