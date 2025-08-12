package com.dsv.assignment.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Grade {
    @Id
    private Long gradeId;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

    private String gradeName;
    private Integer displayOrder;
    private Boolean isActive;

    @OneToMany(mappedBy = "grade")
    private List<Section> sections;
}
