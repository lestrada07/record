CREATE TABLE country (
   id INTEGER IDENTITY PRIMARY KEY,
   country_code VARCHAR(50) NOT NULL UNIQUE,
   name VARCHAR(50) NOT NULL,
   phone_code INTEGER NOT NULL UNIQUE
);


CREATE TABLE city (
   id INTEGER IDENTITY PRIMARY KEY,
   city_code VARCHAR(50) NOT NULL UNIQUE,
   name VARCHAR(50) NOT NULL,
   phone_code INTEGER NOT NULL,
   country_code VARCHAR(50) NOT NULL,
   FOREIGN KEY (country_code) REFERENCES country(country_code)
);


CREATE TABLE user (
   id INTEGER IDENTITY PRIMARY KEY,
   user_id VARCHAR(200) NOT NULL UNIQUE,
   name VARCHAR(50) NOT NULL,
   password VARCHAR(200) NOT NULL,
   token VARCHAR(500) NOT NULL,   
   created DATE,
   modified DATE,
   last_login DATE,
   is_active INTEGER DEFAULT 0
);

CREATE TABLE email (
   id INTEGER IDENTITY PRIMARY KEY,
   email_address VARCHAR(50) NOT NULL UNIQUE,
   user_id VARCHAR(50) NOT NULL,
   FOREIGN KEY (user_id) REFERENCES user(user_id)
);

CREATE TABLE phone (
   id INTEGER IDENTITY PRIMARY KEY,
   phone_number INTEGER,
   user_id VARCHAR(50),
   city_code VARCHAR(50),
   FOREIGN KEY (user_id) REFERENCES user(user_id),
   FOREIGN KEY (city_code) REFERENCES city(city_code)
);
