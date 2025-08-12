package com.dsv.assignment.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class School {
    @Id
    private Long schoolId;

    private String schoolName;
    private String schoolType;
    private String contactNumber;
    private String address;

    @OneToMany(mappedBy = "school")
    private List<Grade> grades;

    @OneToMany(mappedBy = "school")
    private List<Subject> subjects;

    @OneToMany(mappedBy = "school")
    private List<Teacher> teachers;

    @OneToMany(mappedBy = "school")
    private List<Student> students;
}
