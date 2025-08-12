package com.dsv.assignment.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class TeacherSectionMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
}
