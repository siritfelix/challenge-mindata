CREATE TABLE
    superheroes (
        id int NOT NULL AUTO_INCREMENT,
        name varchar(50),
        CONSTRAINT "CONSTRAINT_1" PRIMARY KEY (id)
    );

CREATE TABLE
    superheroskills (
        id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
        description varchar(50),
        super_hero_id int,
        CONSTRAINT "CONSTRAINT_2" PRIMARY KEY (id)
    );

CREATE TABLE
    users (
        id int NOT NULL AUTO_INCREMENT,
        first_name varchar(50),
        last_name varchar(50),
        email varchar(50),
        password varchar(500),
        role varchar(50),
        CONSTRAINT "CONSTRAINT_3" PRIMARY KEY (id)
    );

ALTER TABLE superheroskills ADD CONSTRAINT FKmtk37pxnx7d5ck7fkq2xcna4i FOREIGN KEY (super_hero_id) REFERENCES superheroes (id);
ALTER TABLE users ADD CONSTRAINT UK_srv16ica2c1csub334bxjjb59 UNIQUE (email);