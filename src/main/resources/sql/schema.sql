CREATE DATABASE IF NOT EXISTS beauty_salon;

USE beauty_salon;


CREATE TABLE user(
                     full_name VARCHAR(30) NOT NULL,
                     user_name VARCHAR(30) NOT NULL,
                     password VARCHAR(30) NOT NULL
);


CREATE TABLE staff(
                      st_id VARCHAR(30) PRIMARY KEY,
                      st_name VARCHAR(100) NOT NULL,
                      st_address VARCHAR(100) NOT NULL,
                      st_email VARCHAR(100) NOT NULL,
                      st_tel VARCHAR(10) NOT NULL,
                      st_type VARCHAR(100) NOT NULL
);


CREATE TABLE customer(
                         cus_id VARCHAR(30) PRIMARY KEY,
                         cus_name VARCHAR(100) NOT NULL,
                         cus_email VARCHAR(100) NOT NULL,
                         cus_tel VARCHAR(10) NOT NULL
);


CREATE TABLE product(
                        pr_id VARCHAR(30) PRIMARY KEY,
                        pr_name VARCHAR(100) NOT NULL,
                        pr_type VARCHAR(100) NOT NULL,
                        pr_unit_price DOUBLE NOT NULL,
                        pr_qty_on_hand INT NOT NULL
);


CREATE TABLE service(
                        ser_id VARCHAR(30) PRIMARY KEY,
                        ser_name VARCHAR(100) NOT NULL,
                        ser_type VARCHAR(100) NOT NULL,
                        ser_unit_price DOUBLE NOT NULL
);


CREATE TABLE orders(
                       order_id VARCHAR(30) PRIMARY KEY,
                       cus_id VARCHAR(30) NOT NULL,
                       order_date DATE NOT NULL,
                       order_amount DOUBLE NOT NULL,
                       CONSTRAINT FOREIGN KEY(cus_id) REFERENCES customer(cus_id)
                           ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE appointment(
                            app_id VARCHAR(30) PRIMARY KEY,
                            cus_id VARCHAR(30) NOT NULL,
                            app_date DATE NOT NULL,
                            app_time VARCHAR(30) NOT NULL,
                            app_amount DOUBLE NOT NULL,
                            CONSTRAINT FOREIGN KEY(cus_id) REFERENCES customer(cus_id)
                                ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE payment(
                        pay_id VARCHAR(30) PRIMARY KEY,
                        cus_id VARCHAR(30) NOT NULL,
                        pay_amount DOUBLE NOT NULL,
                        pay_date DATE NOT NULL,
                        pay_time VARCHAR(30) NOT NULL,
                        CONSTRAINT FOREIGN KEY(cus_id) REFERENCES customer(cus_id)
                            ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE staff_app_detail(
                                 app_id VARCHAR(30) NOT NULL,
                                 st_id VARCHAR(30) NOT NULL,
                                 CONSTRAINT FOREIGN KEY(app_id) REFERENCES appointment(app_id)
                                     ON DELETE CASCADE ON UPDATE CASCADE,
                                 CONSTRAINT FOREIGN KEY(st_id) REFERENCES staff(st_id)
                                     ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE product_order_detail(
                                     order_id VARCHAR(30) NOT NULL,
                                     pr_id VARCHAR(30) NOT NULL,
                                     bought_qty INT NOT NULL,
                                     unit_price DOUBLE NOT NULL,
                                     CONSTRAINT FOREIGN KEY(order_id) REFERENCES orders(order_id)
                                         ON DELETE CASCADE ON UPDATE CASCADE,
                                     CONSTRAINT FOREIGN KEY(pr_id) REFERENCES product(pr_id)
                                         ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE service_app_detail(
                                   app_id VARCHAR(30) NOT NULL,
                                   ser_id VARCHAR(30) NOT NULL,
                                   CONSTRAINT FOREIGN KEY(app_id) REFERENCES appointment(app_id)
                                       ON DELETE CASCADE ON UPDATE CASCADE,
                                   CONSTRAINT FOREIGN KEY(ser_id) REFERENCES service(ser_id)
                                       ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO user VALUES("Ayoma Hansani", "ayohansi17@gmail.com", "12345678");
INSERT INTO user VALUES("Sayuri Vithanarachchi", "sayurivithana@gmail.com", "sayuri987");
INSERT INTO user VALUES("Nipuni Chanchala", "nipunichan@gmail.com", "nipuni123");
