package com.dsv.assignment.Dao;

import com.dsv.assignment.model.Timetable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimetableDao extends JpaRepository<Timetable,Long> {
}
