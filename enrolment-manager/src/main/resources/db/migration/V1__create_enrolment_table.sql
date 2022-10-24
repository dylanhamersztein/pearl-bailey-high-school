CREATE TABLE IF NOT EXISTS enrolments
(
    id         SERIAL PRIMARY KEY,
    student_id INT NOT NULL,
    course_id  INT NOT NULL
);

CREATE INDEX IF NOT EXISTS enrolments_student_id_course_id_idx ON enrolments (student_id, course_id);
