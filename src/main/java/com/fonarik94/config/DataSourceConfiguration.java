package com.fonarik94.config;

import org.apache.tomcat.jdbc.pool.ConnectionPool;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;
@Configuration
public class DataSourceConfiguration {
    @Bean(name="dataSource")
    @ConfigurationProperties(prefix = "spring.datasource.tomcat")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }
}
