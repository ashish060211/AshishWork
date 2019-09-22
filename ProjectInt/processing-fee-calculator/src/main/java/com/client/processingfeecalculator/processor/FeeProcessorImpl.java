package com.client.processingfeecalculator.processor;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.client.processingfeecalculator.ProcessingFeeConfig;
import com.client.processingfeecalculator.constant.PriorityFlag;
import com.client.processingfeecalculator.constant.ProcessingFeeCalcConstant;
import com.client.processingfeecalculator.constant.TransactionType;
import com.client.processingfeecalculator.data.ProcessedTransaction;
import com.client.processingfeecalculator.data.TransactionDTO;

@Component
public class FeeProcessorImpl implements FeeProcessor {

    @Autowired
    private ProcessingFeeConfig processingFeeConfig;

    public Stream<ProcessedTransaction> getIntraDayTransactionProcessData(Stream<TransactionDTO> intraDayTransactionStream) {
        return intraDayTransactionStream.map(intraDayTransaction -> {
            ProcessedTransaction processedTransaction = new ProcessedTransaction();
            processedTransaction.setClientId(intraDayTransaction.getClientId());
            processedTransaction.setTransactionDate(intraDayTransaction.getTransactionDate());
            processedTransaction.setTransactionType(intraDayTransaction.getTransactionType().name());
            processedTransaction.setPriorityFlag(intraDayTransaction.getPriorityFlag().name());
            processedTransaction.setProcessingFee(processingFeeConfig.getProcessingFees().get(ProcessingFeeCalcConstant.INTRADAY));
            return processedTransaction;
        });
    }

    public Stream<ProcessedTransaction> getNormalTransactionProcessData(Stream<TransactionDTO> normalTransactionStream) {
        return normalTransactionStream.map(normalTransaction -> {
            ProcessedTransaction processedTransaction = new ProcessedTransaction();
            processedTransaction.setClientId(normalTransaction.getClientId());
            processedTransaction.setTransactionDate(normalTransaction.getTransactionDate());
            processedTransaction.setTransactionType(normalTransaction.getTransactionType().name());
            processedTransaction.setPriorityFlag(normalTransaction.getPriorityFlag().name());
            processedTransaction.setProcessingFee(getProcessingFee(normalTransaction));
            return processedTransaction;
        });
    }

    private int getProcessingFee(TransactionDTO rawTransaction) {
        int processingFee = 0;
        if (PriorityFlag.Y.equals(rawTransaction.getPriorityFlag())) {
            processingFee = processingFeeConfig.getProcessingFees().get(ProcessingFeeCalcConstant.HIGH_PRIORITY);
        } else if (TransactionType.SELL.equals(rawTransaction.getTransactionType()) || TransactionType.WITHDRAW.equals(rawTransaction.getTransactionType())) {
            processingFee = processingFeeConfig.getProcessingFees().get(ProcessingFeeCalcConstant.SELL_OR_WITHDRAW);
        } else if (TransactionType.BUY.equals(rawTransaction.getTransactionType()) || TransactionType.DEPOSIT.equals(rawTransaction.getTransactionType())) {
            processingFee = processingFeeConfig.getProcessingFees().get(ProcessingFeeCalcConstant.BUY_OR_DEPOSIT);
        }
        return processingFee;
    }
}
