package com.dsv.assignment.Dao;

import com.dsv.assignment.model.AttendanceRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceDao extends JpaRepository<AttendanceRegister,Long> {
}
