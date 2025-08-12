package com.dsv.assignment.Dao;

import com.dsv.assignment.model.Section;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionDao extends JpaRepository<Section,Long> {
}
