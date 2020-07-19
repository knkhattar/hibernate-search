package com.example.hsearch.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.hsearch.model.Department;
import com.example.hsearch.model.Employee;
import com.example.hsearch.service.EmployeeService;

@RestController
public class EmployeeController {

	@GetMapping("/createData")
	public String createData() {
		EmployeeService empSvc = new EmployeeService();
		empSvc.insertData();
		return "Data Created";
	}

	@GetMapping("/searchData")
	public String index() {

		EmployeeService empSvc = new EmployeeService();
		List<Department> departments2 = empSvc.searchData("Nancy");
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

}
