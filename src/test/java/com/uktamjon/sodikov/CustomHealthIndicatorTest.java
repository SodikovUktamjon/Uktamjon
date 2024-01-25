package com.uktamjon.sodikov;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.actuate.health.Health;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomHealthIndicatorTest {

    @Mock
    private Connection mockConnection;

    @InjectMocks
    private CustomHealthIndicator customHealthIndicator;

    @Test
    public void testHealthUp() throws SQLException {
        when(mockConnection.isValid(anyInt())).thenReturn(true);
        assertEquals(Health.up().build(), customHealthIndicator.health());
    }

    @Test
    public void testHealthDown() throws SQLException {
        when(mockConnection.isValid(anyInt())).thenReturn(false);
        assertEquals(Health.down().withDetail("Error", "Application is not healthy").build(), customHealthIndicator.health());
    }

    @Test
    public void testDatabaseHealthException() throws SQLException {
        when(mockConnection.isValid(anyInt())).thenThrow(new SQLException("Test Exception"));
        assertEquals(Health.down().withDetail("Error", "Application is not healthy").build(), customHealthIndicator.health());
    }


}
