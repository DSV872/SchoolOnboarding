# 📌 School Onboarding Assignment — Java + Spring Boot + JPA

## 👤 Author: Dasari Santhi Vardhan
**Email:** dasarisanthivardhan872@gmail.com  
**LinkedIn:** [Profile](https://www.linkedin.com/in/dasari-santhi-vardhan-b25515290/)

---

## 1. Overview
This project implements a **School Onboarding System** using **Java, Spring Boot, Spring Data JPA, Apache POI, and MySQL**.  
It automates the loading of school-related data from Excel files into the database, ensuring all **functional rules** mentioned in the assignment are satisfied.

---

## 2. Technologies Used
- **Java 21**
- **Spring Boot** (REST + Data JPA)
- **MySQL**
- **Apache POI** (Excel reading)
- **Lombok**
- **Maven**

---

## 3. Project Structure
```
src/
 ├── main/
 │   ├── java/com/dsv/assignment/
 │   │   ├── model/              # Entity classes (14 tables)
 │   │   ├── dao/                # Repository interfaces
 │   │   ├── readerFiles/        # ExcelReader.java (Apache POI)
 │   │   ├── service/            # ExcelService.java (Data loading logic)
 │   │   ├── Application.java    # Main Spring Boot runner
 │   ├── resources/  
 │   │   ├── application.properties
 │   │   ├── schema.sql          # SQL for table creation
 │   │   ├── excelFiles/                   # Input Excel files for each entity
 │   │   ├── conditions/conditions.sql
README.md
```

---

## 4. Entities Implemented
1. School
2. Grade
3. Section
4. Subject
5. Teacher
6. Student
7. StudentAcademicMap
8. TeacherSectionMap
9. AttendanceRegister
10. Homework
11. ClassDiary
12. Fees
13. Salary
14. Timetable

---

## 5. Data Loading Process
- Data is loaded in a **dependency order** to satisfy foreign keys:
  ```
  School → Grade → Section → Subject → Teacher → Student  
  → AttendanceRegister → ClassDiary → Fees  
  → StudentAcademicMap → Homework → Salary → Timetable → TeacherSectionMap
  ```
- Each `.xlsx` file corresponds to an entity and follows the exact column order as the entity class.

---

## 6. Functional Rules Verification

| Condition | Query | Verified Result |
|-----------|-------|-----------------|
| Attendance ≥ 80% | ```sql SELECT student_id, COUNT(*) AS total_days, SUM(status='Present') AS present_days, (SUM(status='Present')/COUNT(*))*100 AS present_percentage FROM attendance_register GROUP BY student_id;``` | ✅ |
| 3 Homework entries per teacher (Pending, Submitted, Completed) | ```sql SELECT teacher_id, status, COUNT(*) FROM homework GROUP BY teacher_id, status;``` | ✅ |
| ≥ 2 Class Diary entries per teacher | ```sql SELECT teacher_id, COUNT(*) AS entry_count FROM class_diary GROUP BY teacher_id HAVING entry_count >= 2;``` | ✅ |
| ≥ 1 Fee payment per student | ```sql SELECT student_id, COUNT(*) AS fee_count FROM fees GROUP BY student_id HAVING fee_count >= 1;``` | ✅ |
| Salary for June & July | ```sql SELECT teacher_id, GROUP_CONCAT(month) AS months_paid FROM salary GROUP BY teacher_id;``` | ✅ |
| Lunch break each day for each section | ```sql SELECT section_id, day_of_week, COUNT(*) AS lunch_count FROM timetable WHERE period = 'Lunch Break' GROUP BY section_id, day_of_week HAVING lunch_count >= 1;``` | ✅ |
| ≥ 1 Free period per week per section | ```sql SELECT section_id, COUNT(*) AS free_periods FROM timetable WHERE period = 'Free' GROUP BY section_id HAVING free_periods >= 1;``` | ✅ |
| ≥ 1 Leave entry for a teacher | ```sql SELECT COUNT(*) FROM timetable WHERE period = 'Leave';``` | ✅ |

---

## 7. How to Run
1. Create MySQL database:
   ```sql
   CREATE DATABASE schoolOnboarding;
   ```
2. Import `create_tables.sql` once.
3. Place all Excel files in `sample_excels/`.
4. Update DB connection in `application.properties`.
5. Run the application:
   ```bash
   mvn spring-boot:run
   ```
6. The ExcelService will load data into DB.

---

## 8. Submission Folder Contents
```
sample_excels/
README.md
src/ (Java source code)
pom.xml
```