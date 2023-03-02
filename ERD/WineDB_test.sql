-- 모든 테이블 확인
SHOW TABLES;

-- 테이블 개별 확인
SELECT * FROM tb_user;
SELECT * FROM tb_authority;
SELECT * FROM tb_buy;
SELECT * FROM tb_notice;
SELECT * FROM tb_notice_file;
SELECT * FROM tb_point;
SELECT * FROM tb_user_authorities;
SELECT * FROM tb_wine;
SELECT * FROM tb_wine_review;
SELECT * FROM tb_write;
SELECT * FROM tb_write_file;
SELECT * FROM tb_write_review;
            
-- 기존테이블 삭제
DELETE FROM tb_user;
ALTER TABLE tb_user AUTO_INCREMENT = 1;
DELETE FROM tb_authority;
ALTER TABLE tb_authority AUTO_INCREMENT = 1;
DELETE FROM tb_user_authorities;
ALTER TABLE tb_user_authorities AUTO_INCREMENT = 1;

DELETE FROM tb_point;

DELETE FROM tb_write;
ALTER TABLE tb_write AUTO_INCREMENT = 1;
DELETE FROM tb_write_review;
ALTER TABLE tb_write_review AUTO_INCREMENT = 1;
DELETE FROM tb_write_file;
ALTER TABLE tb_write_file AUTO_INCREMENT = 1;

DELETE FROM tb_notice;
ALTER TABLE tb_notice AUTO_INCREMENT = 1;
DELETE FROM tb_notice_file;
ALTER TABLE tb_notice_file AUTO_INCREMENT = 1;

DELETE FROM tb_wine;
ALTER TABLE tb_wine AUTO_INCREMENT = 1;
DELETE FROM tb_wine_review;
ALTER TABLE tb_wine_review AUTO_INCREMENT = 1;

DELETE FROM tb_buy;