package com.spring.boot.crud.service.impl;

import com.spring.boot.crud.entity.Employee;
import com.spring.boot.crud.repository.EmployeeRepository;
import com.spring.boot.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return this.employeeRepository.findAll();
    }

    @Override
    public void saveEmployee(Employee employee) {
        this.employeeRepository.save(employee);
    }

    @Override
    public Employee getEmployeeById(Long employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        Employee employee = null;
        if (optionalEmployee.isPresent())
            employee = optionalEmployee.get();
        else
            throw new RuntimeException("Employee nor found. Id: " + employeeId);
        return employee;
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return this.employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployeeById(Long employeeId) {
        this.employeeRepository.deleteById(employeeId);
    }

    @Override
    public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return this.employeeRepository.findAll(pageable);
    }
}

