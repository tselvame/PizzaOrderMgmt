DROP TABLE IF EXISTS payment_details;
DROP TABLE IF EXISTS pizza_details;
DROP TABLE IF EXISTS order_details;
 
CREATE TABLE order_details (
  order_id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(250) NOT NULL,
  mid_Name VARCHAR(50) NULL,
  last_name VARCHAR(250) NOT NULL,
  dob VARCHAR(10) NULL,
  phone_no VARCHAR(15) NULL,
  cust_note VARCHAR(250) NULL,
  address_line1 VARCHAR(250) NOT NULL,
  address_line2 VARCHAR(250) NULL,
  city VARCHAR(50) NOT NULL,
  state VARCHAR(50) NOT NULL,
  zip_code VARCHAR(10) NOT NULL,
  est_delv_time INT NULL,
  order_status_cd VARCHAR(2) NOT NULL,
  create_dt DATE NULL,
  update_dt DATE NULL
);

CREATE TABLE pizza_details (
  pizza_spec_id INT AUTO_INCREMENT  PRIMARY KEY,
  pizza_style VARCHAR(50) NOT NULL,
  pizza_size INT NOT NULL,
  topng_cd VARCHAR(250) NOT NULL,
  topng_no INT NOT NULL,
  specl_note VARCHAR(250) NULL,
  create_dt DATE NULL,
  update_dt DATE NULL,
  order_id INT,
  FOREIGN KEY (order_id) references order_details(order_id)
);

CREATE TABLE payment_details (
  payment_id INT AUTO_INCREMENT  PRIMARY KEY,
  is_credit VARCHAR(10) NOT NULL,
  card_type VARCHAR(50) NOT NULL,
  card_no VARCHAR(250) NOT NULL,
  card_exp_dt VARCHAR(10) NOT NULL,
  card_secure_cd VARCHAR(3) NOT NULL,
  coupon_cd VARCHAR(250) NULL,
  address_line1 VARCHAR(250) NOT NULL,
  address_line2 VARCHAR(250) NULL,
  city VARCHAR(50) NOT NULL,
  state VARCHAR(50) NOT NULL,
  zip_code VARCHAR(10) NOT NULL,
  create_dt DATE NULL,
  update_dt DATE NULL,
  order_id INT,
  FOREIGN KEY (order_id) references order_details(order_id)
);
