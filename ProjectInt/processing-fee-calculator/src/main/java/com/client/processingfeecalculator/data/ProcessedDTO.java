package com.client.processingfeecalculator.data;

import java.io.Serializable;
import java.time.LocalDate;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.client.processingfeecalculator.constant.PriorityFlag;
import com.client.processingfeecalculator.constant.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessedDTO implements Serializable {

    private static final long serialVersionUID = -5623220506011250835L;

    @JsonProperty("Client Id")
    private String clientId;

    @JsonProperty("Transaction Type")
    private TransactionType transactionType;

    @JsonProperty("Transaction Date")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate transactionDate;

    @JsonProperty("Priority Flag")
    private PriorityFlag priorityFlag;

    @JsonProperty("Processing Fee")
    private int processingFee;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public PriorityFlag getPriorityFlag() {
        return priorityFlag;
    }

    public void setPriorityFlag(PriorityFlag priorityFlag) {
        this.priorityFlag = priorityFlag;
    }

    public int getProcessingFee() {
        return processingFee;
    }

    public void setProcessingFee(int processingFee) {
        this.processingFee = processingFee;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("clientId", clientId).append("transactionType", transactionType).append("transactionDate", transactionDate).append("priorityFlag", priorityFlag).append("processingFee", processingFee).toString();
    }
}
