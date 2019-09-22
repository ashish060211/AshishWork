package com.client.processingfeecalculator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.client.processingfeecalculator.constant.PriorityFlag;
import com.client.processingfeecalculator.constant.ProcessingFeeCalcConstant;
import com.client.processingfeecalculator.constant.TransactionType;
import com.client.processingfeecalculator.data.ProcessedDTO;
import com.client.processingfeecalculator.service.TransactionService;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ProcessingFeeReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void getReportForAllClient_ShouldGenerateCSVFileWhenAccessReportsEndpoint() throws Exception {
        List<ProcessedDTO> processedDTOList = new ArrayList() {
            {
                add(new ProcessedDTO() {
                    {
                        setClientId("AD");
                        setTransactionType(TransactionType.BUY);
                        setTransactionDate(LocalDate.now());
                        setPriorityFlag(PriorityFlag.Y);
                        setProcessingFee(500);
                    }
                });

                add(new ProcessedDTO() {
                    {
                        setClientId("AS");
                        setTransactionType(TransactionType.BUY);
                        setTransactionDate(LocalDate.now());
                        setPriorityFlag(PriorityFlag.Y);
                        setProcessingFee(500);
                    }
                });
            }
        };
        Mockito.when(transactionService.getAllProcessedTransaction()).thenReturn(processedDTOList);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/reports")).andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Assert.assertEquals(200, result.getResponse().getStatus());
        Assert.assertEquals(ProcessingFeeCalcConstant.CONTENT_TYPE_VALUE, result.getResponse().getContentType());
    }

    @Test
    public void getReportForClient_ShouldGenerateCSVFileWhenAccessReportEndpointForGivenClientId() throws Exception {
        List<ProcessedDTO> processedDTOList = new ArrayList() {
            {
                add(new ProcessedDTO() {
                    {
                        setClientId("AD");
                        setTransactionType(TransactionType.BUY);
                        setTransactionDate(LocalDate.now());
                        setPriorityFlag(PriorityFlag.Y);
                        setProcessingFee(500);
                    }
                });
            }
        };
        Mockito.when(transactionService.getAllProcessedTransaction()).thenReturn(processedDTOList);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/reports/AD")).andExpect(MockMvcResultMatchers.status().is(200)).andReturn();
        Assert.assertEquals(200, result.getResponse().getStatus());
        Assert.assertEquals(ProcessingFeeCalcConstant.CONTENT_TYPE_VALUE, result.getResponse().getContentType());
    }

}
