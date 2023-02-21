package com.lec.spring.service;

import com.lec.spring.domain.WriteFile;
import com.lec.spring.repository.FileRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {

    private FileRepository fileRepository;

    @Autowired
    public FileService(SqlSession sqlSession){
        fileRepository = sqlSession.getMapper(FileRepository.class);
        System.out.println("FileService() 생성");
    }

    public WriteFile findById(Long write_file_id) {return fileRepository.findById(write_file_id);}

}
