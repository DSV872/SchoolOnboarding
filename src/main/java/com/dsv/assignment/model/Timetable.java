package com.dsv.assignment.model;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Timetable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "section_id")
    private Section section;

    private String dayOfWeek; // "Monday", "Tuesday", etc.

    private String period; // "Period 1", "Lunch Break", etc.

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = true)
    private Teacher teacher; // Null if lunch break or free period
}
