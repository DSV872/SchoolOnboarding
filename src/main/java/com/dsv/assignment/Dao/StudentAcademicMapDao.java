package com.dsv.assignment.Dao;

import com.dsv.assignment.model.StudentAcademicMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentAcademicMapDao extends JpaRepository<StudentAcademicMap,Long> {
}
