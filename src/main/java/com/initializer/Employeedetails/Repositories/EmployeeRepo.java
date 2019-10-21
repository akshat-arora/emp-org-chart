package com.initializer.Employeedetails.Repositories;

import com.initializer.Employeedetails.Tables.Employee;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/*****Interface for Employee class*****/

public interface EmployeeRepo extends CrudRepository<Employee, Integer> {

   Employee findById(int id);
   String deleteById(String id);
   List<Employee> findAllByManagerId(int id,Sort sort);
   List<Employee> findAll(Sort sort);


}

