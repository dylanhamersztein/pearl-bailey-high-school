CREATE TABLE IF NOT EXISTS courses
(
    id            SERIAL PRIMARY KEY,
    name          varchar not null,
    department_id int     not null,
    taught_by     int     not null,
    description   varchar not null,
    course_status varchar not null
);

CREATE INDEX IF NOT EXISTS courses_taught_by_index ON courses (taught_by);
CREATE INDEX IF NOT EXISTS courses_department_id_index ON courses (department_id);
CREATE INDEX IF NOT EXISTS courses_status_index ON courses (course_status);