package com.example.hsearch.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.example.hsearch.model.Department;
import com.example.hsearch.model.Employee;

public class EmployeeService {

	public void insertData() {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();

			Department department1 = new Department();
			department1.setName("IT Department");
			Department department2 = new Department();
			department2.setName("HR Department");

			Employee employee1 = new Employee();
			employee1.setName("Robin Edward");
			employee1.setDesignation("Manager");
			employee1.setDepartment(department1);

			Employee employee2 = new Employee();
			employee2.setName("Vivian Jackman");
			employee2.setDesignation("Accountant");
			employee2.setDepartment(department1);

			Employee employee3 = new Employee();
			employee3.setName("Eliza Edward");
			employee3.setDesignation("Software Engineer");
			employee3.setDepartment(department2);

			Employee employee4 = new Employee();
			employee4.setName("Nancy Newman");
			employee4.setDesignation("Senior Software Engineer");
			employee4.setDepartment(department2);

			department1.getEmployees().add(employee1);
			department1.getEmployees().add(employee2);

			department2.getEmployees().add(employee3);
			department2.getEmployees().add(employee4);

			session.persist(department1);
			session.persist(department2);

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public Department getDepartment() {
		Session session = null;
		Transaction transaction = null;
		Department department = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();

			department = (Department) session.get(Department.class, new Long(2));
            System.out.println(department.getId() + " - " + department.getName());

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		return department;
	}
	
	public void addEmployee() {
		Session session = null;
		Transaction transaction = null;
		try {
			System.out.println("inside addEmployee:::");
			System.out.println("factoryAdd::"+HibernateUtil.getSessionFactory());
			session = HibernateUtil.getSessionFactory().openSession();
			System.out.println("sessionAdd::"+session);
			transaction = session.getTransaction();
			transaction.begin();

			Department department = new Department();
			department.setName("Dept1");
			
			Employee employee = new Employee();
			employee.setName("Emp1 Sur1");
			employee.setDesignation("Senior Software Engineer");
			employee.setDepartment(department);

			department.getEmployees().add(employee);
			session.persist(department);

			//department.getEmployees().add(employee);

			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	public List<Department> searchData(String text) {
		List<Department> departments = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();

			FullTextSession fullTextSession = Search.getFullTextSession(session);
			fullTextSession.createIndexer().startAndWait();

			QueryBuilder qb = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(Department.class).get();

			// Create lucene query
			// Set indexed field
			org.apache.lucene.search.Query lucenceQuery = qb.keyword().onFields("name", "employees.name").matching(text)
					.createQuery();

			org.apache.lucene.search.Query fuzzyQuery = qb.keyword().fuzzy().withEditDistanceUpTo(2).withPrefixLength(0)
					.onField("name").matching("employees.name").createQuery();

			// Warp lucene query in org.hibernate.query.Query
			@SuppressWarnings("unchecked")
			Query<Department> query = fullTextSession.createFullTextQuery(lucenceQuery, Department.class);
			departments = query.getResultList();

			transaction.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction != null) {
				transaction.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return departments;

	}

}
