package com.uktamjon.sodikov.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.boot.actuate.health.Health;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = org.mockito.quality.Strictness.LENIENT)
class CustomHealthIndicatorTest {

    @Mock
    private Connection mockConnection;

    @InjectMocks
    private CustomHealthIndicator customHealthIndicator;


    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(customHealthIndicator, "activeProfile", "dev");
    }
    @Test
    void health_ShouldReturnUpStatus_WhenDatabaseIsHealthy() throws SQLException {
        when(mockConnection.isValid(5000)).thenReturn(true);
        Health health = customHealthIndicator.health();
        assertEquals(Health.up().build(), health);
    }

    @Test
    void health_ShouldReturnDownStatus_WhenDatabaseIsNotHealthy() throws SQLException {
        when(mockConnection.isValid(5000)).thenReturn(false);

        Health result = customHealthIndicator.health();

        assertEquals(Health.up().build(), result);
    }

    @Test
    void health_ShouldReturnDownStatus_WhenSQLExceptionOccurs() throws SQLException {
        when(mockConnection.isValid(5000)).thenThrow(new SQLException("Test SQLException"));

        Health result = customHealthIndicator.health();

        assertEquals(Health.up().build(), result);
    }
}
