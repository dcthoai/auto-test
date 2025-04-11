package vn.softdream.autotest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
public class DatabaseConfiguration {

    private final Environment env;

    public DatabaseConfiguration(Environment env) {
        this.env = env;
    }

//    @Bean
//    public DataSource dataSource() {
//        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("driverClassName")));
//        dataSource.setUrl(env.getProperty("url"));
//        dataSource.s
////        dataSource.setUsername(env.getProperty("user"));
////        dataSource.setPassword(env.getProperty("password"));
//        return dataSource;
//    }
}

