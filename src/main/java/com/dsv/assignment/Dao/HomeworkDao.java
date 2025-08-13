package com.dsv.assignment.Dao;

import com.dsv.assignment.model.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HomeworkDao extends JpaRepository<Homework, Long> {
}
