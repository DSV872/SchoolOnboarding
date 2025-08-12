package com.dsv.assignment.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class StudentAcademicMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    private String academicYear;
}
