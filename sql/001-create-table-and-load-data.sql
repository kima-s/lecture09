DROP TABLE IF EXISTS users;

CREATE TABLE users (
 id int unsigned AUTO_INCREMENT,
 name VARCHAR(20) NOT NULL,
 address VARCHAR(20) NOT NULL,
 age int unsigned NOT NULL,
 PRIMARY KEY(id)
);

INSERT INTO users (id, name, address, age) VALUES (1, "tanaka", "Aichi", 35);
INSERT INTO users (id, name, address, age) VALUES (2, "sakata", "Tokyo", 60);
INSERT INTO users (id, name, address, age) VALUES (3, "nishimura", "Osaka", 46);