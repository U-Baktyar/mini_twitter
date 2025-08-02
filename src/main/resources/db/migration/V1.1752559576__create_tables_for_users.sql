CREATE TABLE users (
       id BIGSERIAL PRIMARY KEY,
       login VARCHAR(50) UNIQUE,
       password VARCHAR(50),
       currentdate TIMESTAMP NOT NULL DEFAULT now(),
       type_user VARCHAR(50)
);

CREATE TABLE person_info (
        user_id BIGINT PRIMARY KEY,
        first_name VARCHAR(50) NOT NULL,
        last_name VARCHAR(50),
        br_data DATE NOT NULL,
        CONSTRAINT person_info_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE organization_info (
       user_id BIGINT PRIMARY KEY,
       organizationname VARCHAR(50) NOT NULL,
       organizationtype VARCHAR(50),
       br_data DATE NOT NULL,
       CONSTRAINT organization_info_user_id_fkey FOREIGN KEY (user_id) REFERENCES users(id)
);


