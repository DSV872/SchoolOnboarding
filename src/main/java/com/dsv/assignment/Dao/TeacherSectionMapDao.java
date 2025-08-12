package com.dsv.assignment.Dao;

import com.dsv.assignment.model.TeacherSectionMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherSectionMapDao extends JpaRepository<TeacherSectionMap,Long> {
}
