package com.client.processingfeecalculator.constant;

public class ProcessingFeeCalcConstant {

    private ProcessingFeeCalcConstant() {
    }

    public static final String INVALID_FILE = "%s file does not have extension or formatted not supported";

    //Declared constant for sorting
    public static final String CLIENT_ID = "clientId";
    public static final String TRANSACTION_DATE = "transactionDate";
    public static final String TRANSACTION_TYPE = "transactionType";
    public static final String PRIORITY_FLAG = "priorityFlag";

    //Declared constant for processing fee
    public static final String INTRADAY = "intraday";
    public static final String HIGH_PRIORITY = "highPriority";
    public static final String SELL_OR_WITHDRAW = "sellOrWithdraw";
    public static final String BUY_OR_DEPOSIT = "buyOrDeposit";

    //Declared constant for response header
    public static final String CONTENT_DISPOSITION_KEY = "Content-Disposition";
    public static final String CONTENT_DISPOSITION_VALUE = "attachment; filename=%s";
    public static final String CONTENT_TYPE_KEY = "Content-Type";
    public static final String CONTENT_TYPE_VALUE = "application/csv";

}
