package com.lec.spring.controller;

import com.lec.spring.domain.Write;
import com.lec.spring.domain.WriteValidator;
import com.lec.spring.service.BoardService;
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





}