package com.dsv.assignment.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Student {
    @Id
    private Long studentId;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    private String studentName;
    @Temporal(TemporalType.DATE)
    private Date dob;
    private String gender;

    @OneToMany(mappedBy = "student")
    private List<StudentAcademicMap> studentAcademicMaps;
}
