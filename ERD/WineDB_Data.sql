-- 권한 추가
ALTER TABLE tb_user AUTO_INCREMENT = 1;

INSERT INTO tb_authority (authority) VALUES
    ('ROLE_ADMIN'), ('ROLE_SILVER'), ('ROLE_GOLD'), ('ROLE_DIAMOND')
;
   
insert into tb_authority (authority_id, authority) values
	(1, 'ROLE_ADMIN'), (2, 'ROLE_SILVER'), (3, 'ROLE_GOLD'), (4, 'ROLE_DIAMOND')
;

-- 샘플 사용자
INSERT INTO tb_user (user_id, user_pw, user_name, user_email, user_phone, user_addr1, user_addr2, user_addr3)
VALUES
    ('admin', '0000', '관리자', 'admin@gmail.com', '01000000000', '서울특별시 강남구 테헤란로 146', '현익빌딩 3층, 4층', '06236'),
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
    (1, 4),
    (2, 2),
    (3, 2),
    (4, 3),
    (5, 3),
    (6, 4),
    (7, 4)
    ;

-- 샘플 글
INSERT INTO t5_write (user_uid, write_title, write_content)
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


INSERT INTO tb_notice (user_uid, notice_content, notice_title)
VALUES
    (1, '샘플용 공지사항1', '샘플용 공지사항의 상세 내용입니다.'),
    (1, '샘플용 공지사항2', '샘플용 공지사항의 상세 내용입니다.'),
    (1, '샘플용 공지사항3', '샘플용 공지사항의 상세 내용입니다.'),
    (1, '샘플용 공지사항4', '샘플용 공지사항의 상세 내용입니다.'),
    (1, '샘플용 공지사항5', '샘플용 공지사항의 상세 내용입니다.'),
    (1, '샘플용 공지사항6', '샘플용 공지사항의 상세 내용입니다.'),
    (1, '샘플용 공지사항7', '샘플용 공지사항의 상세 내용입니다.')
    ;