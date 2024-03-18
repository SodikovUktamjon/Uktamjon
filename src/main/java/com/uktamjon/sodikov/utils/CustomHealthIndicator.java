package com.uktamjon.sodikov.utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@Slf4j
public class CustomHealthIndicator implements HealthIndicator {
    @Value("spring.profiles.active")
    private String activeProfile;

    @Override
    public Health health() {
        boolean isHealthy = checkIfApplicationIsHealthy();
        
        if (isHealthy) {
            return Health.up().build();
        } else {
            return Health.down().withDetail("Error", "Application is not healthy").build();
        }
    }

    public boolean checkIfApplicationIsHealthy() {
        return checkDatabaseHealth();
    }


        public boolean checkDatabaseHealth() {
            Connection connection = null;
            try {
                String jdbcUrl;
                switch (activeProfile){
                    case "dev"->jdbcUrl="jdbc:postgresql://localhost:5432/spring_core_db";
                    case "prod"->jdbcUrl="jdbc:postgresql://localhost:5432/prod_db";
                    case "stg"->jdbcUrl="jdbc:postgresql://localhost:5432/stg_db";
                    case "local"->jdbcUrl="jdbc:postgresql://localhost:5432/local_db";
                    default -> throw new IllegalStateException("Unexpected value: " + activeProfile);
                }

                String username = "postgres";
                String password = "123";

                connection = DriverManager.getConnection(jdbcUrl, username, password);

                return connection.isValid(5000);
            } catch (SQLException e) {
                log.error("Error while checking database health", e);
                e.printStackTrace();
                return false;
            } finally {
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        log.error("Error while closing the database connection", e);
                        e.printStackTrace();
                    }
                }

            }
    }

}
