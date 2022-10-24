CREATE TABLE IF NOT EXISTS teachers
(
    id            SERIAL PRIMARY KEY,
    first_name    varchar(255) not null,
    middle_name   varchar(255) null,
    last_name     varchar(255) not null,
    date_of_birth DATE         not null
);

CREATE INDEX IF NOT EXISTS teacher_name_index ON teachers (first_name, last_name);