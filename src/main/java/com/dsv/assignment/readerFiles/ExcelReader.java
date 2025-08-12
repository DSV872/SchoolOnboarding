package com.dsv.assignment.readerFiles;

import com.dsv.assignment.model.*;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ExcelReader {

    // -----------------------
    // 1. SCHOOL
    // -----------------------
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

    // -----------------------
    // 2. GRADE
    // -----------------------
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

    // -----------------------
    // 3. SECTION
    // -----------------------
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

    // -----------------------
    // 4. SUBJECT
    // -----------------------
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

    // -----------------------
    // 5. TEACHER
    // -----------------------
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

    // -----------------------
    // 6. STUDENT
    // -----------------------
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
                student.setDob(row.getCell(3).getDateCellValue());
                student.setGender(row.getCell(4).getStringCellValue());

                students.add(student);
            }
        }
        return students;
    }

    // -----------------------
    // 7. STUDENT ACADEMIC MAP
    // -----------------------
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

    // -----------------------
    // 8. TEACHER SECTION MAP
    // -----------------------
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

    // Utility: Build map from list
    public <T> Map<Long, T> toMap(List<T> list, java.util.function.Function<T, Long> keyExtractor) {
        return list.stream().collect(Collectors.toMap(keyExtractor, t -> t));
    }
}
