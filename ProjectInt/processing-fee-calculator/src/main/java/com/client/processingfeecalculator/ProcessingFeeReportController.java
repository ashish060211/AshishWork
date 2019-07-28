package com.client.processingfeecalculator;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.client.processingfeecalculator.constant.ProcessingFeeCalcConstant;
import com.client.processingfeecalculator.data.ProcessedDTO;
import com.client.processingfeecalculator.service.TransactionService;
import com.client.processingfeecalculator.util.Util;

@RestController
@Transactional
public class ProcessingFeeReportController {

    @Autowired
    private TransactionService transactionService;

    @Value("${reportFileName}")
    private String reportFileName;

    @GetMapping(value = "/reports")
    @ResponseStatus(HttpStatus.OK)
    public void getReportForAllClient(HttpServletResponse response) {
        response.addHeader(ProcessingFeeCalcConstant.CONTENT_TYPE_KEY, ProcessingFeeCalcConstant.CONTENT_TYPE_VALUE);
        response.addHeader(ProcessingFeeCalcConstant.CONTENT_DISPOSITION_KEY, String.format(ProcessingFeeCalcConstant.CONTENT_DISPOSITION_VALUE, Util.getFileNameWithTimeStamp(reportFileName)));
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        Util.writeDataToCSV(ProcessedDTO.class, transactionService.getAllProcessedTransaction(), response);
    }

    @GetMapping(value = "/reports/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public void getReportForClient(@PathVariable String clientId, HttpServletResponse response) {
        response.addHeader(ProcessingFeeCalcConstant.CONTENT_TYPE_KEY, ProcessingFeeCalcConstant.CONTENT_TYPE_VALUE);
        response.addHeader(ProcessingFeeCalcConstant.CONTENT_DISPOSITION_KEY, String.format(ProcessingFeeCalcConstant.CONTENT_DISPOSITION_VALUE, Util.getFileNameWithTimeStamp(reportFileName)));
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        Util.writeDataToCSV(ProcessedDTO.class, transactionService.getAllProcessedTransactionForClient(clientId), response);
    }
}
