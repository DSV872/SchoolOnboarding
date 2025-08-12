package com.dsv.assignment.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Section {
    @Id
    private Long sectionId;

    @ManyToOne
    @JoinColumn(name = "grade_id")
    private Grade grade;

    private String sectionName;
    private Boolean isActive;

    @OneToMany(mappedBy = "section")
    private List<StudentAcademicMap> studentAcademicMaps;

    @OneToMany(mappedBy = "section")
    private List<TeacherSectionMap> teacherSectionMaps;
}
