package com.dsv.assignment.service;

import com.dsv.assignment.Dao.*;
import com.dsv.assignment.model.*;
import com.dsv.assignment.readerFiles.ExcelReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExcelService {
    private static final String BASE_PATH = "/home/dsv/";

    @Autowired
    private ExcelReader excelReader;
    @Autowired
    private SchoolDao schoolDao;
    @Autowired
    private StudentDao studentDao;
    @Autowired
    private TeacherDao teacherDao;
    @Autowired
    private SubjectDao subjectDao;
    @Autowired
    private GradeDao gradeDao;
    @Autowired
    private SectionDao sectionDao;
    @Autowired
    private StudentAcademicMapDao studentAcademicMapDao;
    @Autowired
    private TeacherSectionMapDao teacherSectionMapDao;
    @Autowired
    private AttendanceDao attendanceDao;
    @Autowired
    private ClassDiaryDao classDiaryDao;
    @Autowired
    private FeesDao feesDao;
    @Autowired
    private HomeworkDao homeworkDao;
    @Autowired
    private SalaryDao salaryDao;
    @Autowired
    private TimetableDao timetableDao;

    public void  run() throws IOException {
        //reading and saving schools
        var schools = excelReader.readSchools(BASE_PATH+"schools.xlsx");
        schoolDao.saveAll(schools);
        var schoolMap = excelReader.toMap(schools, School::getSchoolId);

        //reading and saving grades
        var grades = excelReader.readGrades(BASE_PATH+"grades.xlsx",schoolMap);
        gradeDao.saveAll(grades);
        var gradeMap = excelReader.toMap(grades, Grade::getGradeId);

        //reading and saving Sections
        var sections = excelReader.readSections(BASE_PATH+"sections.xlsx",gradeMap);
        sectionDao.saveAll(sections);
        var sectionMap = excelReader.toMap(sections, Section::getSectionId);

        //reading and saving Subjects
        var subjects = excelReader.readSubjects(BASE_PATH+"subjects.xlsx",schoolMap);
        subjectDao.saveAll(subjects);
        var subjectMap = excelReader.toMap(subjects, Subject::getSubjectId);

        //reading and saving Teachers
        var teachers = excelReader.readTeachers(BASE_PATH+"teachers.xlsx",schoolMap);
        teacherDao.saveAll(teachers);
        var teacherMap = excelReader.toMap(teachers,Teacher::getTeacherId);

        //reading and saving Students
        var students = excelReader.readStudents(BASE_PATH+"students.xlsx",schoolMap);
        studentDao.saveAll(students);
        var studentMap = excelReader.toMap(students,Student::getStudentId);

        //reading and saving AttendanceRegister
        var attendanceData = excelReader.readAttendanceRegister(BASE_PATH+"attendance.xlsx",studentMap);
        attendanceDao.saveAll(attendanceData);

        //reading and saving classDiary
        var classDiaryData = excelReader.readClassDiary(BASE_PATH+"class_diary.xlsx", teacherMap);
        classDiaryDao.saveAll(classDiaryData);

        //reading and saving fees
        var feesData = excelReader.readFees(BASE_PATH+"fees.xlsx",studentMap);
        feesDao.saveAll(feesData);

        //reading and saving studentAcademic map
        var studentAcademicMaps = excelReader.readStudentAcademicMap(BASE_PATH+"student_academic_map.xlsx",studentMap,gradeMap,sectionMap);
        studentAcademicMapDao.saveAll(studentAcademicMaps);

        //reading and saving homework data
        var homeworkData = excelReader.readHomework(BASE_PATH+"homework.xlsx",teacherMap);
        homeworkDao.saveAll(homeworkData);

        //reading and saving salary data
        var salaryData = excelReader.readSalary(BASE_PATH+"salary.xlsx",teacherMap);
        salaryDao.saveAll(salaryData);

        //reading and saving timetable data
        var timetableData = excelReader.readTimetable(BASE_PATH+"timetable.xlsx",teacherMap,sectionMap);
        timetableDao.saveAll(timetableData);

        //reading and saving teachersection map
        var teacherSectionMaps = excelReader.readTeacherSectionMap(BASE_PATH+"teacher_section_map.xlsx",teacherMap,sectionMap,subjectMap);
        teacherSectionMapDao.saveAll(teacherSectionMaps);

    }
}
