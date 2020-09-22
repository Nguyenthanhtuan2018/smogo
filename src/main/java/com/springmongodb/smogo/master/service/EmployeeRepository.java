package com.springmongodb.smogo.master.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.springmongodb.smogo.master.entity.Employee;

// This is an Interface.
// No need Annotation here
public interface EmployeeRepository extends MongoRepository<Employee, Long> { // Long: Type of Employee ID.

    Employee findByEmployeeNo(String employeeNo);

    List<Employee> findByNameLike(String name);

    List<Employee> findByHireDateGreaterThan(Date hireDate);

    // Supports native JSON query string
    @Query("{name:'?0'}")
    List<Employee> findCustomByName(String name);

}
