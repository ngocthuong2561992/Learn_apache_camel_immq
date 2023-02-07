package com.camelmultidb.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ibm.mq.jms.MQConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.web.client.RestTemplate;

import javax.jms.JMSException;

@EnableJms
@Configuration
@RequiredArgsConstructor
public class BeanConfig {

    final Environment env;

    @Bean(name = "customObjectMapper")
    public ObjectMapper getObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }

//    @Bean(name = "ibmmq")
//    public IBMMQ getIBMMQ() {
//        var mq = new IBMMQ();
//        mq.setHost("localhost");
//        mq.setPort(1416);
//        mq.setQueueManager("QM1");
//        mq.setChannel("DEV.ADMIN.SVRCONN");
//        mq.setUser("admin");
//        mq.setPassword("passw0rd");
//        return mq;
//    }

    @Bean(name = "customRestTemplate")
    public RestTemplate getRestTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setReadTimeout(5000);
        requestFactory.setConnectTimeout(5000);
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        return restTemplate;
    }

    @Bean(name = "encryptorBean")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword("password");
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }

    @Bean
    CamelContextConfiguration contextConfiguration() {
        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(CamelContext context) {
                var connectionFactory = new MQConnectionFactory();
                try {
                    connectionFactory.setQueueManager("QM1");
                    connectionFactory.setTransportType(1);
                    connectionFactory.setPort(1414);
                    connectionFactory.setHostName("localhost");
                    connectionFactory.setChannel("DEV.ADMIN.SVRCONN");
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }

                var adapter = new UserCredentialsConnectionFactoryAdapter();
                adapter.setTargetConnectionFactory(connectionFactory);
                adapter.setUsername("admin");
                adapter.setPassword("passw0rd");
                context.addComponent("ibmmq", JmsComponent.jmsComponentAutoAcknowledge(adapter));

                System.setProperty("hawtio.authenticationEnabled", "false");
            }

            @Override
            public void afterApplicationStart(CamelContext context) {
            }
        };
    }
}
