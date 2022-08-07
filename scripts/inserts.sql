

insert into country(country_code, name, phone_code)values('VE', 'Venezuela', 58);
insert into country(country_code, name, phone_code)values('CL', 'Chile', 56);
insert into country(country_code, name, phone_code)values('CO', 'Colombia', 57);
insert into country(country_code, name, phone_code)values('MX', 'Mexico', 52);


insert into city(city_code, name, phone_code, country_code)values('CCS', 'Caracas', 212, 'VE');
insert into city(city_code, name, phone_code, country_code)values('MCB', 'Maracaibo', 261, 'VE');
insert into city(city_code, name, phone_code, country_code)values('STGO', 'Santiago de Chile', 1, 'CL');
insert into city(city_code, name, phone_code, country_code)values('VAL', 'Valparaiso', 2, 'CL');
insert into city(city_code, name, phone_code, country_code)values('BGTA', 'Bogota', 1, 'CO');
insert into city(city_code, name, phone_code, country_code)values('MED', 'Medellin', 12, 'CO');
insert into city(city_code, name, phone_code, country_code)values('CDMX', 'Ciudad de Mexico', 300, 'MX');
insert into city(city_code, name, phone_code, country_code)values('GDL', 'Guadalajara', 301, 'MX');



insert into user(user_id, name, password, token, created, modified, last_login)values('1122', 'John Titor', 'Futuro', '$"·%777', sysdate, sysdate, sysdate);
insert into user(user_id, name, password, token, created, modified, last_login, is_active)values('1123', 'Sarah Connor', 'Machine', '$"·%885', sysdate, sysdate, sysdate, 1);



insert into email(email_address, user_id)values('jtitor@gmail.com', '1122');
insert into email(email_address, user_id)values('sconnor@hotmail.com', '1123');


insert into phone(phone_number, user_id, city_code)values(5555, '1122', 'CCS');
insert into phone(phone_number, user_id, city_code)values(7777, '1123', 'STGO');