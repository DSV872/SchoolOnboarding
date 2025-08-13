package com.dsv.assignment.Dao;

import com.dsv.assignment.model.ClassDiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassDiaryDao extends JpaRepository<ClassDiary,Long> {
}
