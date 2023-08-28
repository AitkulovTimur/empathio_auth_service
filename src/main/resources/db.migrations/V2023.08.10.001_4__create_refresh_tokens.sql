create table refresh_tokens
(
    id UUID DEFAULT uuid_generated_v4(),
    user_id UUID REFERENCES users(id),
);