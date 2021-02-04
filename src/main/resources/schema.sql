CREATE TABLE service (
    id INT auto_increment,
	s_name CHAR(40) NOT NULL,
	r_date DATE NOT NULL,
	t_period DATE NOT NULL,
	mailaddress CHAR(200),
	card_bra CHAR(40),
	card_num CHAR(16),
	s_id CHAR(40),
	password CHAR(40),
	other CHAR(400)
);