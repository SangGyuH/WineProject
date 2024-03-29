package com.lec.spring.controller;

import com.lec.spring.domain.Write;
import com.lec.spring.domain.WriteValidator;
import com.lec.spring.service.BoardService;
import com.lec.spring.util.Util;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

// Controller layer
//  request 처리 ~ response
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    public BoardController() {
        System.out.println("BoardController() 생성");
    }

    @GetMapping("/write")
    public void write() {
    }

    @PostMapping("/write")
    public String writeOk(
            @RequestParam Map<String, MultipartFile> files      // 첨부파일들
            , @ModelAttribute("dto") Write write
            , Model model
    ) {
        model.addAttribute("result", boardService.write(write, files));
        return "board/writeOk";
    }

    @GetMapping("/detail")
    public void detail(long write_id, Model model){
        model.addAttribute("list", boardService.selectById(write_id));
        model.addAttribute("conPath", Util.getRequest().getContextPath());
    }

    // 페이지 사용
    @GetMapping("/list")
    public void list(Integer page, Model model){
        boardService.list(page, model);
        //public void list(Model model){
        //      model.addAttribute("list", boardService.list());
    }

    @GetMapping("/update")
    public void update(long write_id, Model model){
        model.addAttribute("list", boardService.selectById(write_id));
    }

    @PostMapping("/update")
    public String updateOk(
            @ModelAttribute("dto") Write write
            , @RequestParam Map<String, MultipartFile> files        // 새로 추가될 첨부파일들
            , Long[] delfile    // 삭제될 파일들
            , Model model){
        model.addAttribute("result", boardService.update(write, files, delfile));
        return "board/updateOk";
    }

    @PostMapping("/delete")
    public String deleteOk(long write_id, Model model){
        model.addAttribute("result", boardService.deleteById(write_id));
        return "board/deleteOk";
    }

    // 이 컨트롤러 클래스의 handler 에서 폼 데이터를 바인딩 할때 검증하는 Validator 객체 지정
    @InitBinder
    public void initBinder(WebDataBinder binder){
        System.out.println("initBinder() 호출");
        binder.setValidator(new WriteValidator());
    }

    // 페이징
    // pageRows 변경시 동작
    @PostMapping("/pageRows")
    public String pageRows(Integer page, Integer pageRows){
        Util.getSession().setAttribute("pageRows", pageRows);
        return "redirect:/board/list?page=" + page;

    }




}