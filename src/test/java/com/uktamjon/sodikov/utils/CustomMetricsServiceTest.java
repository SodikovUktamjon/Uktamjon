package com.uktamjon.sodikov.utils;

import com.uktamjon.sodikov.utils.CustomMetricsService;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomMetricsServiceTest {

    private CustomMetricsService customMetricsService;

    @BeforeEach
    void setUp() {
        customMetricsService = new CustomMetricsService();
        customMetricsService.setMeterRegistry(new SimpleMeterRegistry());
    }

    @Test
    void recordCustomMetric() {
        int valueToRecord = 5;

        customMetricsService.recordCustomMetric(valueToRecord);

        Counter counter = customMetricsService.getMeterRegistry().find("custom_metric")
                .tag("tag", "value").counter();
        assertEquals(valueToRecord, Objects.requireNonNull(counter).count(), "Custom metric value should match");
    }
}
