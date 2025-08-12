package com.dsv.assignment.service;

import com.dsv.assignment.Dao.*;
import com.dsv.assignment.model.*;
import com.dsv.assignment.readerFiles.ExcelReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelService {

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

    public void  run() throws IOException {
        //reading and saving schools
        var schools = excelReader.readSchools("/home/dsv/schools.xlsx");
        schoolDao.saveAll(schools);
        var schoolMap = excelReader.toMap(schools, School::getSchoolId);

        //reading and saving grades
        var grades = excelReader.readGrades("/home/dsv/grades.xlsx",schoolMap);
        gradeDao.saveAll(grades);
        var gradeMap = excelReader.toMap(grades, Grade::getGradeId);

        //reading and saving Sections
        var sections = excelReader.readSections("/home/dsv/sections.xlsx",gradeMap);
        sectionDao.saveAll(sections);
        var sectionMap = excelReader.toMap(sections, Section::getSectionId);

        //reading and saving Subjects
        var subjects = excelReader.readSubjects("/home/dsv/subjects.xlsx",schoolMap);
        subjectDao.saveAll(subjects);
        var subjectMap = excelReader.toMap(subjects, Subject::getSubjectId);

        //reading and saving Teachers
        var teachers = excelReader.readTeachers("/home/dsv/teachers.xlsx",schoolMap);
        teacherDao.saveAll(teachers);
        var teacherMap = excelReader.toMap(teachers,Teacher::getTeacherId);

        //reading and saving Students
        var students = excelReader.readStudents("/home/dsv/students.xlsx",schoolMap);
        studentDao.saveAll(students);
        var studentMap = excelReader.toMap(students,Student::getStudentId);

        //reading and saving studentAcademic map
        var studentAcademicMaps = excelReader.readStudentAcademicMap("/home/dsv/student_academic_map.xlsx",studentMap,gradeMap,sectionMap);
        studentAcademicMapDao.saveAll(studentAcademicMaps);


        //reading and saving teacheracademic map
        var teacherAcademicMaps = excelReader.readTeacherSectionMap("/home/dsv/teacher_section_map.xlsx",teacherMap,sectionMap,subjectMap);
        teacherSectionMapDao.saveAll(teacherAcademicMaps);

    }
}
