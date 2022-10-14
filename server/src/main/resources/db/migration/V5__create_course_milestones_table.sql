CREATE TABLE IF NOT EXISTS course_milestones
(
    id        SERIAL PRIMARY KEY,
    name      varchar not null,
    course_id int     not null references courses (id),
    type      varchar not null
);

CREATE INDEX IF NOT EXISTS courses_course_id_index ON course_milestones (course_id);
CREATE INDEX IF NOT EXISTS courses_course_id__type_index ON course_milestones (course_id, type);