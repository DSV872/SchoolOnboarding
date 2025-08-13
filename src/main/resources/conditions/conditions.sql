-- Getting the students those are >= 80%
SELECT student_id, 
       COUNT(*) AS total_days, 
       SUM(status = 'Present') AS present_days,
       (SUM(status = 'Present') / COUNT(*)) * 100 AS present_percentage
FROM attendance_register
GROUP BY student_id;

-- Homework of the specific teacher given
SELECT teacher_id, status, COUNT(*) 
FROM homework
GROUP BY teacher_id, status;

-- Diary given by the teachers count
SELECT teacher_id, COUNT(*) AS entry_count
FROM class_diary
GROUP BY teacher_id
HAVING entry_count >= 2;

-- Fees atleast 1 per student
SELECT student_id, COUNT(*) AS fee_count
FROM fees
GROUP BY student_id
HAVING fee_count >= 1;

-- Salary: June & July entries per teacher
SELECT teacher_id, GROUP_CONCAT(month) AS months_paid
FROM salary
GROUP BY teacher_id;

-- Timetable completeness
SELECT section_id, COUNT(*) AS total_periods
FROM timetable
GROUP BY section_id;

-- Lunch Break check
SELECT section_id, day_of_week, COUNT(*) AS lunch_count
FROM timetable
WHERE period = 'Lunch Break'
GROUP BY section_id, day_of_week
HAVING lunch_count >= 1;

-- Free period check
SELECT section_id, COUNT(*) AS free_count
FROM timetable
WHERE period = 'Free Period'
GROUP BY section_id
HAVING free_count >= 1;

-- Teacher leave check
SELECT teacher_id, COUNT(*) AS leave_count
FROM timetable
WHERE period = 'Leave'
GROUP BY teacher_id
HAVING leave_count >= 1;

INSERT INTO timetable (section_id, day_of_week, period, teacher_id)
VALUES (501, 'Monday', 'Leave', 701),
       (502, 'Tuesday', 'Leave', 702);





