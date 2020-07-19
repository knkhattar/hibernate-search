package com.example.hsearch.service;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import com.example.hsearch.model.Department;
import com.example.hsearch.model.Employee;

public class HibernateUtil {

	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

				Map<String, Object> settings = new HashMap<>();
				settings.put(Environment.DRIVER, "org.h2.Driver");
				settings.put(Environment.URL, "jdbc:h2:tcp://localhost/~/test");
				settings.put(Environment.USER, "sa");
				settings.put(Environment.PASS, "");
				settings.put(Environment.HBM2DDL_AUTO, "update");
				settings.put(Environment.SHOW_SQL, true);

				settings.put("hibernate.search.default.directory_provider", "infinispan");
				settings.put("hibernate.search.default.indexwriter.infostream", true);

				registryBuilder.applySettings(settings);

				registry = registryBuilder.build();
				MetadataSources sources = new MetadataSources(registry).addAnnotatedClass(Employee.class)
						.addAnnotatedClass(Department.class);

				Metadata metadata = sources.getMetadataBuilder().build();
				sessionFactory = metadata.getSessionFactoryBuilder().build();
			} catch (Exception e) {
				if (registry != null) {
					StandardServiceRegistryBuilder.destroy(registry);
				}
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}

	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
}
