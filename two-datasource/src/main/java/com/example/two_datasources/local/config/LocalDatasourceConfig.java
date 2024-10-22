package com.example.two_datasources.local.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.example.two_datasources.local.repository", // Replace with your repository package
        entityManagerFactoryRef = "localEntityManagerFactory",
        transactionManagerRef = "localTransactionManager"
)
public class LocalDatasourceConfig {

    @Bean(name = "localDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.local")
    DataSource localDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "localEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean localEntityManagerFactory(@Qualifier("localDataSource") DataSource dataSource, JpaProperties jpaProperties) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.example.two_datasources.local.entity"); // Replace with your entity package
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        HashMap<String, Object> properties = new HashMap<>(jpaProperties.getProperties());
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean(name = "localTransactionManager")
    PlatformTransactionManager localTransactionManager(@Qualifier("localEntityManagerFactory") LocalContainerEntityManagerFactoryBean localEntityManagerFactory) {
        return new JpaTransactionManager(localEntityManagerFactory.getObject());
    }
}
