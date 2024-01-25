package com.uktamjon.sodikov.utils;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service

public class CustomMetricsService {

    private MeterRegistry meterRegistry;

    public CustomMetricsService() {
        this.meterRegistry = new SimpleMeterRegistry();
    }


    public MeterRegistry getMeterRegistry() {
        return meterRegistry;
    }
    public void setMeterRegistry(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }


    public void recordCustomMetric(int value) {
        meterRegistry.counter("custom_metric", "tag", "value").increment(value);
    }
}
