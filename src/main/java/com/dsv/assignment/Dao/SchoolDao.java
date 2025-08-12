package com.dsv.assignment.Dao;

import com.dsv.assignment.model.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolDao extends JpaRepository<School,Long> {
}
