package com.lec.spring.service;

import com.lec.spring.domain.User;
import com.lec.spring.domain.Write;
import com.lec.spring.domain.WriteFile;
import com.lec.spring.repository.FileRepository;
import com.lec.spring.repository.UserRepository;
import com.lec.spring.repository.WriteRepository;
import com.lec.spring.util.Util;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
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
public class BoardService {

    @Value("${app.upload.path}")
    private String uploadDir;

    private WriteRepository writeRepository;
    private UserRepository userRepository;

    private FileRepository fileRepository;

    @Autowired
    public BoardService(SqlSession sqlSession){  // MyBatis 가 생성한 SqlSession 빈(bean) 객체 주입
        writeRepository = sqlSession.getMapper(WriteRepository.class);
        userRepository = sqlSession.getMapper(UserRepository.class);
        fileRepository = sqlSession.getMapper(FileRepository.class);
        System.out.println("BoardService() 생성");
    }

    public int write(Write write
            , Map<String, MultipartFile> files // 첨부파일들.
    ){
        // 현재 로그인한 작성자 정보
       User user = Util.getLoggedUser();

        // 위 정보는 session 의 정보이고, 일단 DB 에서 다시 읽어온다
        user = userRepository.findById(user.getUser_uid());
        write.setUser(user);  // 글 작성자 세팅

        int cnt = writeRepository.save(write);

        // 첨부파일 추가
        addFiles(files, write.getWrite_id());

        return cnt;
    }

    // 특정 글(id) 첨부파일 추가
    private void addFiles(Map<String, MultipartFile> files, Long write_id){
        if(files != null){
            for(Map.Entry<String, MultipartFile> e :files.entrySet()){

                // name= "upfile##" 인 경우만 등록.(다른 웹에디터와 섞이지 않도록..)
                if(!e.getKey().startsWith("upfile")) continue;

                // 첨부파일 정보 출력
                System.out.println("\n첨부파일 정보: " + e.getKey());     // name 값
                Util.printFileInfo(e.getValue());
                System.out.println();

                // 물리적인 파일 저장
                WriteFile file = upload(e.getValue());

                // 성공하면 DB 에도 저장
                //////////////////////////////////////////////////////////////////////////
                Write write = writeRepository.findById(write_id);
                if(file != null){
                    file.setWrite(write);   // FK 설정
                    fileRepository.save(file); // INSERT
                }
            }
        }
    }


    // 물리적으로 파일 저장. 중복된 이름 rename 처리
    private WriteFile upload(MultipartFile multipartFile){
        WriteFile attachment = null;

        // 담긴 파일이 없으면 pass~
        String originalFilename = multipartFile.getOriginalFilename();
        if(originalFilename == null || originalFilename.length() == 0) return null;

        // 원본 파일 명
        //                  org.springframework.util.StringUtils
        String sourceName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        // 저장될 파일 명
        String fileName = sourceName;

        // 파일이 중복되는지 확인.
        File file = new File(uploadDir + File.separator + sourceName);
        if(file.exists()){      // 이미 존재하는 파일명, 중복된다면 다름 이름은 변경하여 파일 저장


        }

        int pos = fileName.lastIndexOf(".");
        if(pos > -1) { // 확장자가 있는 경우
            String name = fileName.substring(0, pos);  // 파일'이름'
            String ext = fileName.substring(pos + 1);  // 파일'확장명'

            // 중복방지를 위한 새로운 이름 (현재시간 ms) 를 파일명에 추가
            fileName = name + "_" + System.currentTimeMillis() + "." + ext;
        } else {
            fileName += "_" + System.currentTimeMillis();
        }

        // 저장할 파일명

        System.out.println("fileName:" + fileName);

        Path copyOfLocation = Paths.get(new File(uploadDir + File.separator + fileName).getAbsolutePath());
        System.out.println(copyOfLocation);

        try {
            // inputStream을 가져와서
            // copyOfLocation (저장위치)로 파일을 쓴다.
            // copy의 옵션은 기존에 존재하면 REPLACE(대체한다), 오버라이딩 한다

            // java.nio.file.Files
            Files.copy(
                    multipartFile.getInputStream(),
                    copyOfLocation,
                    StandardCopyOption.REPLACE_EXISTING   // 기존에 존재하면 덮어쓰기
            );
        } catch (IOException e) {
            e.printStackTrace();
            // 예외처리는 여기서.
            //throw new FileStorageException("Could not store file : " + multipartFile.getOriginalFilename());
        }

        attachment = WriteFile.builder()
                .file(fileName)     // 저장된 이름
                .source(sourceName)    // 원본 이름
                .build();


        return attachment;

    }

    // [이미지 파일 여부 세팅]
    private void setImage(List<WriteFile> fileList) {
        // upload 실제 물리적인 경로
//    String realPath = context.getRealPath(uploadDir);
        String realPath = new File(uploadDir).getAbsolutePath();

        for(WriteFile writeFile : fileList) {
            BufferedImage imgData = null;
            File f = new File(realPath, writeFile.getFile());  // 첨부파일에 대한 File 객체

            try {
                imgData = ImageIO.read(f);
                // ※ ↑ 파일이 존재 하지 않으면 IOExcepion 발생한다
                //   ↑ 이미지가 아닌 경우는 null 리턴
            } catch (IOException e) {
                System.out.println("파일존재안함: " + f.getAbsolutePath() + " [" + e.getMessage() + "]");
            }

            if(imgData != null) writeFile.setImage(true); // 이미지 여부 체크
        } // end for
    }


    public List<Write> list(){
        return writeRepository.findAll();
    }

    // 특정 id 의 글 읽어오기
    // 조회수 증가 없음
    public List<Write> selectById(long write_id) {
        List<Write> list = new ArrayList<>();

        Write write = writeRepository.findById(write_id);

        if(write != null){
            List<WriteFile> fileList = fileRepository.findByWrite(write.getWrite_id());
            setImage(fileList);     // 이미지 파일 여부 세팅
//            write.setFiles(fileList);
            write.setFileList(fileList);

            list.add(write);
        }

        return list;
    }



    // 페이징 리스트
    public List<Write> list(Integer page, Model model){

        Integer writePages1 = 10;  // 기본 write_pages 값. 한 [페이징] 당 몇개의 페이지가 표시되나
        Integer pageRows1 = 10;    // 기본 page_rows 값.  한 '페이지'에 몇개의 글을 리스트 할것인가?

        // 현재 페이지 parameter
        if(page == null) page = 1; // 디폴트는 1page
        if(page < 1) page = 1;

        // 페이징
        // writePages: 한 [페이징] 당 몇개의 페이지가 표시되나
        // pageRows: 한 '페이지'에 몇개의 글을 리스트 할것인가?
        HttpSession session = Util.getSession();
        Integer writePages = (Integer)session.getAttribute("writePages");
        if(writePages == null) writePages = writePages1; //WRITE_PAGES;      // 만약 session 에 없으면 기본값으로 동작
        Integer pageRows = (Integer)session.getAttribute("pageRows");
        if(pageRows == null) pageRows = pageRows1; // PAGE_ROWS;   // session 에 없으면 기본값으로
        session.setAttribute("page", page);     // 현재 페이지 번호 -> session 에 저장

        long cnt = writeRepository.countAll();      // 글 목록 전체의 개수
        int totalPage = (int)Math.ceil(cnt / (double)pageRows);  //총 몇 '페이지' 분량인가?

        // page 값 보정
        if(page > totalPage) page = totalPage;

        // 몇번째 데이터부터 fromRow
        int fromRow = (page - 1) * pageRows;

        // [페이징] 에 표시할 '시작페이지' 와 '마지막페이지' 계산
        int startPage = ((int)((page - 1) / writePages) * writePages) + 1;
        int endPage = startPage + writePages - 1;
        if (endPage >= totalPage) endPage = totalPage;

        model.addAttribute("cnt", cnt);  // 전체 글 개수
        model.addAttribute("page", page); // 현재 페이지
        model.addAttribute("totalPage", totalPage);  // 총 '페이지' 수
        model.addAttribute("pageRows", pageRows);  // 한 '페이지' 에 표시할 글 개수

        // [페이징]
        model.addAttribute("url", Util.getRequest().getRequestURI());  // 목록 url
        model.addAttribute("writePages", writePages); // [페이징] 에 표시할 숫자 개수
        model.addAttribute("startPage", startPage);  // [페이징] 에 표시할 시작 페이지
        model.addAttribute("endPage", endPage);   // [페이징] 에 표시할 마지막 페이지

        // 해당 페이지의 글 목록 읽어오기
        List<Write> list = writeRepository.selectFromRow(fromRow, pageRows);
        model.addAttribute("list", list);

        return list;
    }



    public int update(Write write
            , Map<String, MultipartFile> files // 새로 추가된 첨부파일
            , Long[] delfile // 삭제될 첨부파일
    ){
        int result = 0;

        result = writeRepository.update(write);

        // 첨부파일 추가
        addFiles(files, write.getWrite_id());

        // 삭제할 첨부파일들은 삭제하기
        if(delfile != null){
            for(Long fileId : delfile){
                WriteFile file = fileRepository.findById(fileId);
                if(file != null){
                    delFile(file);      // 물리적으로 삭제
                    fileRepository.delete(file);    // DB 에서 삭제
                }
            }
        }

        return result;
    }

    public int deleteById(long write_id){
        int result = 0;

        Write write = writeRepository.findById(write_id);
        if(write != null) {
            // 물리적으로 저장된 첨부파일(들) 삭제
            List<WriteFile> fileList = fileRepository.findByWrite(write_id);
            if(fileList != null && fileList.size() > 0) {
                for(WriteFile file : fileList) {
                    delFile(file);
                }
            }

            // 글삭제 (참조하는 첨부파일, 댓글 등도 같이 삭제 될 것이다 on delete cascade)
            result = writeRepository.delete(write);
        }

        return result;
    }

    // 특정 첨부파일(id) 를 물리적으로 삭제
    private void delFile(WriteFile file) {
//    String saveDirectory = context.getRealPath(uploadDir);
        String saveDirectory = new File(uploadDir).getAbsolutePath();

        File f = new File(saveDirectory, file.getFile()); // 물리적으로 저장된 파일들이 삭제 대상
        System.out.println("삭제시도--> " + f.getAbsolutePath());

        if (f.exists()) {
            if (f.delete()) { // 삭제!
                System.out.println("삭제 성공");
            } else {
                System.out.println("삭제 실패");
            }
        } else {
            System.out.println("파일이 존재하지 않습니다.");
        } // end if
    }

}
