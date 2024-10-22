package com.example.two_datasources.egain.config;

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
        basePackages = "com.example.two_datasources.egain.repository", // Replace with your repository package
        entityManagerFactoryRef = "eGainEntityManagerFactory",
        transactionManagerRef = "eGainTransactionManager"
)
public class EGainDatasourceConfig {

    @Bean(name = "eGainDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.e-gain")
    DataSource eGainDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "eGainEntityManagerFactory")
    LocalContainerEntityManagerFactoryBean eGainEntityManagerFactory(@Qualifier("eGainDataSource") DataSource dataSource, JpaProperties jpaProperties) {

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.example.two_datasources.eGain.entity"); // Replace with your entity package
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        HashMap<String, Object> properties = new HashMap<>(jpaProperties.getProperties());
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean(name = "eGainTransactionManager")
    PlatformTransactionManager eGainTransactionManager(@Qualifier("eGainEntityManagerFactory") LocalContainerEntityManagerFactoryBean eGainEntityManagerFactory) {
        return new JpaTransactionManager(eGainEntityManagerFactory.getObject());
    }
}
