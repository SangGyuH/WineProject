SET SESSION FOREIGN_KEY_CHECKS=0;

/* Drop Tables */

DROP TABLE IF EXISTS tb_user_authorities;
DROP TABLE IF EXISTS tb_authority;
DROP TABLE IF EXISTS tb_buy;
DROP TABLE IF EXISTS tb_notice_file;
DROP TABLE IF EXISTS tb_notice;
DROP TABLE IF EXISTS tb_point;
DROP TABLE IF EXISTS tb_wine_review;
DROP TABLE IF EXISTS tb_write_file;
DROP TABLE IF EXISTS tb_write_review;
DROP TABLE IF EXISTS tb_write;
DROP TABLE IF EXISTS tb_user;
DROP TABLE IF EXISTS tb_wine;




/* Create Tables */

CREATE TABLE tb_authority
(
	authority_id int NOT NULL AUTO_INCREMENT,
	authority varbinary(30) NOT NULL,
	PRIMARY KEY (authority_id)
);


CREATE TABLE tb_buy
(
	user_uid int NOT NULL,
	wine_id int NOT NULL,
	buy_quantity int NOT NULL DEFAULT 1,
	buy_regdate datetime DEFAULT now()
);


CREATE TABLE tb_notice
(
	notice_id int NOT NULL AUTO_INCREMENT,
	user_uid int NOT NULL,
	notice_title varchar(100) NOT NULL,
	notice_content longtext NOT NULL,
	notice_regdate datetime DEFAULT now(),
	PRIMARY KEY (notice_id)
);


CREATE TABLE tb_notice_file
(
	notice_file_id int NOT NULL AUTO_INCREMENT,
	notice_id int NOT NULL,
	source varchar(100) NOT NULL,
	file varchar(100),
	PRIMARY KEY (notice_file_id)
);


CREATE TABLE tb_point
(
	user_uid int NOT NULL,
	wine_id int NOT NULL,
	user_point int DEFAULT 0,
	regdate datetime DEFAULT now()
);


CREATE TABLE tb_user
(
	user_uid int NOT NULL AUTO_INCREMENT,
	user_id varchar(100) NOT NULL,
	user_pw varchar(100) NOT NULL,
	user_name varbinary(50) NOT NULL,
	user_email varchar(30),
	user_phone varchar(13),
	user_addr1 varchar(50) NOT NULL,
	user_addr2 varchar(50) NOT NULL,
	user_addr3 varchar(6) NOT NULL,
	PRIMARY KEY (user_uid),
	UNIQUE (user_id)
);


CREATE TABLE tb_user_authorities
(
	authority_id int NOT NULL,
	user_uid int NOT NULL
);


CREATE TABLE tb_wine
(
	wine_id int NOT NULL AUTO_INCREMENT,
	wine_winery varchar(50) NOT NULL,
	wine_name varchar(50) NOT NULL,
	wine_location varchar(30) NOT NULL,
	wine_img varchar(70),
	wine_regdate datetime DEFAULT now(),
	wine_price int NOT NULL,
	wine_type varchar(30),
	wine_count int DEFAULT 0,
	PRIMARY KEY (wine_id)
);


CREATE TABLE tb_wine_review
(
	wnrv_id int NOT NULL AUTO_INCREMENT,
	user_uid int NOT NULL,
	wine_id int NOT NULL,
	wnrv_content longtext NOT NULL,
	wnrv_regdate datetime DEFAULT now(),
	wnrv_reviews int DEFAULT 0,
	PRIMARY KEY (wnrv_id)
);


CREATE TABLE tb_write
(
	write_id int NOT NULL AUTO_INCREMENT,
	user_uid int NOT NULL,
	write_title longtext NOT NULL,
	write_content longtext NOT NULL,
	write_regdate datetime DEFAULT now(),
	PRIMARY KEY (write_id)
);


CREATE TABLE tb_write_file
(
	write_file_id int NOT NULL AUTO_INCREMENT,
	write_id int NOT NULL,
	source varchar(100),
	file varchar(100),
	PRIMARY KEY (write_file_id)
);


CREATE TABLE tb_write_review
(
	wr_id int NOT NULL AUTO_INCREMENT,
	write_id int NOT NULL,
	user_uid int NOT NULL,
	wr_content longtext NOT NULL,
	wr_regdate datetime DEFAULT now(),
	PRIMARY KEY (wr_id)
);



/* Create Foreign Keys */

ALTER TABLE tb_user_authorities
	ADD FOREIGN KEY (authority_id)
	REFERENCES tb_authority (authority_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE tb_notice_file
	ADD FOREIGN KEY (notice_id)
	REFERENCES tb_notice (notice_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE tb_buy
	ADD FOREIGN KEY (user_uid)
	REFERENCES tb_user (user_uid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE tb_notice
	ADD FOREIGN KEY (user_uid)
	REFERENCES tb_user (user_uid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE tb_point
	ADD FOREIGN KEY (user_uid)
	REFERENCES tb_user (user_uid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE tb_user_authorities
	ADD FOREIGN KEY (user_uid)
	REFERENCES tb_user (user_uid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE tb_wine_review
	ADD FOREIGN KEY (user_uid)
	REFERENCES tb_user (user_uid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE tb_write
	ADD FOREIGN KEY (user_uid)
	REFERENCES tb_user (user_uid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE tb_write_review
	ADD FOREIGN KEY (user_uid)
	REFERENCES tb_user (user_uid)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE tb_buy
	ADD FOREIGN KEY (wine_id)
	REFERENCES tb_wine (wine_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE tb_point
	ADD FOREIGN KEY (wine_id)
	REFERENCES tb_wine (wine_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE tb_wine_review
	ADD FOREIGN KEY (wine_id)
	REFERENCES tb_wine (wine_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE tb_write_file
	ADD FOREIGN KEY (write_id)
	REFERENCES tb_write (write_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;


ALTER TABLE tb_write_review
	ADD FOREIGN KEY (write_id)
	REFERENCES tb_write (write_id)
	ON UPDATE RESTRICT
	ON DELETE RESTRICT
;



