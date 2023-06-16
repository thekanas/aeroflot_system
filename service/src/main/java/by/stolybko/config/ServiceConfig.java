package by.stolybko.config;


import by.stolybko.database.config.DatabaseConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import(DatabaseConfig.class)
@Configuration
@ComponentScan("by.stolybko.service")
public class ServiceConfig {
}
