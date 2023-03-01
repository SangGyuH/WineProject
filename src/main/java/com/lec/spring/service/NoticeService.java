package com.lec.spring.service;

import com.lec.spring.domain.Notice;
import com.lec.spring.domain.NoticeFile;
import com.lec.spring.domain.User;
import com.lec.spring.repository.NoticeFileRepository;
import com.lec.spring.repository.NoticeRepository;
import com.lec.spring.repository.UserRepository;
import com.lec.spring.util.Util;

import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class NoticeService {

    public static final Integer WRITE_PAGES = 10;  // 기본 write_pages 값. 한 [페이징] 당 몇개의 페이지가 표시되나
    public static final Integer PAGE_ROWS = 10;    // 기본 page_rows 값.  한 '페이지'에 몇개의 글을 리스트 할것인가?


    @Value("${app.upload.path}")
    private String uploadDir;

    private NoticeRepository noticeRepository;
    private UserRepository userRepository;
    private NoticeFileRepository noticeFileRepository;

    private NoticeService(SqlSession sqlSession){
        noticeRepository = sqlSession.getMapper(NoticeRepository.class);
        userRepository = sqlSession.getMapper(UserRepository.class);
        noticeFileRepository = sqlSession.getMapper(NoticeFileRepository.class);
    }

    public int write(Notice notice, Map<String, MultipartFile> files) {
        User user = Util.getLoggedUser();       //현재 로그인한 사용자
        user = userRepository.findById(user.getUser_uid());      //현재 로그인한 사용자 전체 정보 추출
        notice.setUser(user);       //사용자 설정
        int result = noticeRepository.save(notice);
        addFiles(files, notice.getNotice_id());
        return result;
    }

    private void addFiles(Map<String, MultipartFile> files, Long notice_id){
        if(files!=null){
            for(Map.Entry<String, MultipartFile> e : files.entrySet()){
                if(!e.getKey().startsWith("upfile")) continue;
                Util.printFileInfo(e.getValue());
                NoticeFile file = upload(e.getValue());
                if(file != null){
                    file.setNotice_id(notice_id);
                    noticeFileRepository.save(file);
                }
            }
        }
    }

    private NoticeFile upload(MultipartFile multipartFile){
        NoticeFile attachment = null;

        //담긴 파일 없을 경우
        String originalFilename = multipartFile.getOriginalFilename();
        if(originalFilename == null || originalFilename.length() == 0) return null;

        //원본파일명
        String sourceName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        //저장될 파일명
        String fileName = sourceName;

        //파일 중복확인
        File file = new File(uploadDir + File.separator + sourceName);
        if(file.exists()){
            int pos = fileName.lastIndexOf(".");
            if(pos > -1){
                String name = fileName.substring(0, pos);
                String ext = fileName.substring(pos+1);
                fileName = name + "_" + System.currentTimeMillis() + "." + ext;
            } else {
                fileName += "_" + System.currentTimeMillis();
            }
        }

        Path copyOfLocation = Paths.get(new File(uploadDir + File.separator + fileName).getAbsolutePath());
        try{
            Files.copy(multipartFile.getInputStream(), copyOfLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        attachment = NoticeFile.builder().file(fileName).source(sourceName).build();
        return attachment;
    }

    public List<Notice> detail(long notice_id) {

        List<Notice> list = new ArrayList<>();
        Notice notice = noticeRepository.findById(notice_id);

        if(notice != null){
            List<NoticeFile> fileList = noticeFileRepository.findByNoticeId(notice.getNotice_id());
            setImage(fileList);
            notice.setFileList(fileList);
            list.add(notice);
        }
        return list;
    }

    private void setImage(List<NoticeFile> fileList){
        String realPath = new File(uploadDir).getAbsolutePath();

        for(NoticeFile fileDto : fileList) {
            BufferedImage imgData = null;
            File f = new File(realPath, fileDto.getFile());  // 첨부파일에 대한 File 객체
            try {
                imgData = ImageIO.read(f);
                // ※ ↑ 파일이 존재 하지 않으면 IOExcepion 발생한다
                //   ↑ 이미지가 아닌 경우는 null 리턴
            } catch (IOException e) {
                System.out.println("파일존재안함: " + f.getAbsolutePath() + " [" + e.getMessage() + "]");
            }

            if (imgData != null) fileDto.setImage(true); // 이미지 여부 체크
        }
    }

    public List<Notice> list(){
        return noticeRepository.findAll();
    }

    public List<Notice> list(Integer page, Model model){
        if(page==null || page < 1) page = 1; //default = 1page

        HttpSession session = Util.getSession();
        Integer noticePages = (Integer)session.getAttribute("writePages");
        if(noticePages == null) noticePages = WRITE_PAGES;
        Integer pageRows = (Integer)session.getAttribute("pageRows");
        if(pageRows==null)pageRows = PAGE_ROWS;
        session.setAttribute("page", page);

        long cnt = noticeRepository.countAll();
        int totalPage = (int)Math.ceil(cnt/ (double)pageRows);

        if(page > totalPage) page = totalPage;
        int fromRow = (page-1)*pageRows;
        int startPage = ((int)((page-1)/noticePages) *noticePages) + 1;
        int endPage = startPage+noticePages - 1;
        if(endPage >=totalPage) endPage = totalPage;

        model.addAttribute("cnt", cnt);  // 전체 글 개수
        model.addAttribute("page", page); // 현재 페이지
        model.addAttribute("totalPage", totalPage);  // 총 '페이지' 수
        model.addAttribute("pageRows", pageRows);  // 한 '페이지' 에 표시할 글 개수

        // [페이징]
        model.addAttribute("url", Util.getRequest().getRequestURI());  // 목록 url
        model.addAttribute("writePages", noticePages); // [페이징] 에 표시할 숫자 개수
        model.addAttribute("startPage", startPage);  // [페이징] 에 표시할 시작 페이지
        model.addAttribute("endPage", endPage);   // [페이징] 에 표시할 마지막 페이지

        List<Notice> list = noticeRepository.selectFromRow(fromRow, pageRows);
        model.addAttribute("list", list);

        return list;
    }

    public int update(Notice notice, Map<String, MultipartFile> files, Long[] delfile){
        int result = 0;
        result = noticeRepository.update(notice);;
        addFiles(files, notice.getNotice_id());
        if(delfile != null){
            for(Long fileId : delfile){
                NoticeFile file = noticeFileRepository.findById(fileId);
                if(file != null){
                    delFile(file);
                    noticeFileRepository.delete(file);
                }
            }
        }
        return result;
    }

    public int deleteById(long notice_id){
        int result = 0;
        Notice notice = noticeRepository.findById(notice_id);
        if(notice != null){
            List<NoticeFile> fileList = noticeFileRepository.findByNoticeId(notice_id);
            if(fileList != null && fileList.size() > 0) {
                for(NoticeFile file : fileList) delFile(file);
            }
            result = noticeRepository.delete(notice);
        }
        return result;
    }

    private void delFile(NoticeFile file){
        String saveDirectory = new File(uploadDir).getAbsolutePath();

        File f = new File(saveDirectory, file.getFile());
        if(f.exists()){
            if(f.delete()) System.out.println("### LOG : 파일 삭제 성공 ###");
            else System.out.println("### LOG : 파일 삭제 실패 ###");
        } else {
            System.out.println("### ERROR : 파일이 존재하지 않습니다 ###");
        }
    }

}
