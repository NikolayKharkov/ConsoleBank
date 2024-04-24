create table users(
      id SERIAL PRIMARY KEY,
      user_login varchar
);

CREATE TABLE accounts(
     id SERIAL PRIMARY KEY,
     user_id int,
     amount double precision,
     CONSTRAINT fk_user
         FOREIGN KEY(user_id)
             REFERENCES users(id)
);