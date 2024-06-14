package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Address;
import com.example.demo.entity.Student;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public interface StudentRepository extends JpaRepository<Student, Integer> {

	List<Student> findByNameContainingIgnoreCase(String name);

	List<Student> findByDesignationIgnoreCase(String designation);
    
}
