package com.client.processingfeecalculator.data;

import java.io.Serializable;
import java.time.LocalDate;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.client.processingfeecalculator.constant.PriorityFlag;
import com.client.processingfeecalculator.constant.TransactionType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

public class TransactionDTO implements Serializable {

    private static final long serialVersionUID = 7289158499479182640L;

    @JsonProperty("External Transaction Id")
    private String externalTransactionId;

    @JsonProperty("Client Id")
    private String clientId;

    @JsonProperty("Security Id")
    private String securityId;

    @JsonProperty("Transaction Type")
    private TransactionType transactionType;

    @JsonProperty("Transaction Date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate transactionDate;

    @JsonProperty("Market Value")
    private Double marketValue;

    @JsonProperty("Priority Flag")
    private PriorityFlag priorityFlag;

    public String getExternalTransactionId() {
        return externalTransactionId;
    }

    public void setExternalTransactionId(String externalTransactionId) {
        this.externalTransactionId = externalTransactionId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
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

    public Double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(Double marketValue) {
        this.marketValue = marketValue;
    }

    public PriorityFlag getPriorityFlag() {
        return priorityFlag;
    }

    public void setPriorityFlag(PriorityFlag priorityFlag) {
        this.priorityFlag = priorityFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        TransactionDTO that = (TransactionDTO) o;

        return new EqualsBuilder().append(clientId, that.clientId).append(securityId, that.securityId).append(transactionDate, that.transactionDate).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(clientId).append(securityId).append(transactionDate).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("externalTransactinId", externalTransactionId).append("clientId", clientId).append("securityId", securityId).append("transactionType", transactionType).append("transactionDate", transactionDate).append("marketValue", marketValue).append("priorityFlag", priorityFlag).toString();
    }
}
