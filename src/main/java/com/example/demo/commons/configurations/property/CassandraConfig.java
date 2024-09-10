package com.example.demo.commons.configurations.property;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.beans.factory.InitializingBean;

@Configuration("CassandraConfig")
@EnableCassandraRepositories(basePackages = "com.example.demo.repository")
public class CassandraConfig extends AbstractCassandraConfiguration implements InitializingBean {
    private final Logger logger = LogManager.getLogger(CassandraConfig.class);

    @Override
    protected String getKeyspaceName() {
        return "recharge_emi";
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("Successfully connected to Cassandra keyspace: {}", getKeyspaceName());
    }
}