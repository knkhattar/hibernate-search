package com.example.hsearch.config;

import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;
import static org.hibernate.cfg.AvailableSettings.SHOW_SQL;
import static org.hibernate.cfg.AvailableSettings.DIALECT;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScans(value = { @ComponentScan("com.example.hsearch.dao"), @ComponentScan("com.example.hsearch.service") })
public class AppConfig {

	@Value( "${db.driver}" )
	private  String DRIVER_CLASS_NAME;
	@Value( "${db.url}" )
	private   String URL;
	@Value( "${db.user}" )
	private   String USER;
	@Value( "${db.password}" )
	private   String PASS;
	
	@Autowired
	private Environment env;

	@Bean
	public LocalSessionFactoryBean getSessionFactory() {
		LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

		Properties props = new Properties();

		props.put(SHOW_SQL, env.getProperty("hibernate.show_sql"));
		props.put(HBM2DDL_AUTO, env.getProperty("hibernate.hbm2ddl.auto"));
		props.put(DIALECT, env.getProperty("hibernate.dialect"));
		props.put("hibernate.search.default.indexmanager", "elasticsearch");
		props.put("hibernate.search.default.elasticsearch.host", "http://127.0.0.1:9200");
		props.put("hibernate.search.default.elasticsearch.index_schema_management_strategy", "CREATE");
		props.put("hibernate.search.default.elasticsearch.required_index_status", "yellow");
		factoryBean.setHibernateProperties(props);
		factoryBean.setPackagesToScan("com.example.hsearch.model");
		factoryBean.setDataSource(getDataSource());

		return factoryBean;
	}

	@Bean
	public HibernateTransactionManager getTransactionManager() {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		transactionManager.setSessionFactory(getSessionFactory().getObject());
		return transactionManager;
	}

	public  DataSource getDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(this.DRIVER_CLASS_NAME);
		dataSource.setUrl(this.URL);
		dataSource.setUsername(this.USER);
		dataSource.setPassword(this.PASS);
		return dataSource;
	}

}
