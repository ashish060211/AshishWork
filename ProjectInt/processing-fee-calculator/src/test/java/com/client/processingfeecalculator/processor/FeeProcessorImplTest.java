package com.client.processingfeecalculator.processor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.client.processingfeecalculator.ProcessingFeeConfig;
import com.client.processingfeecalculator.constant.PriorityFlag;
import com.client.processingfeecalculator.constant.ProcessingFeeCalcConstant;
import com.client.processingfeecalculator.constant.TransactionType;
import com.client.processingfeecalculator.data.ProcessedTransaction;
import com.client.processingfeecalculator.data.TransactionDTO;

@RunWith(MockitoJUnitRunner.class)
public class FeeProcessorImplTest {

    @Mock
    private ProcessingFeeConfig processingFeeConfig;

    @InjectMocks
    private FeeProcessorImpl feeProcessor;

    @Test
    public void getIntraDayTransactionProcessData_ShouldReturnProcessedTransactionWithFee500WhenTransactionIsIntraDay() {
        Mockito.when(processingFeeConfig.getProcessingFees()).thenReturn(getProcessingFeeMap());
        Stream<TransactionDTO> rawTransactionStream = Stream.of(new TransactionDTO() {
            {
                setExternalTransactionId("EXT1");
                setClientId("GS");
                setSecurityId("GOOGLE");
                setTransactionType(TransactionType.SELL);
                setTransactionDate(LocalDate.now());
                setPriorityFlag(PriorityFlag.Y);
                setMarketValue(78.9);
            }
        });
        Stream<ProcessedTransaction> processedTransactionStream = feeProcessor.getIntraDayTransactionProcessData(rawTransactionStream);
        Assert.assertNotNull(processedTransactionStream);
        List<ProcessedTransaction> processedTransactionList = processedTransactionStream.collect(Collectors.toList());
        Assert.assertEquals(10, processedTransactionList.get(0).getProcessingFee());
    }

    @Test
    public void getNormalTransactionProcessData_ShouldReturnProcessedTransactionWithFeesWhenTransactionsAreNormal() {
        Mockito.when(processingFeeConfig.getProcessingFees()).thenReturn(getProcessingFeeMap());
        Stream<ProcessedTransaction> processedTransactionStream = feeProcessor.getNormalTransactionProcessData(getNormalRawTransaction());
        Assert.assertNotNull(processedTransactionStream);
        List<ProcessedTransaction> processedTransactionList = processedTransactionStream.collect(Collectors.toList());
        Assert.assertEquals(500, processedTransactionList.get(0).getProcessingFee());
        Assert.assertEquals(50, processedTransactionList.get(1).getProcessingFee());
        Assert.assertEquals(50, processedTransactionList.get(2).getProcessingFee());
        Assert.assertEquals(100, processedTransactionList.get(3).getProcessingFee());
        Assert.assertEquals(100, processedTransactionList.get(4).getProcessingFee());
    }

    private Map<String, Integer> getProcessingFeeMap() {
        return new HashMap<String, Integer>() {
            {
                put(ProcessingFeeCalcConstant.INTRADAY, 10);
                put(ProcessingFeeCalcConstant.HIGH_PRIORITY, 500);
                put(ProcessingFeeCalcConstant.SELL_OR_WITHDRAW, 100);
                put(ProcessingFeeCalcConstant.BUY_OR_DEPOSIT, 50);
            }
        };
    }

    private Stream<TransactionDTO> getNormalRawTransaction() {
        return Stream.of(new TransactionDTO() {
            {
                setExternalTransactionId("EXT1");
                setClientId("GS");
                setSecurityId("GOOGLE");
                setTransactionType(TransactionType.BUY);
                setTransactionDate(LocalDate.now());
                setPriorityFlag(PriorityFlag.Y);
                setMarketValue(78.9);
            }
        }, new TransactionDTO() {
            {
                setExternalTransactionId("EXT1");
                setClientId("GS");
                setSecurityId("GOOGLE");
                setTransactionType(TransactionType.BUY);
                setTransactionDate(LocalDate.now());
                setPriorityFlag(PriorityFlag.N);
                setMarketValue(78.9);
            }
        }, new TransactionDTO() {
            {
                setExternalTransactionId("EXT1");
                setClientId("GS");
                setSecurityId("GOOGLE");
                setTransactionType(TransactionType.DEPOSIT);
                setTransactionDate(LocalDate.now());
                setPriorityFlag(PriorityFlag.N);
                setMarketValue(78.9);
            }
        }, new TransactionDTO() {
            {
                setExternalTransactionId("EXT1");
                setClientId("GS");
                setSecurityId("GOOGLE");
                setTransactionType(TransactionType.SELL);
                setTransactionDate(LocalDate.now());
                setPriorityFlag(PriorityFlag.N);
                setMarketValue(78.9);
            }
        }, new TransactionDTO() {
            {
                setExternalTransactionId("EXT1");
                setClientId("GS");
                setSecurityId("GOOGLE");
                setTransactionType(TransactionType.WITHDRAW);
                setTransactionDate(LocalDate.now());
                setPriorityFlag(PriorityFlag.N);
                setMarketValue(78.9);
            }
        });
    }

}
