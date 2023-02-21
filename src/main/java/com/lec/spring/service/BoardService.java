package com.lec.spring.service;

import com.lec.spring.domain.User;
import com.lec.spring.domain.Write;
import com.lec.spring.repository.FileRepository;
import com.lec.spring.repository.WriteRepository;
import com.lec.spring.util.Util;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class BoardService {

    @Value("${app.upload.path}")
    private String uploadDir;

    private WriteRepository writeRepository;
    //private UserRepository userRepository;

    private FileRepository fileRepository;

    @Autowired
    public BoardService(SqlSession sqlSession){  // MyBatis 가 생성한 SqlSession 빈(bean) 객체 주입
        writeRepository = sqlSession.getMapper(WriteRepository.class);
        //userRepository = sqlSession.getMapper(UserRepository.class);
        fileRepository = sqlSession.getMapper(FileRepository.class);
        System.out.println("BoardService() 생성");
    }

    public int write(Write write
            , Map<String, MultipartFile> files // 첨부파일들.
    ){
        // 현재 로그인한 작성자 정보
//       User user = Util.getLoggedUser();

        // 위 정보는 session 의 정보이고, 일단 DB 에서 다시 읽어온다
 //       user = userRepository.findById(user.getUser_uid());
//        write.setUser(user);  // 글 작성자 세팅

        int cnt = writeRepository.save(write);

        // 첨부파일 추가
       // addFiles(files, write.getWrite_id());

        return cnt;
    }



}
