create table roles
(
    id UUID DEFAULT uuid_generate_v4(),
    role_type VARCHAR(30) NOT NULL,
    primary key (id)
);