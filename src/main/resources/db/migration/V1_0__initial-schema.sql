CREATE TABLE sector (
    id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    parent_id INT,
    PRIMARY KEY (id),
    CONSTRAINT fk_sector_parent FOREIGN KEY (parent_id)
      REFERENCES sector(id)
      ON DELETE SET NULL
);

CREATE TABLE user_data (
    id INT AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    agree BOOLEAN NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE user_sector (
    id INT AUTO_INCREMENT,
    user_data_id INT NOT NULL,
    sector_id INT NOT NULL,
    PRIMARY KEY (user_data_id, sector_id),
    CONSTRAINT fk_user_sector_user FOREIGN KEY (user_data_id)
      REFERENCES user_data(id)
      ON DELETE CASCADE,
    CONSTRAINT fk_user_sector_sector FOREIGN KEY (sector_id)
      REFERENCES sector(id)
      ON DELETE CASCADE
);
