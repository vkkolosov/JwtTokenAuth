CREATE table users (

    id serial PRIMARY KEY,
    user_name varchar(64) UNIQUE,
    user_password varchar(64)

);

CREATE table messages (

    id serial PRIMARY KEY,
    user_id integer,
    message_text varchar(255),

    foreign key (user_id) references users (id)
);

INSERT INTO users (user_name, user_password) values ('test', '$2a$10$T2HJ4/RsO4v3NWqv4Ovb9ujgUd77bXxIBlgbIBjTFLfyYfYJW3Hvu');

INSERT INTO messages (user_id, message_text) values (1, 'message1');
INSERT INTO messages (user_id, message_text) values (1, 'message2');