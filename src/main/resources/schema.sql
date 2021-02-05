CREATE TABLE service (
    id INT auto_increment,
	service_name CHAR(40) NOT NULL,
	registration_date DATE NOT NULL,
	period DATE NOT NULL,
	mail_address CHAR(200),
	card_brand CHAR(40),
	card_num CHAR(16),
	service_id CHAR(40),
	password CHAR(40),
	memo CHAR(400)
);