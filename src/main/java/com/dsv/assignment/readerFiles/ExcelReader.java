package com.dsv.assignment.readerFiles;

import com.dsv.assignment.model.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;

@Component
public class ExcelReader {

    //SCHOOL
    public List<School> readSchools(String filepath) throws IOException {
        List<School> schools = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filepath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                School school = new School();
                school.setSchoolId((long) row.getCell(0).getNumericCellValue());
                school.setSchoolName(row.getCell(1).getStringCellValue());
                school.setSchoolType(row.getCell(2).getStringCellValue());
                school.setContactNumber(row.getCell(3).getStringCellValue());
                school.setAddress(row.getCell(4).getStringCellValue());
                schools.add(school);
            }
        }
        return schools;
    }

    //GRADE
    public List<Grade> readGrades(String filePath, Map<Long, School> schoolMap) throws IOException {
        List<Grade> grades = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Grade grade = new Grade();
                grade.setGradeId((long) row.getCell(0).getNumericCellValue());

                Long schoolId = (long) row.getCell(1).getNumericCellValue();
                grade.setSchool(schoolMap.get(schoolId));

                grade.setGradeName(row.getCell(2).getStringCellValue());
                grade.setDisplayOrder((int) row.getCell(3).getNumericCellValue());
                grade.setIsActive(row.getCell(4).getBooleanCellValue());

                grades.add(grade);
            }
        }
        return grades;
    }

    //SECTION
    public List<Section> readSections(String filePath, Map<Long, Grade> gradeMap) throws IOException {
        List<Section> sections = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Section section = new Section();
                section.setSectionId((long) row.getCell(0).getNumericCellValue());

                Long gradeId = (long) row.getCell(1).getNumericCellValue();
                section.setGrade(gradeMap.get(gradeId));

                section.setSectionName(row.getCell(2).getStringCellValue());
                section.setIsActive(row.getCell(3).getBooleanCellValue());

                sections.add(section);
            }
        }
        return sections;
    }

    //SUBJECT
    public List<Subject> readSubjects(String filePath, Map<Long, School> schoolMap) throws IOException {
        List<Subject> subjects = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Subject subject = new Subject();
                subject.setSubjectId((long) row.getCell(0).getNumericCellValue());

                Long schoolId = (long) row.getCell(1).getNumericCellValue();
                subject.setSchool(schoolMap.get(schoolId));

                subject.setSubjectName(row.getCell(2).getStringCellValue());
                subject.setIsActive(row.getCell(3).getBooleanCellValue());

                subjects.add(subject);
            }
        }
        return subjects;
    }

    //TEACHER
    public List<Teacher> readTeachers(String filePath, Map<Long, School> schoolMap) throws IOException {
        List<Teacher> teachers = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Teacher teacher = new Teacher();
                teacher.setTeacherId((long) row.getCell(0).getNumericCellValue());

                Long schoolId = (long) row.getCell(1).getNumericCellValue();
                teacher.setSchool(schoolMap.get(schoolId));

                teacher.setTeacherName(row.getCell(2).getStringCellValue());
                teacher.setGender(row.getCell(3).getStringCellValue());
                teacher.setQualification(row.getCell(4).getStringCellValue());
                teacher.setContactNumber(row.getCell(5).getStringCellValue());

                teachers.add(teacher);
            }
        }
        return teachers;
    }

    //STUDENT
    public List<Student> readStudents(String filePath, Map<Long, School> schoolMap) throws IOException {
        List<Student> students = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Student student = new Student();
                student.setStudentId((long) row.getCell(0).getNumericCellValue());

                Long schoolId = (long) row.getCell(1).getNumericCellValue();
                student.setSchool(schoolMap.get(schoolId));

                student.setStudentName(row.getCell(2).getStringCellValue());

                Cell cell = row.getCell(3);
                if(cell != null){
                    if(cell.getCellType() == CellType.NUMERIC){
                        if(DateUtil.isCellDateFormatted(cell)){
                            student.setDob(cell.getLocalDateTimeCellValue().toLocalDate());
                        }
                        else {
                            throw new IllegalArgumentException("Invalid date format in Excel");
                        }
                    }
                    else if (cell.getCellType()==CellType.STRING){
                        String dateStr = cell.getStringCellValue().trim();
                        LocalDate dob = LocalDate.parse(dateStr,DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        student.setDob(dob);
                    }
                    else {
                        throw  new IllegalArgumentException("Unsupported cell type for DOB");
                    }
                }
//                String text =  row.getCell(3).getStringCellValue();
//                System.out.println(text);

                student.setGender(row.getCell(4).getStringCellValue());


                students.add(student);
            }
        }
        return students;
    }

    //Homework
    public List<Homework> readHomework(String filePath, Map<Long,Teacher> teacherMap) throws IOException{
        List<Homework> homeworks = new ArrayList<>();
        try(FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis)){
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum();i++){
                Row row = sheet.getRow(i);
                Homework homework = new Homework();

                Long teacherId = (long)row.getCell(0).getNumericCellValue();
                homework.setTeacher(teacherMap.get(teacherId));

                homework.setStatus(row.getCell(1).getStringCellValue());

                Cell cell = row.getCell(2);
                if (cell != null){
                    if (cell.getCellType() == CellType.NUMERIC){
                        if(DateUtil.isCellDateFormatted(cell)){
                            homework.setAssignedDate(cell.getLocalDateTimeCellValue().toLocalDate());
                        }
                        else {
                            throw new IllegalArgumentException("Invalid date format in Excel");
                        }
                    } else if (cell.getCellType() == CellType.STRING) {
                        String dateStr = cell.getStringCellValue().trim();
                        LocalDate assignedDate = LocalDate.parse(dateStr,DateTimeFormatter.ofPattern("dd/MM/yyy"));
                        homework.setAssignedDate(assignedDate);
                    }else {
                        throw new IllegalArgumentException("Unsupported cell type for assignedDate");
                    }
                }
                homework.setDescription(row.getCell(3).getStringCellValue());

                homeworks.add(homework);
            }
        }
        return homeworks;
    }

    //SALARY
    public List<Salary> readSalary(String filePath, Map<Long,Teacher> teacherMap) throws IOException {
        List<Salary> salaries = new ArrayList<>();
        try(FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis)){
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum();i++){
                Row row = sheet.getRow(i);
                Salary salary = new Salary();
                Long id = (long)row.getCell(0).getNumericCellValue();
                salary.setTeacher(teacherMap.get(id));
                salary.setMonth(row.getCell(1).getStringCellValue());
                salary.setAmount(row.getCell(2).getNumericCellValue());

                salaries.add(salary);
            }
        }
        return salaries;
    }

    //CLASSDIARY
    public List<ClassDiary> readClassDiary(String filePath,Map<Long,Teacher> teacherMap) throws IOException {
        List<ClassDiary> classDiaries = new ArrayList<>();
        try(FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis)){
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i<=sheet.getLastRowNum();i++){
                Row row = sheet.getRow(i);
                ClassDiary classDiary = new ClassDiary();
                Long id =(long) row.getCell(0).getNumericCellValue();
                classDiary.setTeacher(teacherMap.get(id));
                Cell cell = row.getCell(1);
                if (cell != null){
                    if (cell.getCellType() == CellType.NUMERIC){
                        if(DateUtil.isCellDateFormatted(cell)){
                            classDiary.setDate(cell.getLocalDateTimeCellValue().toLocalDate());
                        }
                        else {
                            throw new IllegalArgumentException("Invalid date format in Excel");
                        }
                    } else if (cell.getCellType() == CellType.STRING) {
                        String dateStr = cell.getStringCellValue().trim();
                        LocalDate date = LocalDate.parse(dateStr,DateTimeFormatter.ofPattern("dd/MM/yyy"));
                        classDiary.setDate(date);
                    }else {
                        throw new IllegalArgumentException("Unsupported cell type for assignedDate");
                    }
                }
                classDiary.setNotes(row.getCell(2).getStringCellValue());

                classDiaries.add(classDiary);
            }
        }
        return classDiaries;
    }

    //FEES
    public List<Fees> readFees(String filePath,Map<Long,Student> studentMap) throws IOException {
        List<Fees> fees = new ArrayList<>();
        try(FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fis)){
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i<=sheet.getLastRowNum();i++){
                Row row = sheet.getRow(i);
                Fees fee = new Fees();

                Long id =(long) row.getCell(0).getNumericCellValue();
                fee.setStudent(studentMap.get(id));

                Cell cell = row.getCell(1);
                if (cell != null){
                    if (cell.getCellType() == CellType.NUMERIC){
                        if(DateUtil.isCellDateFormatted(cell)){
                            fee.setDate(cell.getLocalDateTimeCellValue().toLocalDate());
                        }
                        else {
                            throw new IllegalArgumentException("Invalid date format in Excel");
                        }
                    } else if (cell.getCellType() == CellType.STRING) {
                        String dateStr = cell.getStringCellValue().trim();
                        LocalDate Date = LocalDate.parse(dateStr,DateTimeFormatter.ofPattern("dd/MM/yyy"));
                        fee.setDate(Date);
                    }else {
                        throw new IllegalArgumentException("Unsupported cell type for assignedDate");
                    }
                }

                fee.setAmount(row.getCell(2).getNumericCellValue());

                fees.add(fee);
            }
        }
        return fees;


    }

    //TIMETABLE
    public List<Timetable> readTimetable(String filePath,Map<Long,Teacher> teacherMap,Map<Long,Section> sectionMap) throws IOException {
        List<Timetable> timetables = new ArrayList<>();
        try(FileInputStream fis = new FileInputStream(filePath);
            Workbook workbook = new XSSFWorkbook(fis)){
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i<=sheet.getLastRowNum();i++){
                Row row = sheet.getRow(i);
                Timetable timetable = new Timetable();
                Long id = (long)row.getCell(0).getNumericCellValue();
                timetable.setSection(sectionMap.get(id));
                timetable.setDayOfWeek(row.getCell(1).getStringCellValue());
                timetable.setPeriod(row.getCell(2).getStringCellValue());
                Cell cell = row.getCell(3);
                Long id2 = null;
                if(cell != null && cell.getCellType()==CellType.NUMERIC){
                    id2 =(long) cell.getNumericCellValue();
                    timetable.setTeacher(teacherMap.get(id2));
                }
                timetables.add(timetable);
            }
        }
        return timetables;
    }

    //AttendanceRegister
    public List<AttendanceRegister> readAttendanceRegister(String filePath,Map<Long,Student> studentMap) throws IOException{
        List<AttendanceRegister> attendanceRegisters = new ArrayList<>();
        try(FileInputStream fis = new FileInputStream(filePath);
        Workbook workbook = new XSSFWorkbook(fis)){
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1  ; i <= sheet.getLastRowNum();i++){
                Row row = sheet.getRow(i);
                AttendanceRegister attendanceRegister = new AttendanceRegister();

                Long studentId = (long)row.getCell(0).getNumericCellValue();
                attendanceRegister.setStudent(studentMap.get(studentId));

                Cell cell = row.getCell(1);
                if(cell != null){
                    if(cell.getCellType() == CellType.NUMERIC){
                        if(DateUtil.isCellDateFormatted(cell)){
                            attendanceRegister.setDate(cell.getLocalDateTimeCellValue().toLocalDate());
                        }
                        else {
                            throw new IllegalArgumentException("Invalid date format in Excel");
                        }
                    }
                    else if(cell.getCellType() == CellType.STRING){
                        String dateStr = cell.getStringCellValue().trim();
                        LocalDate date= LocalDate.parse(dateStr,DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        attendanceRegister.setDate(date);
                    }
                    else {
                        throw new IllegalArgumentException("Unsupported cell type for date");
                    }
                }
                attendanceRegister.setStatus(row.getCell(2).getStringCellValue());

                attendanceRegisters.add(attendanceRegister);
            }
        }
        return attendanceRegisters;
    }
    //STUDENT ACADEMIC MAP
    public List<StudentAcademicMap> readStudentAcademicMap(String filePath,
                                                           Map<Long, Student> studentMap,
                                                           Map<Long, Grade> gradeMap,
                                                           Map<Long, Section> sectionMap) throws IOException {
        List<StudentAcademicMap> mappings = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                StudentAcademicMap map = new StudentAcademicMap();

                map.setStudent(studentMap.get((long) row.getCell(0).getNumericCellValue()));
                map.setGrade(gradeMap.get((long) row.getCell(1).getNumericCellValue()));
                map.setSection(sectionMap.get((long) row.getCell(2).getNumericCellValue()));
                map.setAcademicYear(row.getCell(3).getStringCellValue());

                mappings.add(map);
            }
        }
        return mappings;
    }

    //TEACHER SECTION MAP
    public List<TeacherSectionMap> readTeacherSectionMap(String filePath,
                                                         Map<Long, Teacher> teacherMap,
                                                         Map<Long, Section> sectionMap,
                                                         Map<Long, Subject> subjectMap) throws IOException {
        List<TeacherSectionMap> mappings = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                TeacherSectionMap map = new TeacherSectionMap();

                map.setTeacher(teacherMap.get((long) row.getCell(0).getNumericCellValue()));
                map.setSection(sectionMap.get((long) row.getCell(1).getNumericCellValue()));
                map.setSubject(subjectMap.get((long) row.getCell(2).getNumericCellValue()));

                mappings.add(map);
            }
        }
        return mappings;
    }


    //Build map from list
    public <T> Map<Long, T> toMap(List<T> list, Function<T, Long> keyExtractor) {
        return list.stream().collect(Collectors.toMap(keyExtractor, t -> t));
    }
}
