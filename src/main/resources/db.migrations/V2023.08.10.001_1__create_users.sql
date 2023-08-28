create table users
(
    id UUID DEFAULT uuid_generate_v4(),
    username VARCHAR(20) UNIQUE NOT NULL,
    first_name VARCHAR(60) NOT NULL,
    last_name VARCHAR(60) NOT NULL,
    email VARCHAR(60) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    expiration_date_time TIMESTAMP WITH TIME ZONE,
    blocked BOOLEAN DEFAULT FALSE,
    age INT(90) NOT NULL,
    primary key (id)
);