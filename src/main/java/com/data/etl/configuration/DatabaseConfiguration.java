package com.data.etl.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Bean(name = "postgresDataSource")
    @ConfigurationProperties(prefix = "spring.postgres.datasource")
    public DataSource postgresDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "neo4jDataSource")
    @ConfigurationProperties(prefix = "spring.neo4j.datasource")
    public DataSource neo4jDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "jdbcPostgres")
    @Autowired
    public JdbcTemplate postgresJdbcTemplate(@Qualifier("postgresDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "jdbcNeo4j")
    @Autowired
    public JdbcTemplate neo4jJdbcTemplate(@Qualifier("neo4jDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
