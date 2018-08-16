package com.musicshop.configuration;

import java.util.Properties;

import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableTransactionManagement
@EnableWebMvc
@ComponentScan("com.musicshop")
@PropertySource({ "classpath:connections.properties" })
public class RootContextConfiguration implements WebMvcConfigurer{

	@Autowired
	private Environment environment;
	
	@Bean(name = "dataSource")
	public DataSource getOracleDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(environment.getProperty("oracle.jdbc.OracleDriver"));
		String dbUsername = environment.getProperty("database.username");
		String dbPassword = environment.getProperty("database.password");
		String dbHost = environment.getProperty("database.host");
		String dbPort = environment.getProperty("database.port");
		dataSource.setUrl("jdbc:oracle:thin:" + dbUsername + "/" + dbPassword + "@" + dbHost + ":" + dbPort + ":orcl");
		return dataSource;
	}

	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory() {
		LocalSessionFactoryBuilder sessionFactory = new LocalSessionFactoryBuilder(getOracleDataSource());
		sessionFactory.scanPackages("com.musicshop");
		sessionFactory.addProperties(getHibernateProperties());
		SessionFactory sf = sessionFactory.buildSessionFactory();
		return sf;
	}

	private Properties getHibernateProperties() {
		Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.OracleDialect");
		hibernateProperties.setProperty("hibernate.show_sql", "true");
		hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		return hibernateProperties;
	}

	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager txManager = new HibernateTransactionManager(sessionFactory);
		return txManager;
	}
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE","PATCH").allowedOrigins("*")
        .allowedHeaders("*");
	}
}
