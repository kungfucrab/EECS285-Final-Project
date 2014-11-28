CREATE TABLE users 
(
	username VARCHAR(100) PRIMARY KEY,
	score INT	
);

CREATE TABLE towers
(
	towername VARCHAR(100) PRIMARY KEY,
	wins INT,
	loses INT,
	towerdata VARCHAR (2500)
);

CREATE TABLE tower_user_rel
(
	username VARCHAR(100),
	towername VARCHAR(100),
	PRIMARY KEY (username, towername),
	FOREIGN KEY (username) REFERENCES users (username),
	FOREIGN KEY (towername) REFERENCES towers (towername)
);