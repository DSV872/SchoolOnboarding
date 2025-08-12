package com.dsv.assignment.Dao;

import com.dsv.assignment.model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GradeDao extends JpaRepository<Grade,Long> {
}
