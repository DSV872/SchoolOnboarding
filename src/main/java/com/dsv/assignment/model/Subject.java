package com.dsv.assignment.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Subject {
    @Id
    private Long subjectId;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    private String subjectName;
    private Boolean isActive;

    @OneToMany(mappedBy = "subject")
    private List<TeacherSectionMap> teacherSectionMaps;
}
