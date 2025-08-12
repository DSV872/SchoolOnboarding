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

        //reading and saving studentAcademic map
        var studentAcademicMaps = excelReader.readStudentAcademicMap(BASE_PATH+"student_academic_map.xlsx",studentMap,gradeMap,sectionMap);
        studentAcademicMapDao.saveAll(studentAcademicMaps);


        //reading and saving teacheracademic map
        var teacherAcademicMaps = excelReader.readTeacherSectionMap(BASE_PATH+"teacher_section_map.xlsx",teacherMap,sectionMap,subjectMap);
        teacherSectionMapDao.saveAll(teacherAcademicMaps);

        generateAttendance(students);
    }
    private void generateAttendance(List<Student> students){
        List<AttendanceRegister> attendanceList = new ArrayList<>();
        LocalDate startDate = LocalDate.of(2024,6,1);
        LocalDate endDate = LocalDate.of(2024,6,30);

        for(Student student : students){
            List<LocalDate> dates = startDate.datesUntil(endDate.plusDays(1)).toList();

            //Ensure at least 80% present
            int totalDays = dates.size();
            int presentDays = (int) Math.ceil(totalDays*0.8);

            //shuffle dates so presence/absence is randome
            Collections.shuffle(dates);

            for (int i = 0; i < dates.size();i++){
                AttendanceRegister record = new AttendanceRegister();
                record.setStudent(student);
                record.setDate(dates.get(i));
                record.setStatus(i < presentDays ? "Present" : "Absent");
                attendanceList.add(record);
            }
        }
        attendanceDao.saveAll(attendanceList);

    }
}
