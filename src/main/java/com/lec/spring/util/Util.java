package com.lec.spring.util;

import com.lec.spring.config.PrincipalDetails;
import com.lec.spring.domain.User;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Util {
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attrs.getRequest();      //현재 request
    }

    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    public static User getLoggedUser(){
        PrincipalDetails userDetails = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
        return user;  //현재 로그인 한 사용자
    }



    // 첨부파일 정보 출력하기
    public static void printFileInfo(MultipartFile file){
        String originalFileName = file.getOriginalFilename();       // 원본 파일명

        if(originalFileName == null || originalFileName.length() == 0){
            //System.out.println("### ERROR: Util.java 파일 에서 에러발생! ###\n### ERROR 원인: 파일이 없습니다. ###");
            return;
        }
        System.out.println("----------------------------");         //파일 정보 log 출력
        System.out.println("### LOG: 파일 정보를 출력합니다 ###");
        System.out.println("\tOriginal File Name : " + originalFileName);
        System.out.println("\tCleanPath : " + StringUtils.cleanPath(originalFileName));
        System.out.println("\tFile Size : " + file.getSize() + " bytes");
        System.out.println("\tMIME: " + file.getContentType());
        System.out.println("----------------------------");

        // 이미지 파일 여부
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file.getInputStream());
            if(bufferedImage != null){
                System.out.printf("### LOG: 이미지 파일입니다: %d x %d ###\n", bufferedImage.getWidth(), bufferedImage.getHeight());
            }
        } catch (IOException e) {
            System.out.println("### LOG: 이미지 파일이 아닙니다. ###");
        }

    }
}
