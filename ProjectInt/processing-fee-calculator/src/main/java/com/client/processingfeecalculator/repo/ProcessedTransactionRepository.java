package com.client.processingfeecalculator.repo;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.client.processingfeecalculator.data.ProcessedTransaction;

public interface ProcessedTransactionRepository extends PagingAndSortingRepository<ProcessedTransaction, Long> {
    List<ProcessedTransaction> findByClientId(Sort sort, String clientId);
}
