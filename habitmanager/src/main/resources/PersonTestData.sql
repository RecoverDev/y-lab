CREATE SCHEMA IF NOT EXISTS habit;

CREATE TABLE habit.person (
	id BIGINT NOT NULL PRIMARY KEY,
	username VARCHAR(255),
	email VARCHAR(255),
	password VARCHAR(255),
	role BIGINT,
    blocked BOOLEAN);

CREATE SEQUENCE habit.person_id_seq START 1;
	
INSERT INTO habit.person (id, username, email, password, role, blocked) VALUES 
(nextval('habit.person_id_seq'),'Test User','user1@server.com','111',0, TRUE),
(nextval('habit.person_id_seq'),'Test User2','user2@server.com','222',0, TRUE),
(nextval('habit.person_id_seq'),'Test User3','admin@server.com','1234',1, TRUE);
