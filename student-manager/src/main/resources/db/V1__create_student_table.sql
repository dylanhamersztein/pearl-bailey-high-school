CREATE TABLE IF NOT EXISTS students
(
    id            SERIAL PRIMARY KEY,
    first_name    varchar(255) not null,
    middle_name   varchar(255) null,
    last_name     varchar(255) not null,
    date_of_birth DATE         not null,
    status        VARCHAR      not null
);

CREATE INDEX IF NOT EXISTS student_name_index ON students (first_name, last_name);
CREATE INDEX IF NOT EXISTS student_status_index ON students (status);