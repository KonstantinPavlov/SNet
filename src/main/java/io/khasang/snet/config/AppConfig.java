package io.khasang.snet.config;

import io.khasang.snet.model.*;
import io.khasang.snet.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
@PropertySource(value = {"classpath:util.properties"})
@PropertySource(value = {"classpath:auth.properties"})
@PropertySource(value = {"classpath:backup.properties"})
public class AppConfig {

    private Environment environment;

    @Autowired
    public AppConfig(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        JdbcDaoImpl jdbcImpl = new JdbcDaoImpl();
        jdbcImpl.setDataSource(dataSource());
        jdbcImpl.setUsersByUsernameQuery(environment.getRequiredProperty("usersByQuery"));
        jdbcImpl.setAuthoritiesByUsernameQuery(environment.getRequiredProperty("rolesByQuery"));
        return jdbcImpl;
    }

    @Bean
    CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(1000000);
        return commonsMultipartResolver;
    }

    @Bean
    public BackupBase backupBase(){
        return new BackupBase();
    }

    @Bean
    public DeleteTable deleteTable(){
        return new DeleteTable(jdbcTemplate());
    }

    @Bean
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getProperty("jdbc.postgresql.driverClass"));
        dataSource.setUrl(environment.getProperty("jdbc.postgresql.url"));
        dataSource.setUsername(environment.getProperty("jdbc.postgresql.username"));
        dataSource.setPassword(environment.getProperty("jdbc.postgresql.password"));
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }

    @Bean
    public CreateTable createTable(){
        return new CreateTable(jdbcTemplate());
    }
    
    @Bean
    public SelectFromTable selectFromTable(){
        return new SelectFromTable(jdbcTemplate());
    }
    
    @Bean
    public TableConfiguration createTableEmployee(){
        return new TableConfiguration(jdbcTemplate());
    }

    @Bean
    public TruncateTable truncateTable() {
        return new TruncateTable(jdbcTemplate());
    }

    @Bean
    public PostService postService(){
        return new PostService();
    }


}
