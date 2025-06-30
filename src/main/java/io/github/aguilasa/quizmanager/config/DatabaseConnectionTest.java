package io.github.aguilasa.quizmanager.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class DatabaseConnectionTest {

    private final DataSource dataSource;
    private final Environment env;

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    @ConditionalOnProperty(name = "app.db-test.enabled", havingValue = "true")
    public CommandLineRunner testDatabaseConnection() {
        return args -> {
            log.info("Testing database connection...");
            
            try (Connection connection = dataSource.getConnection()) {
                DatabaseMetaData metaData = connection.getMetaData();
                log.info("Connected to: {} - {}", metaData.getDatabaseProductName(), metaData.getDatabaseProductVersion());
                log.info("JDBC URL: {}", metaData.getURL());
                log.info("Username: {}", metaData.getUserName());
                log.info("Driver: {} - {}", metaData.getDriverName(), metaData.getDriverVersion());
                
                // Test entity mapping
                log.info("Testing entity mapping...");
                entityManager.createNativeQuery("SELECT 1").getResultList();
                log.info("Entity mapping is working correctly");
                
                log.info("Database connection test completed successfully");
            } catch (Exception e) {
                log.error("Database connection test failed", e);
                throw e;
            }
        };
    }
}
