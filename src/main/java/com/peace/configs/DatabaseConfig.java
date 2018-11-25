package com.peace.configs;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Contains database configurations.
 */
@Configuration
@EnableTransactionManagement
@Import({ SecurityConfig.class })
public class DatabaseConfig extends WebMvcConfigurerAdapter{

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory =
                new LocalContainerEntityManagerFactoryBean();

        entityManagerFactory.setDataSource(dataSource);
        entityManagerFactory.setPackagesToScan("com.peace.users.model.mram");

        // Vendor adapter
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(vendorAdapter);

        // Hibernate properties
        Properties additionalProperties = new Properties();

        additionalProperties.put("hibernate.show_sql", "false");
        additionalProperties.put("hibernate.format_sql", "false");
        additionalProperties.put("hibernate.use_sql_comments", "false");
        additionalProperties.put("hibernate.enable_lazy_load_no_trans", "true");
        additionalProperties.put("hibernate.auto_close_session", "true");
        additionalProperties.put("hibernate.connection.characterEncoding", "utf-8");
        additionalProperties.put("connection.useUnicode", "true");
        additionalProperties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");

        additionalProperties.put("hibernate.hbm2ddl.auto","none");
        entityManagerFactory.setJpaProperties(additionalProperties);

        return entityManagerFactory;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager =
                new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                entityManagerFactory.getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**").addResourceLocations("/uploads/");
        registry.addResourceHandler("/admin/**").addResourceLocations("/");
    }


    @Autowired
    private DataSource dataSource;

    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;

    @Bean
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBuilder builder = new LocalSessionFactoryBuilder(dataSource);


        // Hibernate properties
        Properties additionalProperties = new Properties();
        additionalProperties.put("hibernate.show_sql", "false");
        additionalProperties.put("hibernate.format_sql", "false");
        additionalProperties.put("hibernate.use_sql_comments", "false");
        additionalProperties.put("hibernate.enable_lazy_load_no_trans", "true");
        additionalProperties.put("hibernate.auto_close_session", "true");
        additionalProperties.put("hibernate.connection.characterEncoding", "utf-8");
        additionalProperties.put("connection.useUnicode", "true");
        additionalProperties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        additionalProperties.put("current_session_context_class", "tread");

        builder.scanPackages("com.peace.users.model.mram").addProperties(additionalProperties);

        return builder.buildSessionFactory();
    }
}
