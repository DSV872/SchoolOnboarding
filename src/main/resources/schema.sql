-- 1. School
CREATE TABLE school (
    school_id BIGINT NOT NULL,
    school_name VARCHAR(255),
    school_type VARCHAR(50),
    contact_number VARCHAR(20),
    address VARCHAR(255),
    PRIMARY KEY (school_id)
);

-- 2. Grade
CREATE TABLE grade (
    grade_id BIGINT NOT NULL,
    school_id BIGINT,
    grade_name VARCHAR(100),
    display_order INT,
    is_active BOOLEAN,
    PRIMARY KEY (grade_id),
    FOREIGN KEY (school_id) REFERENCES school(school_id)
);
-- 3. Section
CREATE TABLE section (
    section_id BIGINT NOT NULL,
    grade_id BIGINT,
    section_name VARCHAR(50),
    is_active BOOLEAN,
    PRIMARY KEY (section_id),
    FOREIGN KEY (grade_id) REFERENCES grade(grade_id)
);
-- 4. Subject
CREATE TABLE subject (
    subject_id BIGINT NOT NULL,
    school_id BIGINT,
    subject_name VARCHAR(100),
    is_active BOOLEAN,
    PRIMARY KEY (subject_id),
    FOREIGN KEY (school_id) REFERENCES school(school_id)
);
-- 5. Teacher
CREATE TABLE teacher (
    teacher_id BIGINT NOT NULL,
    school_id BIGINT,
    teacher_name VARCHAR(100),
    gender VARCHAR(10),
    qualification VARCHAR(100),
    contact_number VARCHAR(20),
    PRIMARY KEY (teacher_id),
    FOREIGN KEY (school_id) REFERENCES school(school_id)
);
-- 6. Student
CREATE TABLE student (
    student_id BIGINT NOT NULL,
    school_id BIGINT,
    student_name VARCHAR(100),
    dob DATE,
    gender VARCHAR(10),
    PRIMARY KEY (student_id),
    FOREIGN KEY (school_id) REFERENCES school(school_id)
);
-- 7. Student Academic Map
CREATE TABLE student_academic_map (
    id BIGINT NOT NULL AUTO_INCREMENT,
    student_id BIGINT,
    grade_id BIGINT,
    section_id BIGINT,
    academic_year VARCHAR(20),
    PRIMARY KEY (id),
    FOREIGN KEY (student_id) REFERENCES student(student_id),
    FOREIGN KEY (grade_id) REFERENCES grade(grade_id),
    FOREIGN KEY (section_id) REFERENCES section(section_id)
);
-- 8. Teacher Section Map
CREATE TABLE teacher_section_map (
    id BIGINT NOT NULL AUTO_INCREMENT,
    teacher_id BIGINT,
    section_id BIGINT,
    subject_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id),
    FOREIGN KEY (section_id) REFERENCES section(section_id),
    FOREIGN KEY (subject_id) REFERENCES subject(subject_id)
);
-- 9. Attendance Register
CREATE TABLE attendance_register (
    id BIGINT NOT NULL AUTO_INCREMENT,
    student_id BIGINT,
    date DATE,
    status VARCHAR(10),
    PRIMARY KEY (id),
    FOREIGN KEY (student_id) REFERENCES student(student_id)
);
-- 10. Homework
CREATE TABLE homework (
    id BIGINT NOT NULL AUTO_INCREMENT,
    teacher_id BIGINT,
    status VARCHAR(20),
    assigned_date DATE,
    description TEXT,
    PRIMARY KEY (id),
    FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id)
);
-- 11. Class Diary
CREATE TABLE class_diary (
    id BIGINT NOT NULL AUTO_INCREMENT,
    teacher_id BIGINT,
    date DATE,
    notes VARCHAR(100),
    PRIMARY KEY (id),
    FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id)
);
-- 12. Fees
CREATE TABLE fees (
    id BIGINT NOT NULL AUTO_INCREMENT,
    student_id BIGINT,
    amount DECIMAL(10,2),
    date DATE,
    PRIMARY KEY (id),
    FOREIGN KEY (student_id) REFERENCES student(student_id)
);
-- 13. Salary
CREATE TABLE salary (
    id BIGINT NOT NULL AUTO_INCREMENT,
    teacher_id BIGINT,
    month VARCHAR(20),
    amount DECIMAL(10,2),
    PRIMARY KEY (id),
    FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id)
);
-- 14. Timetable
CREATE TABLE timetable (
    id BIGINT NOT NULL AUTO_INCREMENT,
    section_id BIGINT,
    day_of_week VARCHAR(20),
    period VARCHAR(30),
    teacher_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (section_id) REFERENCES section(section_id),
    FOREIGN KEY (teacher_id) REFERENCES teacher(teacher_id)
);