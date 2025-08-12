package com.dsv.assignment.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Teacher {
    @Id
    private Long teacherId;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    private String teacherName;
    private String gender;
    private String qualification;
    private String contactNumber;

    @OneToMany(mappedBy = "teacher")
    private List<TeacherSectionMap> teacherSectionMaps;
}
