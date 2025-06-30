package io.github.aguilasa.quizmanager.integration;

import io.github.aguilasa.quizmanager.model.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@Slf4j
public class PostgresqlIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("quiz")
            .withUsername("postgres")
            .withPassword("postgres");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Environment env;

    @Test
    public void testDatabaseConnection() throws SQLException {
        log.info("Testing PostgreSQL connection via Testcontainers...");
        
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            log.info("Connected to: {} - {}", metaData.getDatabaseProductName(), metaData.getDatabaseProductVersion());
            log.info("JDBC URL: {}", metaData.getURL());
            log.info("Username: {}", metaData.getUserName());
            log.info("Driver: {} - {}", metaData.getDriverName(), metaData.getDriverVersion());
            
            // Verificar se as tabelas foram criadas pelo Flyway
            List<String> tables = new ArrayList<>();
            try (ResultSet rs = metaData.getTables(null, "public", "%", new String[]{"TABLE"})) {
                while (rs.next()) {
                    tables.add(rs.getString("TABLE_NAME"));
                }
            }
            
            log.info("Tables found in database: {}", tables);
            
            // Verificar se as tabelas principais existem
            assertTrue(tables.contains("categories"), "Table 'categories' should exist");
            assertTrue(tables.contains("questions"), "Table 'questions' should exist");
            assertTrue(tables.contains("answers"), "Table 'answers' should exist");
            assertTrue(tables.contains("users"), "Table 'users' should exist");
            
            // Verificar se o tipo enum foi criado
            List<String> types = new ArrayList<>();
            try (ResultSet rs = connection.createStatement().executeQuery(
                    "SELECT typname FROM pg_type JOIN pg_namespace ON pg_namespace.oid = pg_type.typnamespace " +
                    "WHERE nspname = 'public' AND typtype = 'e'")) {
                while (rs.next()) {
                    types.add(rs.getString("typname"));
                }
            }
            
            log.info("Custom types found in database: {}", types);
            assertTrue(types.contains("difficulty_level"), "Type 'difficulty_level' should exist");
        }
    }

    @Test
    public void testEntityMapping() {
        // Verificar se as entidades podem ser carregadas pelo EntityManager
        assertNotNull(entityManager.getMetamodel().entity(Category.class));
        assertNotNull(entityManager.getMetamodel().entity(Question.class));
        assertNotNull(entityManager.getMetamodel().entity(Answer.class));
        assertNotNull(entityManager.getMetamodel().entity(User.class));
        assertNotNull(entityManager.getMetamodel().entity(Authority.class));
        assertNotNull(entityManager.getMetamodel().entity(Questionnaire.class));
        assertNotNull(entityManager.getMetamodel().entity(QuestionnaireFilter.class));
        assertNotNull(entityManager.getMetamodel().entity(QuestionnaireQuestion.class));
        assertNotNull(entityManager.getMetamodel().entity(UserQuestionnaireSession.class));
        assertNotNull(entityManager.getMetamodel().entity(UserAnswer.class));
        assertNotNull(entityManager.getMetamodel().entity(UserAnswerHistory.class));
        
        log.info("All entities are correctly mapped to the database schema");
    }
}
