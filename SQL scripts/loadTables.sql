CREATE TABLE users 
(
	username VARCHAR(100) PRIMARY KEY,
	score INT	
);

CREATE TABLE towers
(
	towerid BIGINT(20) AUTO_INCREMENT PRIMARY KEY,
	towername VARCHAR(100),
	wins INT,
	loses INT,
	winloseratio INT,
	towerdata VARCHAR (500)
);

CREATE TABLE tower_user_rel
(
	username VARCHAR(100),
	towerid BIGINT(20),
	PRIMARY KEY (username, towerid),
	FOREIGN KEY (username) REFERENCES users (username),
	FOREIGN KEY (towerid) REFERENCES towers (towerid)
);