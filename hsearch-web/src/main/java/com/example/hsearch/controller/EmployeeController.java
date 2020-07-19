package com.example.hsearch.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.hsearch.model.Department;
import com.example.hsearch.model.Employee;
import com.example.hsearch.service.EmployeeService;

@RestController
public class EmployeeController {
	EmployeeService empSvc = new EmployeeService();
	@GetMapping("/createData")
	public String createData() {
		empSvc.insertData();
		return "Data Created";
	}

	@GetMapping("/getDepartment")
	public String getDepartment() {
		Department dept = empSvc.getDepartment();
		return dept.getName();
	}
	
	@GetMapping("/searchData")
	public String index(@RequestParam String searchString) {
		System.out.println("searchString::"+searchString);

		List<Department> departments2 = empSvc.searchData(searchString);
		System.out.println("list size::" + departments2.size());
		for (Department department : departments2) {
			System.out.println("Department Name:-" + department.getName());
			for (Employee employee : department.getEmployees()) {
				System.out.println("\tEmployee Name:- " + employee.getName());
			}
		}
		// return "Data Searched";
		return "Entries Found:";
	}
	
	@GetMapping("/addEmployee")
	public String addEmployee() {
		//EmployeeService empSvc = new EmployeeService();
		empSvc.addEmployee();
		return "Data Created";
	}
	
	@GetMapping("/addDepartment")
	public String addDepartment() {
		//EmployeeService empSvc = new EmployeeService();
		empSvc.insertData();
		return "Data Created";
	}

}
