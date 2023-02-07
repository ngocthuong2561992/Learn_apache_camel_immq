package com.camelmultidb.config.datasources;

import com.camelmultidb.repository.mariaDB.entity.ActorEntity;
import lombok.RequiredArgsConstructor;
import org.apache.camel.spring.spi.SpringTransactionPolicy;
import org.jasypt.encryption.StringEncryptor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(basePackages = "com.camelmultidb.repository.mariadb",
        entityManagerFactoryRef = "mariadbEntityManagerFactory",
        transactionManagerRef= "mariadbTransactionManager")
public class MariaDbConfig {
    final StringEncryptor encryptorBean;

    @Value("${mariadb.datasource.dialect}")
    private String hibernateDialect;

    @Value("${mariadb.datasource.url}")
    private String url;

    @Value("${mariadb.datasource.username}")
    private String username;

    @Value("${mariadb.datasource.password}")
    private String password;

    @Bean(name = "mariadbDataSource")
    @ConfigurationProperties("mariadb.datasource.configuration")
    public DataSource getDataSource() {
        return DataSourceBuilder.create()
            .url(url)
            .username(username)
            .password(encryptorBean.decrypt(password))
            .build();
    }

    @Bean(name = "mariadbEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean getEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        HashMap<String, Object> props = new HashMap<>();
        props.put("hibernate.dialect", hibernateDialect);
        return builder
                .dataSource(getDataSource())
                .properties(props)
                .packages(ActorEntity.class)
                .build();
    }

    @Bean(name = "mariadbTransactionManager")
    public PlatformTransactionManager getTransactionManager(
            final @Qualifier("mariadbEntityManagerFactory")
            LocalContainerEntityManagerFactoryBean entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory.getObject());
    }

    @Bean(name = "txPolicyMariadb")
    public SpringTransactionPolicy txPolicyMaria(
            final @Qualifier("mariadbTransactionManager") PlatformTransactionManager txManager) {
        var policy = new SpringTransactionPolicy();
        policy.setTransactionManager(txManager);
        return policy;
    }

}
