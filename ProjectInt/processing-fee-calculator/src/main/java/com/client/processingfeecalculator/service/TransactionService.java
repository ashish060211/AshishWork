package com.client.processingfeecalculator.service;

import java.util.List;

import com.client.processingfeecalculator.data.ProcessedDTO;

public interface TransactionService {
    void processTransactionAndSave(String fileName);

    List<ProcessedDTO> getAllProcessedTransaction();

    List<ProcessedDTO> getAllProcessedTransactionForClient(String clientId);
}
