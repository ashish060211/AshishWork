package com.client.processingfeecalculator.service;

import com.client.processingfeecalculator.constant.FileType;
import com.client.processingfeecalculator.constant.PriorityFlag;
import com.client.processingfeecalculator.constant.ProcessingFeeCalcConstant;
import com.client.processingfeecalculator.constant.TransactionType;
import com.client.processingfeecalculator.data.ProcessedDTO;
import com.client.processingfeecalculator.data.ProcessedTransaction;
import com.client.processingfeecalculator.data.TransactionDTO;
import com.client.processingfeecalculator.processor.FeeProcessor;
import com.client.processingfeecalculator.reader.FileReaderFactory;
import com.client.processingfeecalculator.repo.ProcessedTransactionRepository;
import com.client.processingfeecalculator.util.Util;
import com.fasterxml.jackson.databind.MappingIterator;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private FileReaderFactory fileReaderFactory;

    @Autowired
    private FeeProcessor feeProcessor;

    @Autowired
    private ProcessedTransactionRepository processedTransactionRepository;

    @Value("${sourceFileLocation}")
    private String sourceFileLocation;

    @Value("${targetFileLocation}")
    private String targetFileLocation;

    @Transactional
    public void processTransactionAndSave(String fileName) {
        try {
            final String sourceFilePath = sourceFileLocation + fileName;
            final String targetFilePath = targetFileLocation + fileName;
            final FileType fileType = Util.getFileType(sourceFilePath).orElseThrow(() -> new RuntimeException(String.format(ProcessingFeeCalcConstant.INVALID_FILE, sourceFilePath)));
            final File file = new File(sourceFilePath);

            final MappingIterator<TransactionDTO> mappingIterator = fileReaderFactory.getFileReader(fileType).getFileReaderIterator(TransactionDTO.class, file);
            final List<TransactionDTO> rawTransactionList = mappingIterator.readAll();
            mappingIterator.close();
            //saving raw data into database table
            //transactionRepository.saveAll(rawTransactionList);

            //fetching intra data transactions
            final Stream<ProcessedTransaction> intraDayProcessedTransactionStream = feeProcessor.getIntraDayTransactionProcessData(getIntraDayRawTransactions(rawTransactionList));

            //fetching normal transaction
            final Stream<ProcessedTransaction> normalProcessedTransactionStream = feeProcessor.getNormalTransactionProcessData(getNormalRawTransactions(rawTransactionList));

            //Merging intraDay and Normal Transactions
            final Stream<ProcessedTransaction> processedTransactionStream = Stream.concat(intraDayProcessedTransactionStream, normalProcessedTransactionStream);

            //fetching ProcessTransactionMap group by clientId, transactionType, transactionDate and priorityFlag
            final Map<String, Map<String, Map<LocalDate, Map<String, List<ProcessedTransaction>>>>> processTransactionGroupedMap = getGroupedProcessTransactionMap(processedTransactionStream);

            //Fetching process transaction list which is group by clientId, transactionType, transactionDate and priorityFlag
            final List<ProcessedTransaction> groupedProcessedTransactions = getProcessTransactionsGroupBy(processTransactionGroupedMap);

            //saving processed transaction data into database
            processedTransactionRepository.saveAll(groupedProcessedTransactions);

            //Move file from source to target location
            Util.moveFile(sourceFilePath, targetFilePath).orElseThrow(() -> new RuntimeException("Failed to move file"));

        } catch (Exception ex) {
            throw new RuntimeException("An Error occurred while reading and processing transactions", ex.getCause());
        }
    }

    public List<ProcessedDTO> getAllProcessedTransaction() {
        final List<ProcessedDTO> processedDTOList = new ArrayList<>();
        final Sort sort = new Sort(Sort.Direction.ASC, ProcessingFeeCalcConstant.CLIENT_ID, ProcessingFeeCalcConstant.TRANSACTION_TYPE, ProcessingFeeCalcConstant.TRANSACTION_DATE, ProcessingFeeCalcConstant.PRIORITY_FLAG);
        try {
            processedTransactionRepository.findAll(sort).forEach(processedTransaction -> {
                ProcessedDTO processedDTO = new ProcessedDTO();
                processedDTO.setClientId(processedTransaction.getClientId());
                processedDTO.setTransactionType(EnumUtils.getEnum(TransactionType.class, processedTransaction.getTransactionType()));
                processedDTO.setTransactionDate(processedTransaction.getTransactionDate());
                processedDTO.setPriorityFlag(EnumUtils.getEnum(PriorityFlag.class, processedTransaction.getPriorityFlag()));
                processedDTO.setProcessingFee(processedTransaction.getProcessingFee());
                processedDTOList.add(processedDTO);
            });
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while fetching data from database for all client", ex.getCause());
        }
        return processedDTOList;
    }

    public List<ProcessedDTO> getAllProcessedTransactionForClient(String clientId) {
        final List<ProcessedDTO> processedDTOList = new ArrayList<>();
        final Sort sort = new Sort(Sort.Direction.ASC, ProcessingFeeCalcConstant.TRANSACTION_TYPE, ProcessingFeeCalcConstant.TRANSACTION_DATE, ProcessingFeeCalcConstant.PRIORITY_FLAG);
        try {
            processedTransactionRepository.findByClientId(sort, clientId).forEach(processedTransaction -> {
                ProcessedDTO processedDTO = new ProcessedDTO();
                processedDTO.setClientId(processedTransaction.getClientId());
                processedDTO.setTransactionType(TransactionType.valueOf(processedTransaction.getTransactionType()));
                processedDTO.setTransactionDate(processedTransaction.getTransactionDate());
                processedDTO.setPriorityFlag(EnumUtils.getEnum(PriorityFlag.class, processedTransaction.getPriorityFlag()));
                processedDTO.setProcessingFee(processedTransaction.getProcessingFee());
                processedDTOList.add(processedDTO);
            });
        } catch (Exception ex) {
            throw new RuntimeException("An error occurred while fetching data from database for client", ex.getCause());
        }
        return processedDTOList;
    }

    private Map<String, Map<String, Map<LocalDate, Map<String, List<ProcessedTransaction>>>>> getGroupedProcessTransactionMap(Stream<ProcessedTransaction> processedTransactionStream) {
        return processedTransactionStream.collect(groupingBy(ProcessedTransaction::getClientId, groupingBy(ProcessedTransaction::getTransactionType, groupingBy(processedTransaction -> processedTransaction.getTransactionDate(), groupingBy(ProcessedTransaction::getPriorityFlag)))));
    }

    private List<ProcessedTransaction> getProcessTransactionsGroupBy(Map<String, Map<String, Map<LocalDate, Map<String, List<ProcessedTransaction>>>>> processTransactionGroupedMap) {
        return processTransactionGroupedMap.entrySet().stream().flatMap(clientIdMap -> clientIdMap.getValue().entrySet().stream()).flatMap(transactionTypeMap -> transactionTypeMap.getValue().entrySet().stream()).flatMap(transactionDateMap -> transactionDateMap.getValue().entrySet().stream()).flatMap(priorityMap -> {
            int processingFee = 0;
            for (int i = 0; i < priorityMap.getValue().size(); i++) {
                processingFee = processingFee + priorityMap.getValue().get(i).getProcessingFee();
            }
            priorityMap.getValue().get(0).setProcessingFee(processingFee);
            List<ProcessedTransaction> processedDataEntities = new ArrayList<>();
            processedDataEntities.add(priorityMap.getValue().get(0));
            return processedDataEntities.stream();
        }).collect(Collectors.toList());
    }

    //keeping protected for unit test
    protected Stream<TransactionDTO> getIntraDayRawTransactions(List<TransactionDTO> rawTransactions) {
        return rawTransactions.stream().collect(groupingBy(TransactionDTO::getClientId, groupingBy(TransactionDTO::getSecurityId, groupingBy(TransactionDTO::getTransactionDate)))).entrySet().stream().flatMap(clientIdMap -> clientIdMap.getValue().entrySet().stream()).flatMap(securityIdMap -> securityIdMap.getValue().entrySet().stream()).filter(transactionDateMap -> transactionDateMap.getValue().size() > 1).flatMap(transactionDateMap -> transactionDateMap.getValue().stream());
    }

    //keeping protected for unit test
    protected Stream<TransactionDTO> getNormalRawTransactions(List<TransactionDTO> rawTransactions) {
        return rawTransactions.stream().collect(groupingBy(TransactionDTO::getClientId, groupingBy(TransactionDTO::getSecurityId, groupingBy(TransactionDTO::getTransactionDate)))).entrySet().stream().flatMap(clientIdMap -> clientIdMap.getValue().entrySet().stream()).flatMap(securityIdMap -> securityIdMap.getValue().entrySet().stream()).filter(transactionDateMap -> transactionDateMap.getValue().size() == 1).flatMap(transactionDateMap -> transactionDateMap.getValue().stream());
    }
}
