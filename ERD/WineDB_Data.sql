-- 권한 추가
ALTER TABLE tb_user AUTO_INCREMENT = 1;

INSERT INTO tb_authority (authority) VALUES
    ('ROLE_ADMIN'), ('ROLE_SILVER'), ('ROLE_GOLD'), ('ROLE_DIAMOND')
;
   
-- admin 사용자 (id: admin, pw: 1234)
INSERT INTO tb_user (user_id, user_pw, user_name, user_email, user_phone, user_addr1, user_addr2, user_addr3) 
VALUES 
	('admin', '$2a$10$tlrJg7LySHU.2JdNUZ/aX.mp1435Xa9lxqOW2iy6npTqP1sEoZUXK','관리자', 'admin@gmail.com','01012345678', '서울특별시 강남구 테헤란로 146', '현익빌딩 3층, 4층', '06236')
;

-- 샘플 사용자
INSERT INTO tb_user (user_id, user_pw, user_name, user_email, user_phone, user_addr1, user_addr2, user_addr3)
VALUES
    ('apple', '1111', '김사과', 'apple@gmail.com', '01011111111', '서울특별시 강남구 테헤란로 146', '현익빌딩 3층, 4층', '06236'),
    ('banana', '2222', '반하나', 'banana@gmail.com', '01022222222', '서울특별시 강남구 테헤란로 146', '현익빌딩 3층, 4층', '06236'),
    ('orange', '3333', '오렌지', 'orange@gmail.com', '01033333333', '서울특별시 강남구 테헤란로 146', '현익빌딩 3층, 4층', '06236'),
    ('watermelon', '4444', '박수박', 'watermelon@gmail.com', '01044444444', '서울특별시 강남구 테헤란로 146', '현익빌딩 3층, 4층', '06236'),
    ('cherry', '5555', '이체리', 'cherry@gmail.com', '01055555555', '서울특별시 강남구 테헤란로 146', '현익빌딩 3층, 4층', '06236'),
    ('melon', '6666', '정멜론', 'melon@gmail.com', '01066666666', '서울특별시 강남구 테헤란로 146', '현익빌딩 3층, 4층', '06236')
    ;

-- 사용자 권한 추가
INSERT INTO tb_user_authorities
VALUES
	(1, 1),
    (4, 1),
    (2, 2),
    (2, 3),
    (3, 4),
    (3, 5),
    (4, 6),
    (4, 7)
    ;
   

-- 샘플 글
INSERT INTO tb_write (user_uid, write_title, write_content)
VALUES
    (2, '김사과표 제목', '김사과씨가 적은 내용입니다.'),
    (3, '반하나표 제목', '반하나씨가 적은 내용입니다.'),
    (4, '오렌지표 제목', '오렌지씨가 적은 내용입니다.'),
    (5, '박수박표 제목', '박수박씨가 적은 내용입니다.'),
    (6, '이체리표 제목', '이체리씨가 적은 내용입니다.'),
    (7, '정멜론표 제목', '정멜론씨가 적은 내용입니다.'),
    (2, '김사과표 제목2', '김사과씨가 또 적은 내용입니다.'),
    (2, '김사과표 제목3', '김사과씨가 또 한번 더 적은 내용입니다.'),
    (6, '이체리표 제목2', '이체리씨가 또 적은 내용입니다.')
    ;


INSERT INTO tb_notice (user_uid, notice_title, notice_content)
VALUES
    (1, '샘플용 공지사항1', '샘플용 공지사항의 상세 내용입니다.'),
    (1, '샘플용 공지사항2', '샘플용 공지사항의 상세 내용입니다.'),
    (1, '샘플용 공지사항3', '샘플용 공지사항의 상세 내용입니다.'),
    (1, '샘플용 공지사항4', '샘플용 공지사항의 상세 내용입니다.'),
    (1, '샘플용 공지사항5', '샘플용 공지사항의 상세 내용입니다.'),
    (1, '샘플용 공지사항6', '샘플용 공지사항의 상세 내용입니다.'),
    (1, '샘플용 공지사항7', '샘플용 공지사항의 상세 내용입니다.')
    ;

-- 두번째 관리자 추가
INSERT INTO tb_user (user_id, user_pw, user_name, user_email, user_phone, user_addr1, user_addr2, user_addr3)
VALUES
	('admin2', '$2a$10$tlrJg7LySHU.2JdNUZ/aX.mp1435Xa9lxqOW2iy6npTqP1sEoZUXK','관리자2', 'admin@gmail.com','01012345678', '서울특별시 강남구 테헤란로 146', '현익빌딩 3층, 4층', '06236')
;
INSERT INTO tb_user_authorities
VALUES (1, 8)
;

INSERT INTO tb_notice (user_uid, notice_title, notice_content)
VALUES
    (8, '샘플용 공지사항8', '샘플용 공지사항의 상세 내용입니다.'),
    (8, '샘플용 공지사항9', '샘플용 공지사항의 상세 내용입니다.'),
    (8, '샘플용 공지사항10', '샘플용 공지사항의 상세 내용입니다.'),
    (8, '샘플용 공지사항11', '샘플용 공지사항의 상세 내용입니다.')
;