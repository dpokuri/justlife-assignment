package com.justlife.hs.clean.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DataAccessContext {

    public static final String PASSWORD_KEY = "DB_PASS";

    public static final String USER_KEY = "DB_USER";
    
    public static final String HOST_KEY = "DB_HOST";
    
    public static final String DB_KEY = "DB_NAME";
    
    public static final String URL_KEY = "SPRING_DATASOURCE_URL";
    

    @Bean
    public HikariConfig hikariConfig(final Environment environment) {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setCatalog("public");
        hikariConfig.setDriverClassName("org.postgresql.Driver");
        
        
        String url = "jdbc:postgresql://" + environment.getRequiredProperty(HOST_KEY) 
        + "/" + environment.getRequiredProperty(DB_KEY);

        hikariConfig.setJdbcUrl(url);
        hikariConfig.setUsername(environment.getRequiredProperty(USER_KEY));
        hikariConfig.setPassword(environment.getRequiredProperty(PASSWORD_KEY));
        return hikariConfig;
    }

    @Bean
    public DataSource dataSource(HikariConfig hikariConfig) {
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

}
