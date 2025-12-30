DROP TABLE hechizo IF EXISTS;

CREATE TABLE hechizo (
                         id VARCHAR(50) PRIMARY KEY,
                         nombre VARCHAR(100),
                         tipo VARCHAR(50),
                         nivel INT
);