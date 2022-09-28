DROP TABLE IF EXISTS MOVIES;
CREATE TABLE MOVIES (
                      id INT AUTO_INCREMENT  PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      aired_year VARCHAR(50),
                      type VARCHAR(50),
                      imdbid VARCHAR(20),
                      poster_url VARCHAR(255)
);
