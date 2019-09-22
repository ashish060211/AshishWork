package com.client.processingfeecalculator;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.client.processingfeecalculator.service.TransactionService;

@Component
public class ScheduledTask {

    @Autowired
    private TransactionService transactionService;

    @Value("${sourceFileLocation}")
    private String sourceFileLocation;

    @Scheduled(cron = "${cron.expression}")
    public void loadTransactions() {
        try {
            Files.walk(Paths.get(sourceFileLocation)).map(Path::toFile).filter(file -> !file.isDirectory()).map(File::getName).filter(fileName -> fileName.contains(".csv")).collect(Collectors.toSet()).forEach(fileName -> transactionService.processTransactionAndSave(fileName));
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while loading file", ex.getCause());
        }
    }
}
