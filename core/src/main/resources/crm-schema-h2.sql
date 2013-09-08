create sequence  hibernate_sequence ;

CREATE TABLE customer
(
  id bigint NOT NULL,
  first_name varchar(255),
  last_name varchar(255),
  signup_date timestamp  ,
  CONSTRAINT customer_pkey PRIMARY KEY (id )  
);

INSERT INTO customer (id, first_name, last_name, signup_date ) VALUES (NEXTVAL('hibernate_sequence'), 'Arjen', 'Poutsma', '2013-06-12 22:37:22');--, 3);
INSERT INTO customer (id, first_name, last_name, signup_date ) VALUES (NEXTVAL('hibernate_sequence'), 'Mark', 'Pollack', '2013-06-12 22:37:22');--, 4);
INSERT INTO customer (id, first_name, last_name, signup_date ) VALUES (NEXTVAL('hibernate_sequence'), 'David', 'Syer', '2013-06-12 22:37:22');--, 2);
INSERT INTO customer (id, first_name, last_name, signup_date ) VALUES (NEXTVAL('hibernate_sequence'), 'Gunnar', 'Hillert', '2013-06-12 22:37:22');--, 5);
INSERT INTO customer (id, first_name, last_name, signup_date ) VALUES (NEXTVAL('hibernate_sequence'), 'Rossen', 'Stoyanchev', '2013-06-12 22:37:22');--, 5);
INSERT INTO customer (id, first_name, last_name, signup_date ) VALUES (NEXTVAL('hibernate_sequence'), 'Josh', 'Long', '2013-06-13 14:13:05');--, 5);
INSERT INTO customer (id, first_name, last_name, signup_date ) VALUES (NEXTVAL('hibernate_sequence'), 'Brian', 'Dussault', '2013-06-13 17:07:21');--, 3);
INSERT INTO customer (id, first_name, last_name, signup_date ) VALUES (NEXTVAL('hibernate_sequence'), 'Phill', 'Webb', '2013-06-26 03:54:53');--, 4);
INSERT INTO customer (id, first_name, last_name, signup_date ) VALUES (NEXTVAL('hibernate_sequence'), 'Scott', 'Andrews', '2013-06-28 19:11:19');--, 2);
INSERT INTO customer (id, first_name, last_name, signup_date ) VALUES (NEXTVAL('hibernate_sequence'), 'Mark', 'Fisher', '2013-06-28 19:54:14');--, 5);
INSERT INTO customer (id, first_name, last_name, signup_date ) VALUES (NEXTVAL('hibernate_sequence'), 'Mark', 'Pollack', '2013-06-28 19:54:14');--, 2);
INSERT INTO customer (id, first_name, last_name, signup_date ) VALUES (NEXTVAL('hibernate_sequence'), 'Roy', 'Clarkson', '2013-06-28 19:54:14');--, 5);
