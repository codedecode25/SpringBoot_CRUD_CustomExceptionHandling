package com.codedecode.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codedecode.demo.custom.exception.BusinessException;
import com.codedecode.demo.entity.Employee;
import com.codedecode.demo.repos.EmployeeCrudRepo;

@Service
public class EmployeeService implements EmployeeServiceInterface{
	
	@Autowired
	private EmployeeCrudRepo crudRepo;

	@Override
	public Employee addEmployee(Employee employee) {

		if(employee.getName().isEmpty() || employee.getName().length() == 0 ) {
			throw new BusinessException("601","Please send proper name, It blank");
		}
		try {
			Employee savedEmployee = crudRepo.save(employee);
			return savedEmployee;
		}catch (IllegalArgumentException e) {
			throw new BusinessException("602","given employee is null" + e.getMessage());
		}catch (Exception e) {
			throw new BusinessException("603","Something went wrong in Service layer while saving the employee" + e.getMessage());
		}


	}

	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> empList = null;
		try {
			empList = crudRepo.findAll();
		}
		catch (Exception e) {
			throw new BusinessException("605","Something went wrong in Service layer while fetching all employees" + e.getMessage());
		}
		if(empList.isEmpty())
			throw new BusinessException("604", "Hey list completely empty, we have nothing to return");
		return empList;
	}
		
	

	@Override
	public Employee getEmpById(Long empidL)  {
		try {
			return crudRepo.findById(empidL).get();
			
		}catch (IllegalArgumentException e) {
			throw new BusinessException("606","given employee id is null, please send some id to be searched" + e.getMessage());
		}
		catch (java.util.NoSuchElementException e) {
			throw new BusinessException("607","given employee id doesnot exist in DB" + e.getMessage());
		}catch (Exception e) {
			throw new BusinessException("609","Something went wrong in Service layer while fetching all employees" + e.getMessage());
		}
		
	}

	@Override
	public void deleteEmpById(Long empidL) {
		try {
			crudRepo.deleteById(empidL);
		}catch (IllegalArgumentException e) {
			throw new BusinessException("608","given employee id is null, please send some id to be deleted" + e.getMessage());
		}catch (Exception e) {
			throw new BusinessException("610","Something went wrong in Service layer while fetching all employees" + e.getMessage());
		}
		
	}

}
