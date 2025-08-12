package com.dsv.assignment.Dao;

import com.dsv.assignment.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectDao extends JpaRepository<Subject, Long> {
}
