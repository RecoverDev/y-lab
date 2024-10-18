CREATE SCHEMA IF NOT EXISTS habit;

CREATE TABLE habit.person (
	id BIGINT NOT NULL PRIMARY KEY,
	username VARCHAR(255),
	email VARCHAR(255),
	password VARCHAR(255),
	role BIGINT,
    blocked BOOLEAN);

CREATE TABLE habit.habit (
	id BIGINT NOT NULL PRIMARY KEY,
    name_habit VARCHAR(255),
    description VARCHAR(255),
    person_id BIGINT,
    period_id BIGINT,
    registration DATE);

CREATE SEQUENCE habit.person_id_seq START 1;
CREATE SEQUENCE habit.habit_id_seq START 1;
	
INSERT INTO habit.person (id, username, email, password, role, blocked) VALUES 
(nextval('habit.person_id_seq'),'Test User','user1@server.com','111',0, TRUE),
(nextval('habit.person_id_seq'),'Test User2','user2@server.com','222',0, TRUE),
(nextval('habit.person_id_seq'),'Test User3','admin@server.com','1234',1, TRUE);

INSERT INTO habit.habit (id, name_habit, description, person_id, period_id, registration) VALUES
(nextval('habit.habit_id_seq'),'read book','read book every day',1,0,DATE('2014-10-10')),
(nextval('habit.habit_id_seq'),'call mom','call mom every day',1,0,DATE('2014-10-01')),
(nextval('habit.habit_id_seq'),'learn english','learn english',2,1,DATE('2014-10-12')),
(nextval('habit.habit_id_seq'),'morning jog','morning jog every day',2,0,DATE('2014-10-08'));
