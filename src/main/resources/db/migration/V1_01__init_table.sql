USE test_flyweight;

CREATE TABLE account (
  id bigint NOT NULL AUTO_INCREMENT,
  username varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  password varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  email varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  role varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  status varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_account_username (username),
  UNIQUE KEY UK_account_email (email)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE customer (
  id bigint NOT NULL AUTO_INCREMENT,
  first_name varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  last_name varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  address varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  phone varchar(12) COLLATE utf8mb4_unicode_ci NOT NULL,
  account_id bigint DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_customer_phone (phone),
  UNIQUE KEY UK_customer_account_id (account_id),
  CONSTRAINT FK_customer_account_id FOREIGN KEY (account_id) REFERENCES account (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE employee (
  id bigint NOT NULL AUTO_INCREMENT,
  first_name varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  last_name varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  phone varchar(12) COLLATE utf8mb4_unicode_ci NOT NULL,
  address varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  salary double NOT NULL,
  allowance double DEFAULT NULL,
  account_id bigint DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_employee_phone (phone),
  UNIQUE KEY UK_employee_account_id (account_id),
  CONSTRAINT FK_employee_account_id FOREIGN KEY (account_id) REFERENCES account (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE category (
  number bigint NOT NULL AUTO_INCREMENT,
  name varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (number)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE goods (
  number bigint NOT NULL AUTO_INCREMENT,
  description text COLLATE utf8mb4_unicode_ci NOT NULL,
  name varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  price double NOT NULL,
  total_quantity int NOT NULL,
  category_number bigint DEFAULT NULL,
  PRIMARY KEY (number),
  KEY FKf9kgwdd50kjpsu1tqg6pn2i9y (category_number),
  CONSTRAINT FKf9kgwdd50kjpsu1tqg6pn2i9y FOREIGN KEY (category_number) REFERENCES category (number)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE image (
  id bigint NOT NULL AUTO_INCREMENT,
  link varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8_unicode_ci NOT NULL,
  goods_number bigint DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_4pgw8w3qp244ujvcs4wowiv3x (link),
  KEY FKdolwhgwk75ds660tuahohhnyj (goods_number),
  CONSTRAINT FKdolwhgwk75ds660tuahohhnyj FOREIGN KEY (goods_number) REFERENCES goods (number)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE cart (
  id bigint NOT NULL AUTO_INCREMENT,
  customer_id bigint NOT NULL,
  total_cart_details int NOT NULL,
  total_price double NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_867x3yysb1f3jk41cv3vsoejj (customer_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE cart_details (
  cart_id bigint NOT NULL,
  goods_id bigint NOT NULL,
  price double NOT NULL,
  quantity int NOT NULL,
  total_price double NOT NULL,
  PRIMARY KEY (cart_id,goods_id),
  KEY FK56ksgv1kcrnawkgh59hve8unw (goods_id),
  CONSTRAINT FK56ksgv1kcrnawkgh59hve8unw FOREIGN KEY (goods_id) REFERENCES goods (number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE car_care (
  number bigint NOT NULL AUTO_INCREMENT,
  description varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  price double NOT NULL,
  service_name varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (number),
  UNIQUE KEY UK_tbit9fo3l6b0r0kv9rwnxkcyd (service_name)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE repaired_car (
  id bigint NOT NULL AUTO_INCREMENT,
  car_number varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  car_status varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  car_type varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  description varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  end_date datetime(6) DEFAULT NULL,
  start_date datetime(6) DEFAULT NULL,
  customer_id bigint NOT NULL,
  PRIMARY KEY (id),
  KEY FKfi8inorb6o7k25v77pdxu8dj1 (customer_id),
  CONSTRAINT FKfi8inorb6o7k25v77pdxu8dj1 FOREIGN KEY (customer_id) REFERENCES customer (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE used_service (
  car_care_number bigint NOT NULL,
  repaired_car_id bigint NOT NULL,
  car_care_price double NOT NULL,
  PRIMARY KEY (car_care_number,repaired_car_id),
  KEY FKjhma8cknwkgl68wvauuj4duef (repaired_car_id),
  CONSTRAINT FKjhma8cknwkgl68wvauuj4duef FOREIGN KEY (repaired_car_id) REFERENCES repaired_car (id),
  CONSTRAINT FKs8nsd00kd61gc12l1ppaihvno FOREIGN KEY (car_care_number) REFERENCES car_care (number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE bills (
  number bigint NOT NULL AUTO_INCREMENT,
  payment_date date NOT NULL,
  payment_method varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  total_money bigint NOT NULL,
  repaired_car_id bigint NOT NULL,
  PRIMARY KEY (number),
  KEY FKr0t8j5rplrisofx64idbvwe7u (repaired_car_id),
  CONSTRAINT FKr0t8j5rplrisofx64idbvwe7u FOREIGN KEY (repaired_car_id) REFERENCES repaired_car (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE goods_car (
  price double NOT NULL,
  quantity int NOT NULL,
  total_price double NOT NULL,
  repaired_car_id bigint NOT NULL,
  goods_number bigint NOT NULL,
  PRIMARY KEY (goods_number,repaired_car_id),
  KEY FKh1iim3aa649jmmdgtjip13xrx (repaired_car_id),
  CONSTRAINT FKh1iim3aa649jmmdgtjip13xrx FOREIGN KEY (repaired_car_id) REFERENCES repaired_car (id),
  CONSTRAINT FKrf8g8p4q220f1cx00vohqtj5g FOREIGN KEY (goods_number) REFERENCES goods (number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE goods_bill (
  id bigint NOT NULL AUTO_INCREMENT,
  payment_date date DEFAULT NULL,
  payment_method varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  purchase_date date NOT NULL,
  total_money double DEFAULT NULL,
  customer_id bigint NOT NULL,
  PRIMARY KEY (id),
  KEY FKsdwkeg0as5puo80xkf0ud5a5v (customer_id),
  CONSTRAINT FKsdwkeg0as5puo80xkf0ud5a5v FOREIGN KEY (customer_id) REFERENCES customer (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE goods_bill_details (
  price double NOT NULL,
  quantity int NOT NULL,
  total_price double NOT NULL,
  goods_number bigint NOT NULL,
  goods_bill_number bigint NOT NULL,
  PRIMARY KEY (goods_number,goods_bill_number),
  KEY FKo2aw0uagf77swem55d8ghvj23 (goods_bill_number),
  CONSTRAINT FKo2aw0uagf77swem55d8ghvj23 FOREIGN KEY (goods_bill_number) REFERENCES goods_bill (id),
  CONSTRAINT FKsq91rh5gnh0ccf4c2w4cy2i3b FOREIGN KEY (goods_number) REFERENCES goods (number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE confirm_token (
  id bigint NOT NULL AUTO_INCREMENT,
  confirm_at datetime(6) DEFAULT NULL,
  create_at datetime(6) DEFAULT NULL,
  expire_at datetime(6) DEFAULT NULL,
  token varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  account_id bigint NOT NULL,
  PRIMARY KEY (id),
  KEY FKlf74je4uadeiuuq8i8p2l7vuj (account_id),
  CONSTRAINT FKlf74je4uadeiuuq8i8p2l7vuj FOREIGN KEY (account_id) REFERENCES account (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
