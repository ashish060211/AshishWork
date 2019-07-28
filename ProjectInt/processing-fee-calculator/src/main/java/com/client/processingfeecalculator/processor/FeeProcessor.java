package com.client.processingfeecalculator.processor;

import java.util.stream.Stream;

import com.client.processingfeecalculator.data.ProcessedTransaction;
import com.client.processingfeecalculator.data.TransactionDTO;

public interface FeeProcessor {
    Stream<ProcessedTransaction> getIntraDayTransactionProcessData(Stream<TransactionDTO> intraDayTransactionStream);

    Stream<ProcessedTransaction> getNormalTransactionProcessData(Stream<TransactionDTO> normalTransactionStream);
}
