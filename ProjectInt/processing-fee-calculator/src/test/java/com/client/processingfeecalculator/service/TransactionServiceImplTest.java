package com.client.processingfeecalculator.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;
import org.springframework.test.util.ReflectionTestUtils;

import com.client.processingfeecalculator.constant.FileType;
import com.client.processingfeecalculator.constant.PriorityFlag;
import com.client.processingfeecalculator.constant.TransactionType;
import com.client.processingfeecalculator.data.ProcessedDTO;
import com.client.processingfeecalculator.data.ProcessedTransaction;
import com.client.processingfeecalculator.data.TransactionDTO;
import com.client.processingfeecalculator.processor.FeeProcessor;
import com.client.processingfeecalculator.reader.CsvFileReader;
import com.client.processingfeecalculator.reader.FileReaderFactory;
import com.client.processingfeecalculator.repo.ProcessedTransactionRepository;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {

    @Mock
    private FileReaderFactory fileReaderFactory;

    @Mock
    private FeeProcessor feeProcessor;

    @Mock
    private ProcessedTransactionRepository processedTransactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;
    final String targetFileLocation = "src/test/resources/ProcessedData/";
    final String sourceFileLocation = "src/test/resources/InputData/";
    final String fileName = "Transaction Input Data.csv";

    @Before
    public void init() throws IOException {
        final String copyFilePath = "src/test/resources/Transaction Input Data.csv";
        String sourceFilePath = sourceFileLocation + fileName;
        if (!Files.exists(Paths.get(sourceFilePath))) {
            Files.copy(Paths.get(copyFilePath), Paths.get(sourceFilePath));
        }
    }

    @After
    public void tearDown() throws IOException {
        Files.walk(Paths.get(targetFileLocation)).map(Path::toFile).filter(file -> !file.isDirectory()).forEach(File::delete);
    }

    @Test
    public void processTransactionAndSave_ShouldProcessTransactionFromFileAndSave() throws IOException {
        final String absSourceFileLocation = new File(sourceFileLocation).getAbsolutePath();
        final String absTargetFileLocation = new File(targetFileLocation).getAbsolutePath();
        ReflectionTestUtils.setField(transactionService, "targetFileLocation", absTargetFileLocation + "/");
        ReflectionTestUtils.setField(transactionService, "sourceFileLocation", absSourceFileLocation + "/");
        final CsvFileReader csvFileReader = new CsvFileReader();
        Mockito.when(fileReaderFactory.getFileReader(FileType.CSV)).thenReturn(csvFileReader);
        Mockito.when(feeProcessor.getIntraDayTransactionProcessData(ArgumentMatchers.any())).thenReturn(getIntraDayTransactionProcessData());
        Mockito.when(feeProcessor.getNormalTransactionProcessData(ArgumentMatchers.any())).thenReturn(getNormalTransactionProcessData());
        Mockito.when(processedTransactionRepository.saveAll(ArgumentMatchers.anyIterable())).thenReturn(new ArrayList<>());
        transactionService.processTransactionAndSave(fileName);
        Assert.assertEquals(true, Files.exists(Paths.get(targetFileLocation + fileName)));
    }

    @Test
    public void getAllProcessedTransaction_ShouldReturnProcessedTransactionData() {
        Mockito.when(processedTransactionRepository.findAll(ArgumentMatchers.any(Sort.class))).thenReturn(getAllProcessedTransactionData());
        List<ProcessedDTO> processedDTOList = transactionService.getAllProcessedTransaction();
        Assert.assertNotNull(processedDTOList);
        Assert.assertEquals(4, processedDTOList.size());
    }

    @Test
    public void getAllProcessedTransactionForClient_ShouldReturnProcessedTransactionForSpecificClient() {
        Mockito.when(processedTransactionRepository.findByClientId(ArgumentMatchers.any(Sort.class), ArgumentMatchers.any())).thenReturn(getAllProcessedTransactionForClient());
        List<ProcessedDTO> processedDTOList = transactionService.getAllProcessedTransactionForClient("AP");
        Assert.assertNotNull(processedDTOList);
        Assert.assertEquals(1, processedDTOList.size());
    }

    @Test
    public void getIntraDayRawTransactions_ShouldReturnIntraDayTransactions() {
        Assert.assertEquals(2, transactionService.getIntraDayRawTransactions(getRawTransactions()).collect(Collectors.toList()).size());
    }

    @Test
    public void getNormalRawTransactions_ShouldReturnNormalTransactions() {
        Assert.assertEquals(1, transactionService.getNormalRawTransactions(getRawTransactions()).collect(Collectors.toList()).size());
    }

    private static Stream<ProcessedTransaction> getIntraDayTransactionProcessData() {
        return Stream.of(new ProcessedTransaction() {
            {
                setClientId("GS");
                setTransactionType(TransactionType.BUY.name());
                setTransactionDate(LocalDate.of(2013, 11, 23));
                setPriorityFlag(PriorityFlag.Y.name());
                setProcessingFee(10);
            }
        }, new ProcessedTransaction() {
            {
                setClientId("AS");
                setTransactionType(TransactionType.SELL.name());
                setTransactionDate(LocalDate.of(2013, 11, 23));
                setPriorityFlag(PriorityFlag.N.name());
                setProcessingFee(10);
            }
        });
    }

    private static Stream<ProcessedTransaction> getNormalTransactionProcessData() {
        return Stream.of(new ProcessedTransaction() {
            {
                setClientId("AP");
                setTransactionType(TransactionType.BUY.name());
                setTransactionDate(LocalDate.of(2013, 11, 23));
                setPriorityFlag(PriorityFlag.Y.name());
                setProcessingFee(50);
            }
        }, new ProcessedTransaction() {
            {
                setClientId("AS");
                setTransactionType(TransactionType.SELL.name());
                setTransactionDate(LocalDate.of(2014, 11, 23));
                setPriorityFlag(PriorityFlag.N.name());
                setProcessingFee(100);
            }
        }, new ProcessedTransaction() {
            {
                setClientId("GS");
                setTransactionType(TransactionType.DEPOSIT.name());
                setTransactionDate(LocalDate.of(2013, 12, 23));
                setPriorityFlag(PriorityFlag.Y.name());
                setProcessingFee(50);
            }
        }, new ProcessedTransaction() {
            {
                setClientId("HJ");
                setTransactionType(TransactionType.WITHDRAW.name());
                setTransactionDate(LocalDate.of(2015, 11, 25));
                setPriorityFlag(PriorityFlag.N.name());
                setProcessingFee(100);
            }
        });
    }

    private static List<ProcessedTransaction> getAllProcessedTransactionData() {
        return new ArrayList() {
            {
                add(new ProcessedTransaction() {
                    {
                        setClientId("AP");
                        setTransactionType(TransactionType.BUY.name());
                        setTransactionDate(LocalDate.of(2013, 11, 23));
                        setPriorityFlag(PriorityFlag.Y.name());
                        setProcessingFee(50);
                    }
                });
                add(new ProcessedTransaction() {
                    {
                        setClientId("AS");
                        setTransactionType(TransactionType.SELL.name());
                        setTransactionDate(LocalDate.of(2014, 11, 23));
                        setPriorityFlag(PriorityFlag.N.name());
                        setProcessingFee(100);
                    }
                });

                add(new ProcessedTransaction() {
                    {
                        setClientId("GS");
                        setTransactionType(TransactionType.DEPOSIT.name());
                        setTransactionDate(LocalDate.of(2013, 12, 23));
                        setPriorityFlag(PriorityFlag.Y.name());
                        setProcessingFee(50);
                    }
                });

                add(new ProcessedTransaction() {
                    {
                        setClientId("HJ");
                        setTransactionType(TransactionType.WITHDRAW.name());
                        setTransactionDate(LocalDate.of(2015, 11, 25));
                        setPriorityFlag(PriorityFlag.N.name());
                        setProcessingFee(100);
                    }
                });
            }
        };
    }

    private static List<ProcessedTransaction> getAllProcessedTransactionForClient() {
        return new ArrayList() {
            {
                add(new ProcessedTransaction() {
                    {
                        setClientId("AP");
                        setTransactionType(TransactionType.BUY.name());
                        setTransactionDate(LocalDate.of(2013, 11, 23));
                        setPriorityFlag(PriorityFlag.Y.name());
                        setProcessingFee(50);
                    }
                });
            }
        };
    }

    private static List<TransactionDTO> getRawTransactions() {
        return new ArrayList() {
            {
                add(new TransactionDTO() {
                    {
                        setExternalTransactionId("TX1");
                        setClientId("AP");
                        setSecurityId("GOOGLE");
                        setTransactionType(TransactionType.BUY);
                        setTransactionDate(LocalDate.of(2013, 11, 23));
                        setPriorityFlag(PriorityFlag.Y);
                        setMarketValue(70.8);
                    }
                });
                add(new TransactionDTO() {
                    {
                        setExternalTransactionId("TX2");
                        setClientId("AP");
                        setSecurityId("GOOGLE");
                        setTransactionType(TransactionType.SELL);
                        setTransactionDate(LocalDate.of(2013, 11, 23));
                        setPriorityFlag(PriorityFlag.Y);
                        setMarketValue(70.8);
                    }
                });
                add(new TransactionDTO() {
                    {
                        setExternalTransactionId("TX3");
                        setClientId("GS");
                        setSecurityId("GOOGLE");
                        setTransactionType(TransactionType.SELL);
                        setTransactionDate(LocalDate.of(2013, 11, 23));
                        setPriorityFlag(PriorityFlag.Y);
                        setMarketValue(70.8);
                    }
                });
            }
        };
    }
}
