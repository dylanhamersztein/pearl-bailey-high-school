CREATE TABLE IF NOT EXISTS departments
(
    id                    SERIAL PRIMARY KEY,
    name                  varchar not null,
    head_of_department_id int     not null
);