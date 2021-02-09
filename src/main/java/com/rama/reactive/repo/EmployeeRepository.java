package com.rama.reactive.repo;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.rama.reactive.model.Employee;

@Repository
public interface EmployeeRepository extends ReactiveMongoRepository<Employee, String>{

}
