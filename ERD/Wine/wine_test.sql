select * from tb_wine;
SELECT * FROM tb_buy ;
SELECT * FROM tb_point ;
SELECT user_point  FROM tb_point WHERE user_uid = 23;
SELECT ifnull(sum(user_point), 0) AS "userPoint"  FROM tb_point WHERE user_uid = 23;

select wine_id from tb_wine where wine_serialkey = 3 and wine_type = 'red';

--select wnrv_reviews from tb_wine_review
--	where wine_id = (select wine_id from tb_wine where wine_serialkey = 3 and wine_type = 'red');

select wine_count from tb_wine where wine_id = 4;

UPDATE tb_wine SET wine_count = wine_count - 1 where wine_id = 4;

INSERT INTO tb_buy(user_uid, wine_id, buy_quantity,buy_regdate, wine_paymentKey) 
	VALUES (4, 4, 1, now() ,"aabb123456");

INSERT INTO tb_point (user_uid, wine_id, user_point, regdate)
	VALUES (2, 4, 100, now());

SELECT a.authority_id "authority_id", a.authority "authority"
        FROM tb_authority a, tb_user_authorities u
        WHERE a.authority_id = u.authority_id  AND  u.user_uid = 2;
       
INSERT INTO tb_wine
	(wine_serialkey,
	wine_winery, 
	wine_name, 
	wine_location, 
	wine_img, 
	wine_regdate, 
	wine_price, 
	wine_count, 
	wine_type)
	VALUES
	(
	3,
	"winery",
	"name",
	"location",
	"img",
	now(),
	1000,
	5,
	"type"
	);
       
       
       

