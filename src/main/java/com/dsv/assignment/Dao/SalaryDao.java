package com.dsv.assignment.Dao;

import com.dsv.assignment.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalaryDao extends JpaRepository<Salary,Long> {
}
