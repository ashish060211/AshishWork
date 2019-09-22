package com.client.processingfeecalculator;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "processing-fee-config")
public class ProcessingFeeConfig {

    private Map<String, Integer> processingFees;

    public Map<String, Integer> getProcessingFees() {
        return processingFees;
    }

    public void setProcessingFees(Map<String, Integer> processingFees) {
        this.processingFees = processingFees;
    }
}
