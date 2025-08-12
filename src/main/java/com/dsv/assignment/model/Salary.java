package com.dsv.assignment.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    private String month; // "June", "July", etc.

    private Double amount;
}
