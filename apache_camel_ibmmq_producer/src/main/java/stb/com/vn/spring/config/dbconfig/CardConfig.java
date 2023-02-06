package stb.com.vn.spring.config.dbconfig;


import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import stb.com.vn.spring.services.EncryptService;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {"stb.com.vn.spring.repositories.card"},
        entityManagerFactoryRef = "cardEntityManagerFactory",
        transactionManagerRef = "cardTransactionManager"
)
@RequiredArgsConstructor
@Slf4j
public class CardConfig {

    private final EncryptService encryptService;

    @Value("${spring.user-datasource.hbm2ddl-auto-db2: }")
    String hbm2ddlAuto;

    @Primary
    @Bean(name = "cardDataSourceProperties")
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties cardDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "cardDataSource")
    @ConfigurationProperties("spring.datasource.configuration")
    public DataSource cardDataSource(@Qualifier("cardDataSourceProperties") DataSourceProperties cardDataSourceProperties) {
        var dataSource = cardDataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();

        dataSource.setPassword(encryptService.decode(dataSource.getPassword()));

        return dataSource;
    }


    @Primary
    @Bean(name = "cardEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean cardEntityManagerFactory(
            EntityManagerFactoryBuilder builder
            , @Qualifier("cardDataSource") DataSource cardDataSource) {

        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.dialect", "org.hibernate.dialect.DB2Dialect");
        if (Strings.isNotBlank(hbm2ddlAuto)) {
            properties.put("hibernate.hbm2ddl.auto", hbm2ddlAuto);
        }

        return builder
                .dataSource(cardDataSource)
                .packages("stb.com.vn.spring.entity.card", "stb.com.vn.spring.entity.base")
                .properties(properties)
                .build();
    }

    @Primary
    @Bean(name = "cardTransactionManager")
    public PlatformTransactionManager primaryTransactionManager(
            @Qualifier("cardEntityManagerFactory") EntityManagerFactory cardEntityManagerFactory) {

        return new JpaTransactionManager(cardEntityManagerFactory);
    }
}